package com.mattmyers.shopifyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyResponse {

    private ResponseData data;

    @Data
    public static class ResponseData {
        private Orders orders;
    }

    @Data
    public static class Orders {
        private List<OrderNode> nodes;
    }

    @Data
    public static class OrderNode {
        private String id;
        private String name;
        private String createdAt;
        private String email;
        private Customer customer;
        private TotalPriceSet totalPriceSet;
    }

    @Data
    public static class Customer {
        private String firstName;
        private String lastName;
    }

    @Data
    public static class TotalPriceSet {
        private ShopMoney shopMoney;
    }

    @Data
    public static class ShopMoney {
        private String amount;
        private String currencyCode;
    }
}
