package com.example.onlineshoppingisa.util;

public class OrderDetailsOfOrderFragment {
    private String productImage;
    private String productName;
    private String productQuantity;
    private String productTotalPrice;

    public OrderDetailsOfOrderFragment(String productImage, String productName, String productQuantity, String productTotalPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }
}
