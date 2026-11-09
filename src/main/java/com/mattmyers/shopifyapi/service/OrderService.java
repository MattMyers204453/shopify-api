package com.mattmyers.shopifyapi.service;

import com.mattmyers.shopifyapi.model.Order;
import com.mattmyers.shopifyapi.model.ShopifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.mattmyers.shopifyapi.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private static final String QUERY = "{ orders(first: 100) { nodes { id name createdAt email "
            + "customer { firstName lastName } totalPriceSet { shopMoney { amount currencyCode } } } } }";

    private static final String STORE = "test-store-szeaoot9";

    @Value("${SHOPIFY_API_TOKEN}")
    private String token;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public void deleteAll(){
        orderRepository.deleteAll();
    }

    public void fetchAllAndSave() {
        RestClient client = RestClient.create();
        ShopifyResponse response = client.post()
                .uri("https://" + STORE + ".myshopify.com/admin/api/2025-10/graphql.json")
                .header("X-Shopify-Access-Token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("query", QUERY))
                .retrieve()
                .body(ShopifyResponse.class);

        List<Order> orders = new ArrayList<>();
        for (ShopifyResponse.OrderNode node : response.getData().getOrders().getNodes()) {
            Order order = parseOrder(node);
            orders.add(order);
        }
        orderRepository.saveAll(orders);
    }

    private Order parseOrder(ShopifyResponse.OrderNode node) {
        String gid = node.getId();
        ShopifyResponse.Customer customer = node.getCustomer();
        ShopifyResponse.ShopMoney total = node.getTotalPriceSet().getShopMoney();

        Order order = new Order();
        order.setShopifyId(Long.parseLong(gid.substring(gid.lastIndexOf('/') + 1)));
        order.setOrderName(node.getName());
        order.setCreatedAt(Instant.parse(node.getCreatedAt()));
        if (customer == null) {
            order.setCustomerName("Unknown Customer");
        } else {
            order.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
        }
        order.setEmail(node.getEmail());
        order.setTotalAmount(new BigDecimal(total.getAmount()));
        order.setCurrency(total.getCurrencyCode());
        return order;
    }

}
