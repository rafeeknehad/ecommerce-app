package com.example.onlineshoppingisa.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "productId"})
public class ProductRoom {

    @NonNull
    private String userId;

    @NonNull
    private String productId;

    public ProductRoom(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }
}
