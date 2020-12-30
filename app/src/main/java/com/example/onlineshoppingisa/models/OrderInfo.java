package com.example.onlineshoppingisa.models;

public class OrderInfo {
    private String productQuantity;

    private String productPrice;

    private String DataDeliver;

    private String location;

    public OrderInfo(String productQuantity, String productPrice, String dataDeliver, String location) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        DataDeliver = dataDeliver;
        this.location = location;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getDataDeliver() {
        return DataDeliver;
    }

    public String getLocation() {
        return location;
    }
}
