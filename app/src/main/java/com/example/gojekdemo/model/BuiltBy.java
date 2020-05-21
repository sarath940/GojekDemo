package com.example.gojekdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuiltBy implements Serializable {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("username")
    @Expose
    private String username;
    private final static long serialVersionUID = 8104402436738083686L;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

