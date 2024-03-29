package com.example.onlineshoppingisa.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"userId", "productId"})
public class ProductRoom {

    private String userId;

    private String productId;

    private Integer quantity;

    @Ignore
    public ProductRoom() {
    }

    public ProductRoom(Integer quantity, String userId, String productId) {
        this.quantity = quantity;
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
