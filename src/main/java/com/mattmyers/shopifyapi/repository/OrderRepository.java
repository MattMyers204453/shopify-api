package com.mattmyers.shopifyapi.repository;

import com.mattmyers.shopifyapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
