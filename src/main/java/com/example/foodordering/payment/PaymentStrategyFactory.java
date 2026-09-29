package com.example.foodordering.payment;

import com.example.foodordering.entity.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {

    public PaymentStrategy getStrategy(PaymentMethod method) {
        return switch (method) {
            case COD -> new CashPaymentStrategy();
            case UPI -> new UpiPaymentStrategy();
            case CARD -> new CardPaymentStrategy();
        };
    }
}
