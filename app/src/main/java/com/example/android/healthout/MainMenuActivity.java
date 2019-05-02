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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainMenuActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    AppLog fitbitAppLog = new AppLog();
    AppLog googleFitAppLog = new AppLog();

    Button updateButton;
    Button registerAppsButton;
    Button editGoalsButton;
    ListView listView;

    String goalItem[];
    String item_type[];
    String item_app[];
    String item_period[];
    String item_target[];
    String item_progress[];
    Integer imageID[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DatabaseHelper(this);

        user = (User)getIntent().getSerializableExtra("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        fitbitAppLog = db.getAppLogFromLogTable(user.getUser_id(), 1, date);
        googleFitAppLog = db.getAppLogFromLogTable(user.getUser_id(), 2, date);

        updateButton = findViewById(R.id.button_update);
        registerAppsButton = findViewById(R.id.button_register_apps);
        editGoalsButton = findViewById(R.id.button_edit_goals);
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
                //if(user.goalList.get(i).getProgress() == null)
                   // item_progress[i] = "[prog]";
               // else
                    //item_progress[i] = user.goalList.get(i).getProgress();

                if (user.goalList.get(i).getApp_id() == 1){
                    switch(user.goalList.get(i).getType_name()) {
                        case "Steps Walked":
                            item_progress[i] = Long.toString(fitbitAppLog.getSteps_walked());
                            break;
                        case "Miles Walked":
                            item_progress[i] = String.format("%.1f", fitbitAppLog.getMiles_walked());
                            break;
                        case "Calories Burned":
                            item_progress[i] = Long.toString(fitbitAppLog.getCalories_burned());
                            break;
                        case "Calories Consumed":
                            item_progress[i] = Long.toString(fitbitAppLog.getCalories_consumed());
                            break;
                        case "Pulse":
                            item_progress[i] = Long.toString(fitbitAppLog.getPulse());
                            break;
                        case "Blood Pressure":
                            item_progress[i] = fitbitAppLog.getBlood_pressure();
                            break;
                        default:
                            item_progress[i] = "[prog]";
                            break;
                    }
                    if (item_progress[i].equals("-1") || item_progress[i].equals("-1.0")){
                        item_progress[i] = "0";
                    }
                }
                else if (user.goalList.get(i).getApp_id() == 2){
                    switch(user.goalList.get(i).getType_name()) {
                        case "Steps Walked":
                            item_progress[i] = Long.toString(googleFitAppLog.getSteps_walked());
                            break;
                        case "Miles Walked":
                            item_progress[i] = String.format("%.1f", fitbitAppLog.getMiles_walked());
                            break;
                        case "Calories Burned":
                            item_progress[i] = Long.toString(googleFitAppLog.getCalories_burned());
                            break;
                        case "Calories Consumed":
                            item_progress[i] = Long.toString(googleFitAppLog.getCalories_consumed());
                            break;
                        case "Pulse":
                            item_progress[i] = Long.toString(googleFitAppLog.getPulse());
                            break;
                        case "Blood Pressure":
                            item_progress[i] = googleFitAppLog.getBlood_pressure();
                            break;
                        default:
                            item_progress[i] = "[prog]";
                            break;
                    }
                    if (item_progress[i].equals("-1") || item_progress[i].equals("-1.0")){
                        item_progress[i] = "0";
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
                    case "Blood Pressure":
                        imageID[i] = R.drawable.icon_goaltype_blood_pressure;
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
                    //Toast.makeText(getApplicationContext(), goalInfoArray[position], Toast.LENGTH_LONG).show();
                    Intent moveToGraph = new Intent(MainMenuActivity.this, GraphActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("user", (User)getIntent().getSerializableExtra("user"));
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
                    /*
                    // API Table -- add data
                    db.addApiToApiTable(1, 1, true, "N/A", "Alex@gmail.com", "AlexIsDaMan2");
                    db.addApiToApiTable(1, 2, true, "KingAlex90", "Alex@gmail.com", "AlexIsTooFly45");
                    db.addApiToApiTable(2, 1, true, "N/A", "cdk123@yahoo.com", "PleaseLogin");
                    db.addApiToApiTable(2, 2, false, null, null, null);
                    db.addApiToApiTable(3, 1, true, "N/A", "Blue123@outlook.com", "fmd2m4k$");
                    db.addApiToApiTable(3, 2, true, "Blue123", "Blue123@outlook.com", "fmd2m4k$");

                    db.addLogToLogTable(1,1, 1560,0.8, 200, 1000, 93, "135/90");
                    db.addLogToLogTable(3, 1, 9786, 6.2, 1003, 2340, 86, "128/93");
                    db.addLogToLogTable(3, 2, 2010, 1.3, 300, 1650, 85, "122/93");
                    db.addLogToLogTable(1,2, 1032, 0.7, 197, 0, 0, null);
                    */

                if (user.goalList.size() == 0){
                    db.addGoalToGoalTable(1,1,1,1, "3000");
                    db.addGoalToGoalTable(1,1,2,1, "3");
                    db.addGoalToGoalTable(1,1,3,1, "2500");
                    db.addGoalToGoalTable(1,1,4,1, "2200");
                    db.addGoalToGoalTable(1,1,5,1, "80");
                }

                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "135/90", user.getCalculatedDate("yyyy-MM-dd", -16));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "128/93", user.getCalculatedDate("yyyy-MM-dd", -15));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "122/93", user.getCalculatedDate("yyyy-MM-dd", -14));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "127/90", user.getCalculatedDate("yyyy-MM-dd", -13));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "131/85", user.getCalculatedDate("yyyy-MM-dd", -12));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "130/97", user.getCalculatedDate("yyyy-MM-dd", -11));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "125/87", user.getCalculatedDate("yyyy-MM-dd", -10));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "124/84", user.getCalculatedDate("yyyy-MM-dd", -9));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "132/85", user.getCalculatedDate("yyyy-MM-dd", -8));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "124/83", user.getCalculatedDate("yyyy-MM-dd", -3));
                db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "125/84", user.getCalculatedDate("yyyy-MM-dd", -2));
                db.addLogToLogTable(1, 1, new Random().nextInt(9000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "127/89", user.getCalculatedDate("yyyy-MM-dd", -1));
                Long test = db.addLogToLogTable(1, 1, new Random().nextInt(4000) + 1000, (new Random().nextInt(49) + 1) * 0.1, new Random().nextInt(1000) + 2000, new Random().nextInt(1000) + 1750, new Random().nextInt(40) + 60, "127/89", user.getCalculatedDate("yyyy-MM-dd", 0));
                if (test > 0) {
                    Toast.makeText(MainMenuActivity.this, "Random Log Data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainMenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                user.goalList = db.getGoalListFromGoalTable(user.getUser_id());
                user.appAndApiList = db.getAppAndApiListFromLogTable(user.getUser_id());

                // Refreshes page
                finish();
                startActivity(getIntent());
                    /*
                    // User Table -- Display
                    Toast.makeText(getApplicationContext(), "User Table:", Toast.LENGTH_LONG).show();
                    long key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_USER);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getAccountFromUserTable(i), Toast.LENGTH_SHORT).show();

                    // Goal Table -- Display
                    Toast.makeText(getApplicationContext(), "Goal Table:", Toast.LENGTH_LONG).show();
                    key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_GOAL);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getGoalInfoFromGoalTable(i), Toast.LENGTH_SHORT).show();

                    // API Table -- Display
                    Toast.makeText(getApplicationContext(), "API Table:", Toast.LENGTH_LONG).show();
                    key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_API);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getApiInfoFromApiTable(i), Toast.LENGTH_SHORT).show();

                    // Log Table -- Display
                    Toast.makeText(getApplicationContext(), "Log Table:", Toast.LENGTH_LONG).show();
                    long key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_LOG);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getLogInfoFromLogTable(i), Toast.LENGTH_SHORT).show();

                    Long test = db.changePasswordInUserTable(1, "NewerPassword");
                    if(test > 0){
                        Toast.makeText(MainMenuActivity.this,db.getUserPasswordFromUserTable(1), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this,"Error Change Email", Toast.LENGTH_SHORT).show();
                    }

                   int key = user.goalList.size();
                    for (int i = 0; i < key; i++){
                        Toast.makeText(MainMenuActivity.this, user.goalList.get(i).getAllGoalData(), Toast.LENGTH_SHORT).show();
                    }
                    */
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
                        Toast.makeText(getApplicationContext(), "USER_ID = "+user.getUser_id(), Toast.LENGTH_LONG).show();
                        db.deleteAccountFromUserTable(user.getUser_id());

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
}
