package com.mattmyers.shopifyapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Long shopifyId;

    private String orderName;
    private Instant createdAt;
    private String customerName;
    private String email;
    private BigDecimal totalAmount;
    private String currency;
}
