package com.example.foodordering.payment;

import com.example.foodordering.entity.PaymentMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStrategyFactoryTest {

    @Test
    void factoryReturnsCorrectStrategy() {
        PaymentStrategyFactory factory = new PaymentStrategyFactory();

        assertInstanceOf(CashPaymentStrategy.class,
                factory.getStrategy(PaymentMethod.COD));

        assertInstanceOf(UpiPaymentStrategy.class,
                factory.getStrategy(PaymentMethod.UPI));

        assertInstanceOf(CardPaymentStrategy.class,
                factory.getStrategy(PaymentMethod.CARD));
    }
}
