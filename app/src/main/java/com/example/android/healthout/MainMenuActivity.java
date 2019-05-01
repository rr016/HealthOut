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

import com.example.android.healthout.dataEntities.User;

public class MainMenuActivity extends AppCompatActivity {
    Boolean dataAdded = false; // Used for testing if databases work

    DatabaseHelper db;

    Button updateButton;
    Button registerAppsButton;
    Button editGoalsButton;
    ListView listView;

    User user;
    String goalItem[];
    String item_progress[];
    String item_type[];
    Integer imageID[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        user = (User)getIntent().getSerializableExtra("user");
        db = new DatabaseHelper(this);

        updateButton = findViewById(R.id.button_update);
        registerAppsButton = findViewById(R.id.button_register_apps);
        editGoalsButton = findViewById(R.id.button_edit_goals);
        listView = findViewById(R.id.ListView_display_goals);

        // For Clickable ListView Goals
        goalItem = new String[user.goalList.size()];
        item_progress = new String[user.goalList.size()];
        item_type = new String[user.goalList.size()];
        imageID = new Integer[user.goalList.size()];

        if (user.goalList.size() < 1){
            goalItem = new String[] {"No goals created"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goalItem);
            listView.setAdapter(adapter);
        }
        else{
            for (int i = 0; i < goalItem.length; i++) {
                if(user.goalList.get(i).getProgress() == null)
                    item_progress[i] = "[progress]";
                else
                    item_progress[i] = user.goalList.get(i).getProgress();
                item_type[i] = user.goalList.get(i).getType_name();

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
            CustomListMainMenu adapter = new CustomListMainMenu(MainMenuActivity.this, item_progress, item_type, imageID);
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
                if (dataAdded == false){
                    /*
                    // Goal Table -- add data
                    db.addGoalToGoalTable(1, 1, 1, 1, "5000");
                    db.addGoalToGoalTable(2,2,4,2, "10000");
                    db.addGoalToGoalTable(1,1,2,3,"30");
                    db.addGoalToGoalTable(3,2,3,2,"1200");
                    db.addGoalToGoalTable(3,1,2,4,"30000");
                    db.addGoalToGoalTable(2,1,6,1,"120/80");
                    db.addGoalToGoalTable(3,2,1,4,"1000000");
                    db.addGoalToGoalTable(1,2,5,1,"80");

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
                    Long test = db.addLogToLogTable(1, 2, 2144, 1.6, 321, 535, 90, "131/85");
                    if(test > 0){
                        Toast.makeText(MainMenuActivity.this,"Data Added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                    */

                    db.addLogToLogTable(1,1, 143,0.1, 20, 0, 86, "135/90");
                    db.addLogToLogTable(1, 1, 375, 0.2, 43, 0, 86, "128/93");
                    db.addLogToLogTable(1, 1, 791, 0.4, 77, 350, 85, "122/93");
                    db.addLogToLogTable(1,1, 1033, 0.6, 95, 350, 87, "127/90");
                    db.addLogToLogTable(1, 1, 1598, 0.9, 122, 350, 89, "131/85");
                    db.addLogToLogTable(1, 1, 1954, 1.3, 146, 790, 85, "130/97");
                    db.addLogToLogTable(1, 1, 2201, 1.6, 172, 790, 90, "125/87");
                    db.addLogToLogTable(1, 1, 2504, 1.8, 190, 790, 87, "124/84");
                    db.addLogToLogTable(1, 1, 3079, 2.3, 238, 1601, 92, "132/85");
                    db.addLogToLogTable(1, 1, 3734, 2.8, 276, 1601, 85, "124/83");
                    db.addLogToLogTable(1, 1, 4300, 3.1, 301, 1601, 86, "125/84");
                    Long test =  db.addLogToLogTable(1, 1, 4441, 3.2, 311, 2234, 86, "127/89");
                    if(test > 0){
                        Toast.makeText(MainMenuActivity.this,"Data Added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }

                    user.goalList = db.getGoalArrayListFromGoalTable(user.getUser_id());
                    user.appAndApiList = db.getAppAndApiArrayListFromLogTable(user.getUser_id());

                    dataAdded = true;

                }
                else{
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
                Toast.makeText(getApplicationContext(), "Update Clicked!", Toast.LENGTH_LONG).show();
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
