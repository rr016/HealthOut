package com.example.android.healthout.dataEntities;

import java.io.Serializable;

public class AppLog implements Serializable {
    private  long log_id;
    private long steps_walked;
    private double miles_walked;
    private long calories_burned;
    private long calories_consumed;
    private long pulse;
    private String date;

    public AppLog(){
        this.log_id = -1;
        this.steps_walked = -1;
        this.miles_walked = -1;
        this.calories_burned = -1;
        this.calories_consumed = -1;
        this.pulse = -1;
        this.date = null;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public long getSteps_walked() {
        return steps_walked;
    }

    public void setSteps_walked(long steps_walked) {
        this.steps_walked = steps_walked;
    }

    public double getMiles_walked() {
        return miles_walked;
    }

    public void setMiles_walked(double miles_walked) {
        this.miles_walked = miles_walked;
    }

    public long getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(long calories_burned) {
        this.calories_burned = calories_burned;
    }

    public long getCalories_consumed() {
        return calories_consumed;
    }

    public void setCalories_consumed(long calories_consumed) { this.calories_consumed = calories_consumed; }

    public long getPulse() {
        return pulse;
    }

    public void setPulse(long pulse) {
        this.pulse = pulse;
    }

    public String getDate() {
        return date;
    }

    public void setDate (String date) { this.date = date; }

    public void combineAppLogs (AppLog appLog){
        this.log_id = -1;
        this.steps_walked += appLog.getSteps_walked();
        this.miles_walked += appLog.getMiles_walked();
        this.calories_burned += appLog.getCalories_burned();
        this.calories_consumed += appLog.getCalories_consumed();
        this.pulse += appLog.getPulse();
    }

    public void averagePulse (int numAddedTogether){
        this.pulse = pulse/numAddedTogether;
    }

    public String getAppLogInfo(){
        String info = getSteps_walked() + "; " + getMiles_walked() + "; " + getCalories_burned() + "; "
                + getCalories_consumed() + getPulse();
        return info;
    }

}
