package com.example.android.healthout;

import java.io.Serializable;

public class Goal implements Serializable {
    private int app_id;
    private String app_name;
    private int type_id;
    private String type_name;
    private int period_id;
    private String period_length;
    private String target_value;
    private String progress;
    ThirdPartyAppAndApi thirdPartyApp = new ThirdPartyAppAndApi();

    public Goal(){
        this.app_id = -1;
        this.app_name = null;
        this.type_id = -1;
        this.type_name = null;
        this.period_id = -1;
        this.period_length = null;
        this.target_value = null;
        this.progress = null;
    }

    public Goal(int app_id, String app_name, int type_id, String type_name, int period_id, String period_length, String target_value, String preogress){
        this.app_id = app_id;
        this.app_name = app_name;
        this.type_id = type_id;
        this.type_name = type_name;
        this.period_id = period_id;
        this.period_length = period_length;
        this.target_value = target_value;
        this.progress = preogress;
    }


    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public String getPeriod_length() {
        return period_length;
    }

    public void setPeriod_length(String period_length) {
        this.period_length = period_length;
    }

    public String getTarget_value() {
        return target_value;
    }

    public void setTarget_value(String target_value) {
        this.target_value = target_value;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
