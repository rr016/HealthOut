package com.example.android.healthout.dataEntities;

import com.example.android.healthout.database.DatabaseHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private boolean logged_in;
    private long user_id;
    private String username;
    private String password;

    public List<String> allAppsList = new ArrayList<String>();
    public List<String> allTypesList = new ArrayList<String>();
    public List<String> allPeriodList = new ArrayList<String>();

    public List<ThirdPartyAppAndApi> appAndApiList = new ArrayList<ThirdPartyAppAndApi>();
    public List<Goal> goalList = new ArrayList<Goal>();

    public User() {
        this.logged_in = false;
        this.user_id = -1;
        this.username = null;
        this.password = null;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isusernameValid(String username){
        if (username.length() < 15 && username.length() > 0)
            return true;

        return false;
    }

    public boolean isPasswordValid(String password, String confirmPassword){
        if (password.length() >= 5 && password.length() <= 20)
            if (password.equals(confirmPassword))
                return true;

        return false;
    }

    public long getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTimeInMillis();
    }
}

