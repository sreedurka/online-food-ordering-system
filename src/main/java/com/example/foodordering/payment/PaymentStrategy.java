package com.example.foodordering.payment;

import com.example.foodordering.entity.Order;

public interface PaymentStrategy {
    PaymentResult pay(Order order);
}
