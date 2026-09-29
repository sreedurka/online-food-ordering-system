package com.example.foodordering.payment;

import com.example.foodordering.entity.Order;
import java.util.UUID;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(Order order) {
        return new PaymentResult(
                true,
                "COD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "Cash on delivery selected."
        );
    }
}
