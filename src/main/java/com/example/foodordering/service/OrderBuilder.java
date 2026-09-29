package com.example.foodordering.service;

import com.example.foodordering.entity.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {

    private final Order order = new Order();

    public OrderBuilder customer(String name, String phone, String address) {
        order.setCustomerName(name);
        order.setPhone(phone);
        order.setAddress(address);
        return this;
    }

    public OrderBuilder paymentMethod(PaymentMethod method) {
        order.setPaymentMethod(method);
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        order.setStatus(status);
        return this;
    }

    public OrderBuilder createdAt(LocalDateTime createdAt) {
        order.setCreatedAt(createdAt);
        return this;
    }

    public OrderBuilder items(List<OrderItem> items) {
        order.setItems(new ArrayList<>(items));
        return this;
    }

    public OrderBuilder total(BigDecimal total) {
        order.setTotalAmount(total);
        return this;
    }

    public Order build() {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order must contain at least one item.");
        }
        return order;
    }
}
