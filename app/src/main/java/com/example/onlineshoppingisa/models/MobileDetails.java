package com.example.onlineshoppingisa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class MobileDetails implements Parcelable {
    private String categouryId;
    private String key;
    private String name;
    private String desc1;
    private String desc2;
    private String image;
    private String memoryRam;
    private String numberOfSIM;
    private String price;
    private String rating;
    private String storageCapacity;

    protected MobileDetails(Parcel in) {
        categouryId = in.readString();
        key = in.readString();
        name = in.readString();
        desc1 = in.readString();
        desc2 = in.readString();
        image = in.readString();
        memoryRam = in.readString();
        numberOfSIM = in.readString();
        price = in.readString();
        rating = in.readString();
        storageCapacity = in.readString();
    }

    public static final Creator<MobileDetails> CREATOR = new Creator<MobileDetails>() {
        @Override
        public MobileDetails createFromParcel(Parcel in) {
            return new MobileDetails(in);
        }

        @Override
        public MobileDetails[] newArray(int size) {
            return new MobileDetails[size];
        }
    };

    public String getCategouryId() {
        return categouryId;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MobileDetails() {
    }

    public String getName() {
        return name;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public String getImage() {
        return image;
    }

    public String getMemoryRam() {
        return memoryRam;
    }

    public String getNumberOfSIM() {
        return numberOfSIM;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categouryId);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(desc1);
        dest.writeString(desc2);
        dest.writeString(image);
        dest.writeString(memoryRam);
        dest.writeString(numberOfSIM);
        dest.writeString(price);
        dest.writeString(rating);
        dest.writeString(storageCapacity);
    }
}
