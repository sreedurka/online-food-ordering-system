package com.example.foodordering.controller;

import com.example.foodordering.dto.OrderRequest;
import com.example.foodordering.dto.OrderStatusRequest;
import com.example.foodordering.entity.Order;
import com.example.foodordering.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @Valid @RequestBody OrderStatusRequest request) {
        return orderService.updateStatus(id, request.getStatus());
    }
}
