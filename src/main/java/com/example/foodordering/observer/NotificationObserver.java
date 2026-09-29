package com.example.foodordering.observer;

import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class NotificationObserver implements OrderObserver {

    @Override
    public void onStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        System.out.printf(
                "[NOTIFICATION] Customer=%s, Order #%d, Status: %s -> %s%n",
                order.getCustomerName(),
                order.getId(),
                oldStatus,
                newStatus
        );
    }
}
