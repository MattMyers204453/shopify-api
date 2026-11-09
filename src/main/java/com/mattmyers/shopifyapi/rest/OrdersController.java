package com.mattmyers.shopifyapi.rest;

import com.mattmyers.shopifyapi.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mattmyers.shopifyapi.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> allOrders = orderService.getAll();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @PostMapping(path="/fetch", produces = "application/json")
    public ResponseEntity<Void> fetchAll(){
        orderService.fetchAllAndSave();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Void> deleteAll() {
        orderService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
