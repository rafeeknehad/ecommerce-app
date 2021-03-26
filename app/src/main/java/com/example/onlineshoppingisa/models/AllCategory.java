package com.example.onlineshoppingisa.models;

import java.util.List;

public class AllCategory {
    private List<MobileDetails> mobileDetailsList;
    private List<ProductType> productTypeList;
    private List<FashionDetails> fashionDetailsList;
    private List<LaptopDetails> laptopDetails;

    public AllCategory(List<ProductType> productTypeList, List<MobileDetails> mobileDetailsList, List<FashionDetails> fashionDetailsList, List<LaptopDetails> laptopDetails) {
        this.mobileDetailsList = mobileDetailsList;
        this.productTypeList = productTypeList;
        this.fashionDetailsList = fashionDetailsList;
        this.laptopDetails = laptopDetails;
    }

    public List<MobileDetails> getMobileDetailsList() {
        return mobileDetailsList;
    }

    public List<ProductType> getProductTypeList() {
        return productTypeList;
    }

    public List<FashionDetails> getFashionDetailsList() {
        return fashionDetailsList;
    }

    public List<LaptopDetails> getLaptopDetails() {
        return laptopDetails;
    }
}
