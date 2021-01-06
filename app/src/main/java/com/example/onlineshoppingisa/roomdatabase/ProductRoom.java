package com.example.onlineshoppingisa.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.google.firebase.firestore.Exclude;

@Entity(primaryKeys = {"userId", "productId"})
public class ProductRoom {

    @NonNull
    private String userId;

    @NonNull
    private String productId;

    @NonNull
    private ConfirmOrder confirmOrder;

    @Ignore
    public ProductRoom() {
    }

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
