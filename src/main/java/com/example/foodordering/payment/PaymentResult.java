package com.example.foodordering.payment;

public record PaymentResult(boolean success, String transactionReference, String message) {}
