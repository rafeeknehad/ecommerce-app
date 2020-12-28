package com.example.onlineshoppingisa.models;

public class ProductDetailCardView {
    private String productType;
    private String productName;
    private String productPrice;
    private String productRating;
    private String productImage;

    public ProductDetailCardView(String productType, String productName, String productPrice, String productRating, String productImage) {
        this.productType = productType;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productRating = productRating;
        this.productImage = productImage;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductRating() {
        return productRating;
    }

    public String getProductImage() {
        return productImage;
    }
}
