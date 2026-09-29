package com.example.foodordering.dto;

import com.example.foodordering.entity.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotBlank
    private String customerName;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;

    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public List<OrderItemRequest> getItems() { return items; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
