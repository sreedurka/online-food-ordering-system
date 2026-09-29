package com.example.foodordering.dto;

import com.example.foodordering.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class OrderStatusRequest {
    @NotNull
    private OrderStatus status;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
