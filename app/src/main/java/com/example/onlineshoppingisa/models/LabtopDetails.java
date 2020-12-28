package com.example.onlineshoppingisa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

public class LabtopDetails implements Parcelable {
    private String categouryId;
    private String hardDisk;
    private String desc1;
    private String desc2;
    private String image;
    private String name;
    private String price;
    private String rating;
    private String key;

    public LabtopDetails() {
    }

    protected LabtopDetails(Parcel in) {
        categouryId = in.readString();
        hardDisk = in.readString();
        desc1 = in.readString();
        desc2 = in.readString();
        image = in.readString();
        name = in.readString();
        price = in.readString();
        rating = in.readString();
        key = in.readString();
    }

    public static final Creator<LabtopDetails> CREATOR = new Creator<LabtopDetails>() {
        @Override
        public LabtopDetails createFromParcel(Parcel in) {
            return new LabtopDetails(in);
        }

        @Override
        public LabtopDetails[] newArray(int size) {
            return new LabtopDetails[size];
        }
    };

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getHardDisk() {
        return hardDisk;
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

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getKey() {
        return key;
    }

    public String getCategouryId() {
        return categouryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categouryId);
        dest.writeString(hardDisk);
        dest.writeString(desc1);
        dest.writeString(desc2);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(rating);
        dest.writeString(key);
    }
}
