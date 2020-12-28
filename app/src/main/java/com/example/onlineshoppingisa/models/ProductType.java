package com.example.onlineshoppingisa.models;

import com.google.firebase.firestore.Exclude;

public class ProductType {
    private String key;

    private String type;

    public ProductType() {
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }
}
