package com.example.onlineshoppingisa.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Orders implements Comparable<Orders> {
    @ServerTimestamp
    private Date date;
    private String customerId;
    private String address;
    private String orderId;

    public Orders() {
    }

    public Orders(String customerId, String address) {
        this.customerId = customerId;
        this.address = address;
    }

    @Exclude
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int compareTo(Orders o) {
        return getDate().compareTo(o.getDate());

    }
}
