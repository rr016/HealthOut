package com.example.android.healthout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.AppLog;
import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    long app_id;
    long type_id;
    long period_id;
    String target_value;

    public List<AppLog> appLogList = new ArrayList<AppLog>();
    public List<AppLog> appLogListToBeGraphed = new ArrayList<AppLog>();

    LineGraphSeries<DataPoint> logDataLine;
    LineGraphSeries<DataPoint> targetValueLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = (User)extras.getSerializable("user");
        app_id = extras.getLong("app_id");
        type_id = extras.getLong("type_id");
        period_id = extras.getLong("period_id");
        target_value = extras.getString("target_value");

        appLogList = db.getAppLogListFromLogTable(user.getUser_id(), app_id);

        switch ((int)period_id){
            case 1:
                appLogListToBeGraphed = createDailyAppLogList(appLogList);
                break;
            case 2:
                appLogListToBeGraphed = createWeeklyAppLogList(appLogList);
                break;
            case 3:
                appLogListToBeGraphed = createMonthlyAppLogList(appLogList);
                break;
        }

        long dataLong = -1;
        double dataDouble = -1.0;
        String dataString = null;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        logDataLine = new LineGraphSeries<DataPoint>();
        targetValueLine = new LineGraphSeries<DataPoint>();

        // Log Table -- Display
        Toast.makeText(getApplicationContext(), "Log Table:", Toast.LENGTH_LONG).show();
        for (int i = 0; i < appLogListToBeGraphed.size(); i++)
            Toast.makeText(GraphActivity.this, appLogListToBeGraphed.get(i).getAppLogInfo(), Toast.LENGTH_SHORT).show();

        int entries = appLogListToBeGraphed.size();

        for(int i = 0; i < entries; i++){
            switch ((int)type_id){
                case 1:
                    dataLong = appLogListToBeGraphed.get(i).getSteps_walked();
                    break;
                case 2:
                    dataDouble = appLogListToBeGraphed.get(i).getMiles_walked();
                    break;
                case 3:
                    dataLong = appLogListToBeGraphed.get(i).getCalories_burned();
                    break;
                case 4:
                    dataLong = appLogListToBeGraphed.get(i).getCalories_consumed();
                    break;
                case 5:
                    dataLong = appLogListToBeGraphed.get(i).getPulse();
                    break;
            }

            if (type_id == 1 || (type_id >=3 && type_id <=5))
                logDataLine.appendData(new DataPoint(i, dataLong), true, entries);
            else if (type_id == 2)
                logDataLine.appendData(new DataPoint(i, dataDouble), true, entries);


            targetValueLine.appendData(new DataPoint(i, Integer.valueOf(target_value)), true, entries); // striaght line at target value
        }

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        if (type_id == 1){
            gridLabel.setVerticalAxisTitle("Steps Walked");
        }
        else if (type_id == 2){
            gridLabel.setVerticalAxisTitle("Miles Walked");
        }
        else if (type_id == 3){
            gridLabel.setVerticalAxisTitle("Calories Burned");
        }
        else if (type_id == 4){
            gridLabel.setVerticalAxisTitle("Calories Consumed");
        }
        else if (type_id == 5){
            gridLabel.setVerticalAxisTitle("Pulse");
        }

        if (period_id == 1){
            gridLabel.setHorizontalAxisTitle("Days");
        }
        else if (period_id == 2){
            gridLabel.setHorizontalAxisTitle("Weeks");
        }
        else if (period_id == 3){
            gridLabel.setHorizontalAxisTitle("Months");
        }

        switch((int) period_id) {
            case 1: // Daily
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(7);
                break;
            case 2: // Weekly
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(4);
                break;
            case 3: // Monthly
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(4);
                break;
        }
        graph.getViewport().setXAxisBoundsManual(true);

        targetValueLine.setColor(Color.rgb(255, 0, 0));
        graph.addSeries(logDataLine);
        graph.addSeries(targetValueLine);
    }

    /************************ MENU BAR ************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Home - Main Menu
            case R.id.home:
                Intent moveToMainMenu = new Intent(GraphActivity.this, MainMenuActivity.class).putExtra("user", user);
                startActivity(moveToMainMenu);
                return true;

            // Logout
            case R.id.one:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent moveToLogin = new Intent(GraphActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(GraphActivity.this, EditAccountActivity.class).putExtra("user", user);
                startActivity(moveToEditAccount);
                return true;

            // Delete Account
            case R.id.three:
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this account?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "USER_ID = "+user.getUser_id(), Toast.LENGTH_LONG).show();
                        db.deleteAccountFromUserTable(user.getUser_id());

                        Intent moveToLogin = new Intent(GraphActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<AppLog> createDailyAppLogList(List<AppLog> appLogList) {
        List<AppLog> dailyAppLogList = new ArrayList<AppLog>();

        if (appLogList.size() > 7){
            for (int i = -7; i < 0; i++){
                dailyAppLogList.add(appLogList.get(appLogList.size() + i));
            }
        }
        else{
            for (int i = - appLogList.size(); i < 0; i++){
                dailyAppLogList.add(appLogList.get(appLogList.size() + i));
            }
        }

        return dailyAppLogList;
    }

    public List<AppLog> createWeeklyAppLogList(List<AppLog> appLogList) {
        List<AppLog> weeklyAppLogList = new ArrayList<AppLog>();
        AppLog cumulativeAppLog;

        if (appLogList.size() >= 28){
            cumulativeAppLog = appLogList.get(appLogList.size() -28);
            for (int i = -27; i < -21; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(7);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        weeklyAppLogList.add(cumulativeAppLog);

        if (appLogList.size() >= 21){
            cumulativeAppLog = appLogList.get(appLogList.size() -21);
            for (int i = -20; i < -14; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(7);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        weeklyAppLogList.add(cumulativeAppLog);

        if (appLogList.size() >= 14){
            cumulativeAppLog = appLogList.get(appLogList.size() -14);
            for (int i = -13; i < -7; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(7);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        weeklyAppLogList.add(cumulativeAppLog);

        if (appLogList.size() >= 7){
            cumulativeAppLog = appLogList.get(appLogList.size() -7);
            for (int i = -6; i < 0; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(7);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        weeklyAppLogList.add(cumulativeAppLog);

        return weeklyAppLogList;
    }

    public List<AppLog> createMonthlyAppLogList(List<AppLog> appLogList) {
        List<AppLog> monthlyAppLogList = new ArrayList<AppLog>();
        AppLog cumulativeAppLog;
        
        if (appLogList.size() >= 120){
            cumulativeAppLog = appLogList.get(appLogList.size() -120);
            for (int i = -119; i < -90; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(30);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        monthlyAppLogList.add(cumulativeAppLog);
            
        
        if (appLogList.size() >= 90){
            cumulativeAppLog = appLogList.get(appLogList.size() -90);
            for (int i = -89; i < -60; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(30);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        monthlyAppLogList.add(cumulativeAppLog);

        if (appLogList.size() >= 60){
            cumulativeAppLog = appLogList.get(appLogList.size() -60);
            for (int i = -59; i < -30; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(30);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        monthlyAppLogList.add(cumulativeAppLog);

        if (appLogList.size() >= 30){
            cumulativeAppLog = appLogList.get(appLogList.size() -30);
            for (int i = -29; i < 0; i++){
                cumulativeAppLog.combineAppLogs(appLogList.get(appLogList.size() + i));
            }
            cumulativeAppLog.averagePulse(30);
        }
        else{
            cumulativeAppLog = new AppLog();
        }
        monthlyAppLogList.add(cumulativeAppLog);

        return monthlyAppLogList;
    }
}