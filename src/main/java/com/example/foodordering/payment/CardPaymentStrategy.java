package com.example.foodordering.payment;

import com.example.foodordering.entity.Order;
import java.util.UUID;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(Order order) {
        return new PaymentResult(
                true,
                "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "Card payment simulated successfully."
        );
    }
}
