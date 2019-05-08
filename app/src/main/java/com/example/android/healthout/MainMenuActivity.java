package com.example.android.healthout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.AppLog;
import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainMenuActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    long user_id;

    public List<AppLog> fitbitAppLogList = new ArrayList<AppLog>();
    public List<AppLog> googleFitAppLogList = new ArrayList<AppLog>();

    Button updateButton;
    Button registerAppsButton;
    Button editGoalsButton;
    Button inputLogButton;
    ListView listView;

    String[] goalItem;
    String[] item_type;
    String[] item_app;
    String[] item_period;
    String[] item_target;
    String[] item_progress;
    Integer[] imageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DatabaseHelper(this);

        user = (User)getIntent().getSerializableExtra("user");
        user_id = user.getUser_id();

        user.goalList = db.getGoalListFromGoalTable(user_id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        fitbitAppLogList = db.getAppLogListFromLogTable(user_id, 1);
        googleFitAppLogList = db.getAppLogListFromLogTable(user_id, 2);


        // Log Table -- Display
        Toast.makeText(getApplicationContext(), "Log Table:", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < fitbitAppLogList.size(); i++)
            Toast.makeText(MainMenuActivity.this, fitbitAppLogList.get(i).getAppLogInfo(), Toast.LENGTH_SHORT).show();


        updateButton = findViewById(R.id.button_update);
        registerAppsButton = findViewById(R.id.button_register_apps);
        editGoalsButton = findViewById(R.id.button_edit_goals);
        inputLogButton = findViewById(R.id.button_input_log);
        listView = findViewById(R.id.ListView_display_goals);

        // For Clickable ListView Goals
        goalItem = new String[user.goalList.size()];
        item_type = new String[user.goalList.size()];
        item_app = new String[user.goalList.size()];
        item_period = new String[user.goalList.size()];
        item_target = new String[user.goalList.size()];
        item_progress = new String[user.goalList.size()];
        imageID = new Integer[user.goalList.size()];

        if (user.goalList.size() < 1){
            goalItem = new String[] {"No goals created"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goalItem);
            listView.setAdapter(adapter);
        }
        else{
            for (int i = 0; i < goalItem.length; i++) {
                item_type[i] = user.goalList.get(i).getType_name();
                item_app[i] = user.goalList.get(i).getApp_name();
                item_period[i] = user.goalList.get(i).getPeriod_length();
                item_target[i] = user.goalList.get(i).getTarget_value();

                if (user.goalList.get(i).getApp_id() == 1){
                    if (fitbitAppLogList.size() < 1){
                        item_progress[i] = "0";
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 1) {
                        switch (user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = Long.toString(fitbitAppLogList.get(fitbitAppLogList.size() - 1).getSteps_walked());
                                break;
                            case "Miles Walked":
                                item_progress[i] = String.format("%.2f", fitbitAppLogList.get(fitbitAppLogList.size() - 1).getMiles_walked());
                                break;
                            case "Calories Burned":
                                item_progress[i] = Long.toString(fitbitAppLogList.get(fitbitAppLogList.size() - 1).getCalories_burned());
                                break;
                            case "Calories Consumed":
                                item_progress[i] = Long.toString(fitbitAppLogList.get(fitbitAppLogList.size() - 1).getCalories_consumed());
                                break;
                            case "Pulse":
                                item_progress[i] = Long.toString(fitbitAppLogList.get(fitbitAppLogList.size() - 1).getPulse());
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 2){
                        switch(user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = getWeeklyProgress(fitbitAppLogList, 1);
                                break;
                            case "Miles Walked":
                                item_progress[i] = getWeeklyProgress(fitbitAppLogList, 2);
                                break;
                            case "Calories Burned":
                                item_progress[i] = getWeeklyProgress(fitbitAppLogList, 3);
                                break;
                            case "Calories Consumed":
                                item_progress[i] = getWeeklyProgress(fitbitAppLogList, 4);
                                break;
                            case "Pulse":
                                item_progress[i] = getWeeklyProgress(fitbitAppLogList, 5);
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 3){
                        switch(user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = getMonthlyProgress(fitbitAppLogList, 1);
                                break;
                            case "Miles Walked":
                                item_progress[i] = getMonthlyProgress(fitbitAppLogList, 2);
                                break;
                            case "Calories Burned":
                                item_progress[i] = getMonthlyProgress(fitbitAppLogList, 3);
                                break;
                            case "Calories Consumed":
                                item_progress[i] = getMonthlyProgress(fitbitAppLogList, 4);
                                break;
                            case "Pulse":
                                item_progress[i] = getMonthlyProgress(fitbitAppLogList, 5);
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                }
                else if (user.goalList.get(i).getApp_id() == 2){
                    if (googleFitAppLogList.size() < 1){
                        item_progress[i] = "0";
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 1) {
                        switch(user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = Long.toString(googleFitAppLogList.get(googleFitAppLogList.size() - 1).getSteps_walked());
                                break;
                            case "Miles Walked":
                                item_progress[i] =  String.format("%.2f", googleFitAppLogList.get(googleFitAppLogList.size() - 1).getMiles_walked());
                                break;
                            case "Calories Burned":
                                item_progress[i] = Long.toString(googleFitAppLogList.get(googleFitAppLogList.size() - 1).getCalories_burned());
                                break;
                            case "Calories Consumed":
                                item_progress[i] = Long.toString(googleFitAppLogList.get(googleFitAppLogList.size() - 1).getCalories_consumed());
                                break;
                            case "Pulse":
                                item_progress[i] = Long.toString(googleFitAppLogList.get(googleFitAppLogList.size() - 1).getPulse());
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 2) {
                        switch(user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = getWeeklyProgress(googleFitAppLogList, 1);
                                break;
                            case "Miles Walked":
                                item_progress[i] = getWeeklyProgress(googleFitAppLogList, 2);
                                break;
                            case "Calories Burned":
                                item_progress[i] = getWeeklyProgress(googleFitAppLogList, 3);
                                break;
                            case "Calories Consumed":
                                item_progress[i] = getWeeklyProgress(googleFitAppLogList, 4);
                                break;
                            case "Pulse":
                                item_progress[i] = getWeeklyProgress(googleFitAppLogList, 5);
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                    else if (user.goalList.get(i).getPeriod_id() == 3){
                        switch(user.goalList.get(i).getType_name()) {
                            case "Steps Walked":
                                item_progress[i] = getMonthlyProgress(googleFitAppLogList, 1);
                                break;
                            case "Miles Walked":
                                item_progress[i] = getMonthlyProgress(googleFitAppLogList, 2);
                                break;
                            case "Calories Burned":
                                item_progress[i] = getMonthlyProgress(googleFitAppLogList, 3);
                                break;
                            case "Calories Consumed":
                                item_progress[i] = getMonthlyProgress(googleFitAppLogList, 4);
                                break;
                            case "Pulse":
                                item_progress[i] = getMonthlyProgress(googleFitAppLogList, 5);
                                break;
                            default:
                                item_progress[i] = "[prog]";
                                break;
                        }
                    }
                }

                switch(user.goalList.get(i).getType_name()) {
                    case "Steps Walked":
                        imageID[i] = R.drawable.icon_goaltype_steps;
                        break;
                    case "Miles Walked":
                        imageID[i] = R.drawable.icon_goaltype_steps;
                        break;
                    case "Calories Burned":
                        imageID[i] = R.drawable.icon_goaltype_calories_burned;
                        break;
                    case "Calories Consumed":
                        imageID[i] = R.drawable.icon_goaltype_calories_consumed;
                        break;
                    case "Pulse":
                        imageID[i] = R.drawable.icon_goaltype_pulse;
                        break;
                    default:
                        imageID[i] = R.drawable.icon_delete_account;
                        break;
                }
            }
            CustomListMainMenu adapter = new CustomListMainMenu(MainMenuActivity.this,
                    item_type, item_app, item_period, item_target, item_progress, imageID);
            listView.setAdapter(adapter);


            // Click a Goal
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent moveToGraph = new Intent(MainMenuActivity.this, GraphActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("user", getIntent().getSerializableExtra("user"));
                    extras.putLong("app_id", user.goalList.get(position).getApp_id());
                    extras.putLong("type_id", user.goalList.get(position).getType_id());
                    extras.putLong("period_id", user.goalList.get(position).getPeriod_id());
                    extras.putString("target_value", user.goalList.get(position).getTarget_value());
                    moveToGraph.putExtras(extras);
                    startActivity(moveToGraph);
                }
            });
        }

        // Click Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wipes data clean first
                db.deleteUserFromGoalTable(user_id);
                db.deleteUserFromDateTable(user_id);
                db.deleteUserFromLogTable(user_id);

                // --- Fitbit ---
                // Daily
                db.addGoalToGoalTable(user_id, 1, 1, 1, "3000");
                db.addGoalToGoalTable(user_id, 1, 2, 1, "3");
                db.addGoalToGoalTable(user_id, 1, 3, 1, "2500");
                db.addGoalToGoalTable(user_id, 1, 4, 1, "2200");
                db.addGoalToGoalTable(user_id, 1, 5, 1, "80");
                // Weekly
                db.addGoalToGoalTable(user_id, 1, 1, 2, "18000");
                db.addGoalToGoalTable(user_id, 1, 2, 2, "18");
                db.addGoalToGoalTable(user_id, 1, 3, 2, "15000");
                db.addGoalToGoalTable(user_id, 1, 4, 2, "12000");
                db.addGoalToGoalTable(user_id, 1, 5, 2, "80");
                // Monthly
                db.addGoalToGoalTable(user_id, 1, 1, 3, "70000");
                db.addGoalToGoalTable(user_id, 1, 2, 3, "70");
                db.addGoalToGoalTable(user_id, 1, 3, 3, "55000");
                db.addGoalToGoalTable(user_id, 1, 4, 3, "45000");
                db.addGoalToGoalTable(user_id, 1, 5, 3, "80");

                // --- Google Fit ---
                // Daily
                db.addGoalToGoalTable(user_id, 2, 1, 1, "3000");
                db.addGoalToGoalTable(user_id, 2, 2, 1, "3");
                db.addGoalToGoalTable(user_id, 2, 3, 1, "2500");
                db.addGoalToGoalTable(user_id, 2, 4, 1, "2200");
                db.addGoalToGoalTable(user_id, 2, 5, 1, "80");
                // Weekly
                db.addGoalToGoalTable(user_id, 2, 1, 2, "18000");
                db.addGoalToGoalTable(user_id, 2, 2, 2, "18");
                db.addGoalToGoalTable(user_id, 2, 3, 2, "15000");
                db.addGoalToGoalTable(user_id, 2, 4, 2, "12000");
                db.addGoalToGoalTable(user_id, 2, 5, 2, "80");
                // Monthly
                db.addGoalToGoalTable(user_id, 2, 1, 3, "70000");
                db.addGoalToGoalTable(user_id, 2, 2, 3, "70");
                db.addGoalToGoalTable(user_id, 2, 3, 3, "55000");
                db.addGoalToGoalTable(user_id, 2, 4, 3, "45000");
                db.addGoalToGoalTable(user_id, 2, 5, 3, "80");

                long test = -1;

                // Fitbit -- Create Random Log Data
                for (int i = -122; i < 0; i++) {
                    test = db.addLogToLogTable(user_id, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, user.getCalculatedDate(i), user.getDateString(user.getCalculatedDate(i)));
                }

                // Google Fit -- Create Random Log Data
                for (int i = -122; i < 0; i++) {
                    test = db.addLogToLogTable(user_id, 2, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, user.getCalculatedDate(i), user.getDateString(user.getCalculatedDate(i)));
                }

                if (test > 0) {
                    Toast.makeText(MainMenuActivity.this, "Random Log Data added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainMenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                // Refreshes page
                finish();
                startActivity(getIntent());
            }
        });

        // Click Register Apps Button
        registerAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToRegisterApps = new Intent(MainMenuActivity.this, RegisterAppsActivity.class).putExtra("user", user);
                startActivity(moveToRegisterApps);
            }
        });

        // Click Edit Goals Button
        editGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToEditGoals = new Intent(MainMenuActivity.this, EditGoalsActivity.class).putExtra("user", user);
                startActivity(moveToEditGoals);
            }
        });

        // Click Input Log Button
        inputLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToInputLog = new Intent(MainMenuActivity.this, InputLogActivity.class).putExtra("user", user);
                startActivity(moveToInputLog);
            }
        });
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
                        Intent moveToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(MainMenuActivity.this, EditAccountActivity.class).putExtra("user", user);
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
                        db.deleteAccountFromUserTable(user_id);
                        Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();

                        Intent moveToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
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

    public String getWeeklyProgress (List<AppLog> appLogList, long type_id) {
        String progress = "null";
        long valueLong = 0;
        double valueDouble = 0;

        if (appLogList.size() > 7) {
            for (int i = -7; i < 0; i++) {
                switch ((int) type_id) {
                    case 1:
                        valueLong += appLogList.get((appLogList.size()) + i).getSteps_walked();
                        break;
                    case 2:
                        valueDouble += appLogList.get((appLogList.size()) + i).getMiles_walked();
                        break;
                    case 3:
                        valueLong += appLogList.get((appLogList.size()) + i).getCalories_burned();
                        break;
                    case 4:
                        valueLong += appLogList.get((appLogList.size()) + i).getCalories_consumed();
                        break;
                    case 5:
                        valueLong += appLogList.get((appLogList.size()) + i).getPulse();
                        break;
                }
            }
        } else {
            for (int i = -appLogList.size(); i < 0; i++) {
                switch ((int) type_id) {
                    case 1:
                        valueLong += appLogList.get(appLogList.size() + i).getSteps_walked();
                        break;
                    case 2:
                        valueDouble += appLogList.get(appLogList.size() + i).getMiles_walked();
                        break;
                    case 3:
                        valueLong += appLogList.get(appLogList.size() + i).getCalories_burned();
                        break;
                    case 4:
                        valueLong += appLogList.get(appLogList.size() + i).getCalories_consumed();
                        break;
                    case 5:
                        valueLong += appLogList.get(appLogList.size() + i).getPulse();
                        break;
                }
            }

        }

        if (type_id == 1 || (type_id >=3 && type_id <=4))
            progress = String.valueOf(valueLong);
        else if (type_id == 2)
            progress = String.format("%.2f", valueDouble);
        else if (type_id == 5){
            valueLong = valueLong / 7;
            progress = String.valueOf(valueLong);
        }

        return progress;
    }

    public String getMonthlyProgress (List<AppLog> appLogList, long type_id) {
        String progress = "null";
        long valueLong = 0;
        double valueDouble = 0;

        if (appLogList.size() > 30) {
            for (int i = -30; i < 0; i++) {
                switch ((int) type_id) {
                    case 1:
                        valueLong += appLogList.get((appLogList.size()) + i).getSteps_walked();
                        break;
                    case 2:
                        valueDouble += appLogList.get((appLogList.size()) + i).getMiles_walked();
                        break;
                    case 3:
                        valueLong += appLogList.get((appLogList.size()) + i).getCalories_burned();
                        break;
                    case 4:
                        valueLong += appLogList.get((appLogList.size()) + i).getCalories_consumed();
                        break;
                    case 5:
                        valueLong += appLogList.get((appLogList.size()) + i).getPulse();
                        break;
                }
            }
        } else {
            for (int i = -appLogList.size(); i < 0; i++) {
                switch ((int) type_id) {
                    case 1:
                        valueLong += appLogList.get(appLogList.size() + i).getSteps_walked();
                        break;
                    case 2:
                        valueDouble += appLogList.get(appLogList.size() + i).getMiles_walked();
                        break;
                    case 3:
                        valueLong += appLogList.get(appLogList.size() + i).getCalories_burned();
                        break;
                    case 4:
                        valueLong += appLogList.get(appLogList.size() + i).getCalories_consumed();
                        break;
                    case 5:
                        valueLong += appLogList.get(appLogList.size() + i).getPulse();
                        break;
                }
            }

        }

        if (type_id == 1 || (type_id >=3 && type_id <=4))
            progress = String.valueOf(valueLong);
        else if (type_id == 2)
            progress = String.format("%.2f", valueDouble);
        else if (type_id == 5){
            valueLong = valueLong / 30;
            progress = String.valueOf(valueLong);
        }

        return progress;
    }
}
