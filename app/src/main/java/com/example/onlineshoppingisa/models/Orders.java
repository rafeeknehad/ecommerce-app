package com.example.onlineshoppingisa.models;

public class Orders {
    private String date;
    private String customerId;
    private String address;

    public Orders() {
    }

    public Orders(String date, String customerId, String address) {
        this.date = date;
        this.customerId = customerId;
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

}
