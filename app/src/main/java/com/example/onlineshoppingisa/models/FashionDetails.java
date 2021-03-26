package com.example.onlineshoppingisa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

public class FashionDetails implements Parcelable {
    private String categoryId;
    private String key;
    private String name;
    private String desc1;
    private String desc2;
    private String image;
    private String price;
    private String rating;

    public FashionDetails() {
    }

    protected FashionDetails(Parcel in) {
        categoryId = in.readString();
        key = in.readString();
        name = in.readString();
        desc1 = in.readString();
        desc2 = in.readString();
        image = in.readString();
        price = in.readString();
        rating = in.readString();
    }

    public static final Creator<FashionDetails> CREATOR = new Creator<FashionDetails>() {
        @Override
        public FashionDetails createFromParcel(Parcel in) {
            return new FashionDetails(in);
        }

        @Override
        public FashionDetails[] newArray(int size) {
            return new FashionDetails[size];
        }
    };

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(desc1);
        dest.writeString(desc2);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(rating);
    }
}
