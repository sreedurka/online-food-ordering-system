package com.example.foodordering.observer;

import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderStatus;

public interface OrderObserver {
    void onStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus);
}
