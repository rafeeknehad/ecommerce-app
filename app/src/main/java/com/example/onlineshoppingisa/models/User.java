package com.example.onlineshoppingisa.models;

import android.net.Uri;

import com.google.firebase.firestore.Exclude;

public class User {
    private String userAuthId;
    private String email;
    private String userName;
    private String pass;
    private String job;
    private String gender;
    private String birthData;
    private String idKey;
    private String userImage;

    public User() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthData(String birthData) {
        this.birthData = birthData;
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
    }

    public User(String email, String userName, String pass, String job, String gender, String birthData) {
        this.email = email;
        this.userName = userName;
        this.pass = pass;
        this.job = job;
        this.gender = gender;
        this.birthData = birthData;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getUserAuthId() {
        return userAuthId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public String getJob() {
        return job;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthData() {
        return birthData;
    }
    @Exclude
    public String getIdKey() {
        return idKey;
    }
}
