package com.example.foodordering.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public OrderItem() {}

    public OrderItem(MenuItem menuItem, Integer quantity, BigDecimal unitPrice) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public MenuItem getMenuItem() { return menuItem; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public void setId(Long id) { this.id = id; }
    public void setOrder(Order order) { this.order = order; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
