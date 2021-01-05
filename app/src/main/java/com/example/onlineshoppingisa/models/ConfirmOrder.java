package com.example.onlineshoppingisa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfirmOrder implements Parcelable {

    private String productId;
    private String productImage;
    private String getProductQuantity;
    private String productPrice;
    private String latitude;
    private String longitude;
    private String productName;
    private String productDeliverDate;
    private String orderDetailId;
    private String orderId;

    public String getProductDeliverDate() {
        return productDeliverDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public ConfirmOrder(String productId, String productImage, String getProductQuantity, String productPrice, String latitude, String longitude, String productName, String productDeliverDate) {
        this.productId = productId;
        this.productImage = productImage;
        this.getProductQuantity = getProductQuantity;
        this.productPrice = productPrice;
        this.latitude = latitude;
        this.longitude = longitude;
        this.productName = productName;
        this.productDeliverDate = productDeliverDate;
    }

    protected ConfirmOrder(Parcel in) {
        productId = in.readString();
        productImage = in.readString();
        getProductQuantity = in.readString();
        productPrice = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        productName = in.readString();
    }



    public static final Creator<ConfirmOrder> CREATOR = new Creator<ConfirmOrder>() {
        @Override
        public ConfirmOrder createFromParcel(Parcel in) {
            return new ConfirmOrder(in);
        }

        @Override
        public ConfirmOrder[] newArray(int size) {
            return new ConfirmOrder[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getGetProductQuantity() {
        return getProductQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productImage);
        dest.writeString(getProductQuantity);
        dest.writeString(productPrice);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(productName);
    }
}
