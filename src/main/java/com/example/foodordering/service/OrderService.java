package com.example.foodordering.service;

import com.example.foodordering.dto.OrderItemRequest;
import com.example.foodordering.dto.OrderRequest;
import com.example.foodordering.entity.*;
import com.example.foodordering.exception.ResourceNotFoundException;
import com.example.foodordering.observer.OrderStatusPublisher;
import com.example.foodordering.payment.PaymentResult;
import com.example.foodordering.payment.PaymentStrategy;
import com.example.foodordering.payment.PaymentStrategyFactory;
import com.example.foodordering.repository.MenuItemRepository;
import com.example.foodordering.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final OrderStatusPublisher statusPublisher;

    public OrderService(OrderRepository orderRepository,
                        MenuItemRepository menuRepository,
                        PaymentStrategyFactory paymentStrategyFactory,
                        OrderStatusPublisher statusPublisher) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.paymentStrategyFactory = paymentStrategyFactory;
        this.statusPublisher = statusPublisher;
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Menu item not found: " + itemRequest.getMenuItemId()));

            if (!menuItem.isAvailable()) {
                throw new IllegalArgumentException(menuItem.getName() + " is currently unavailable.");
            }

            BigDecimal lineTotal = menuItem.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = new OrderItem(
                    menuItem,
                    itemRequest.getQuantity(),
                    menuItem.getPrice()
            );

            orderItems.add(orderItem);
            total = total.add(lineTotal);
        }

        Order order = new OrderBuilder()
                .customer(request.getCustomerName(), request.getPhone(), request.getAddress())
                .paymentMethod(request.getPaymentMethod())
                .status(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .items(orderItems)
                .total(total)
                .build();

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        Order saved = orderRepository.save(order);

        PaymentStrategy paymentStrategy =
                paymentStrategyFactory.getStrategy(request.getPaymentMethod());

        PaymentResult result = paymentStrategy.pay(saved);

        if (!result.success()) {
            throw new IllegalArgumentException("Payment failed: " + result.message());
        }

        return saved;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = getOrder(id);
        OrderStatus oldStatus = order.getStatus();

        if (oldStatus == newStatus) {
            return order;
        }

        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        statusPublisher.publish(saved, oldStatus, newStatus);
        return saved;
    }
}
