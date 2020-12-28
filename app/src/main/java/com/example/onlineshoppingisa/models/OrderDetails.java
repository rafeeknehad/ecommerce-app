package com.example.onlineshoppingisa.models;

public class OrderDetails {
    private String productId;
    private String quantity;
    private String orderId;

    public OrderDetails() {
    }

    public OrderDetails(String productId, String orderId, String quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }
}
