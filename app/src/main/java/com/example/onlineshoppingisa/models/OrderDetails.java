package com.example.onlineshoppingisa.models;

import com.google.firebase.firestore.Exclude;

public class OrderDetails {
    private String productId;
    private String quantity;
    private String orderId;
    private String orderDetailsId;

    public OrderDetails() {
    }

    public OrderDetails(String productId, String orderId, String quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    @Exclude
    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
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

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
