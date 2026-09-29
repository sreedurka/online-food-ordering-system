package com.example.foodordering.observer;

import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderStatusPublisher {

    private final List<OrderObserver> observers = new ArrayList<>();

    public OrderStatusPublisher(List<OrderObserver> observers) {
        this.observers.addAll(observers);
    }

    public void publish(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        for (OrderObserver observer : observers) {
            observer.onStatusChanged(order, oldStatus, newStatus);
        }
    }
}
