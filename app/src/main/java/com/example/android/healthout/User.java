package com.example.android.healthout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private boolean logged_in;
    private long user_id;
    private String email;
    private String password;
    List<ThirdPartyApp> appList = new ArrayList<ThirdPartyApp>();
    List<Goal> goalList = new ArrayList<Goal>();

    public User() {
        this.logged_in = false;
        this.user_id = -1;
        this.email = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailValid(String email){
        if (email.contains("@") && email.contains("."))
            return true;

        return false;
    }

    public boolean isPasswordValid(String password, String confirmPassword){
        if (password.length() >= 5 && password.length() <= 20)
            if (password.equals(confirmPassword))
                return true;

        return false;
    }
}

