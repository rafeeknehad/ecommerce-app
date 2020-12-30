package com.example.onlineshoppingisa.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.example.onlineshoppingisa.models.OrderInfo;

@Entity(primaryKeys = {"userId", "productId"})
public class ProductRoom {

    @NonNull
    private String userId;

    @NonNull
    private String productId;

    @NonNull
    private ConfirmOrder confirmOrder;

    public ProductRoom(ConfirmOrder confirmOrder, String userId, String productId) {
        this.confirmOrder = confirmOrder;
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    @NonNull
    public ConfirmOrder getConfirmOrder() {
        return confirmOrder;
    }
}
