package com.example.android.healthout.dataEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ThirdPartyAppAndApi implements Serializable {
    private long api_id;
    private long app_id;
    private String app_name;
    private boolean registered;
    private String app_username;
    private String app_email;
    private String app_password;
    public List<AppLog> appLog = new ArrayList<AppLog>();

    public ThirdPartyAppAndApi() {
        this.api_id = -1;
        this.app_id = -1;
        this.app_name = null;
        this.registered = false;
        this.app_username = null;
        this.app_email = null;
        this.app_password = null;
    }
    public long getApi_id() {
        return api_id;
    }

    public void setApi_id(long api_id) {
        this.api_id = api_id;
    }

    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getApp_username() {
        return app_username;
    }

    public void setApp_username(String app_username) {
        this.app_username = app_username;
    }

    public String getApp_email() {
        return app_email;
    }

    public void setApp_email(String app_email) {
        this.app_email = app_email;
    }

    public String getApp_password() {
        return app_password;
    }

    public void setApp_password(String app_password) {
        this.app_password = app_password;
    }

}

