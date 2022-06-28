package com.reail.point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUser{
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("subscribed")
    @Expose
    private String subscribed;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("loginDate")
    @Expose
    private String loginDate;
    @SerializedName("phone_number")
    @Expose
    private String phone_number;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("email")
    @Expose
    private String email;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
