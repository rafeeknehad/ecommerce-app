package com.example.onlineshoppingisa.models;

import com.example.onlineshoppingisa.roomdatabase.ProductRoom;

import java.util.List;

public class OrderGroup {
    private String date;

    private List<ProductRoom> productRooms;

    public OrderGroup() {
    }

    public OrderGroup(String date, List<ProductRoom> productRooms) {
        this.date = date;
        this.productRooms = productRooms;
    }

    public String getDate() {
        return date;
    }

    public List<ProductRoom> getProductRooms() {
        return productRooms;
    }
}
