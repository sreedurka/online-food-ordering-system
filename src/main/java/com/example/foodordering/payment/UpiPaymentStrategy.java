package com.example.foodordering.payment;

import com.example.foodordering.entity.Order;
import java.util.UUID;

public class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(Order order) {
        return new PaymentResult(
                true,
                "UPI-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "UPI payment simulated successfully."
        );
    }
}
