package com.example.onlineshoppingisa.models;

import java.util.List;

public class ProductDetailCardViewGroup {
    private String productType;
    private List<ProductDetailCardView> productDetailCardViews;

    public ProductDetailCardViewGroup(String productType, List<ProductDetailCardView> productDetailCardViews) {
        this.productType = productType;
        this.productDetailCardViews = productDetailCardViews;
    }

    public String getProductType() {
        return productType;
    }

    public List<ProductDetailCardView> getProductTypeList() {
        return productDetailCardViews;
    }
}
