package com.example.android.healthout;

import java.io.Serializable;

public class AppLog implements Serializable {
    private int steps_walked;
    private int miles_walked;
    private int calories_burned;
    private int calories_consumed;
    private int pulse;
    private String blood_pressure;
    private int timestamp;

    public AppLog(){
        this.steps_walked = -1;
        this.miles_walked = -1;
        this.calories_burned = -1;
        this.calories_consumed = -1;
        this.pulse = -1;
        this.blood_pressure = null;
        this.timestamp = -1;
    }

    public int getSteps_walked() {
        return steps_walked;
    }

    public void setSteps_walked(int steps_walked) {
        this.steps_walked = steps_walked;
    }

    public int getMiles_walked() {
        return miles_walked;
    }

    public void setMiles_walked(int miles_walked) {
        this.miles_walked = miles_walked;
    }

    public int getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(int calories_burned) {
        this.calories_burned = calories_burned;
    }

    public int getCalories_consumed() {
        return calories_consumed;
    }

    public void setCalories_consumed(int calories_consumed) {
        this.calories_consumed = calories_consumed;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
