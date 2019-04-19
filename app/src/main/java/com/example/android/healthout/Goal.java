package com.example.android.healthout;

public class Goal extends User {
    private String app_pulled_from;
    private int app_id;
    private String goal_type;
    private int goal_id;
    private String target;
    private String period;
    private int period_id;
    private String progress;
    ThirdPartyApp thirdPartyApp = new ThirdPartyApp();

    public Goal(){
        this.app_pulled_from = null;
        this.app_id = -1;
        this.goal_type = null;
        this.goal_id = -1;
        this.target = null;
        this.period = null;
        this.period_id = -1;
        this.progress = null;
    }

    public Goal(String a, int b, String c, int d, String e, String f, int g, String h){
        this.app_pulled_from = a;
        this.app_id = b;
        this.goal_type = c;
        this.goal_id = d;
        this.target = e;
        this.period = f;
        this.period_id = g;
        this.progress = h;
    }

    public String getApp_pulled_from() {
        return app_pulled_from;
    }

    public void setApp_pulled_from(String app_pulled_from) {
        this.app_pulled_from = app_pulled_from;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(String goal_type) {
        this.goal_type = goal_type;
    }

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
