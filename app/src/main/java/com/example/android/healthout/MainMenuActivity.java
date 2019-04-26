package com.example.android.healthout;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
    Boolean dataAdded = false; // Used for testing if databases work
    DatabaseHelper db;

    Button updateButton;
    Button registerAppsButton;
    Button editGoalsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DatabaseHelper(this);

        updateButton = findViewById(R.id.button_update);
        registerAppsButton = findViewById(R.id.button_register_apps);
        editGoalsButton = findViewById(R.id.button_edit_goals);

        // Click Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataAdded == false){
                    // App Table -- add data
                    db.addAppToAppTable("FitBit");
                    db.addAppToAppTable("App2");
                    db.addAppToAppTable("App3");

                    // Type Table -- add data
                    db.addTypeToTypeTable("Steps Walked");
                    db.addTypeToTypeTable("Miles Walked");
                    db.addTypeToTypeTable("Calores Consumed");
                    db.addTypeToTypeTable("Calories Burned");
                    db.addTypeToTypeTable("Pulse");
                    db.addTypeToTypeTable("Blood Pressure");

                    // Period Table -- add data
                    db.addPeriodToPeriodTable("Daily");
                    db.addPeriodToPeriodTable("Weekly");
                    db.addPeriodToPeriodTable("Monthly");
                    db.addPeriodToPeriodTable("Yearly");

                    // Goal Table -- add data
                    db.addGoalToGoalTable(1, 1, 1, 1, "5000");
                    db.addGoalToGoalTable(2,3,4,2, "10000");
                    db.addGoalToGoalTable(1,1,2,3,"30");
                    db.addGoalToGoalTable(3,2,3,2,"1200");
                    db.addGoalToGoalTable(3,3,2,4,"30000");
                    db.addGoalToGoalTable(2,1,6,1,"120/80");
                    db.addGoalToGoalTable(3,2,1,4,"1000000");
                    db.addGoalToGoalTable(1,2,5,1,"80");

                    // API Table -- add data
                    db.addApiToApiTable(1, 1, true, "N/A", "Alex@gmail.com", "AlexIsDaMan2");
                    db.addApiToApiTable(1, 2, true, "N/A", "Alex@gmail.com", "AlexIsTooFly45");
                    db.addApiToApiTable(1, 3, true, "KingAlex90", "Alex@gmail.com", "AlexIsKillinIt11");
                    db.addApiToApiTable(2, 1, true, "N/A", "cdk123@yahoo.com", "PleaseLogin");
                    db.addApiToApiTable(2, 2, false, null, null, null);
                    db.addApiToApiTable(2, 3, true, "cdk94", "cdkOther@gmail.com", "OpenSesame");
                    db.addApiToApiTable(3, 1, true, "N/A", "Blue123@outlook.com", "fmd2m4k$");
                    db.addApiToApiTable(3, 2, true, "N/A", "Blue123@outlook.com", "fmd2m4k$");
                    db.addApiToApiTable(3, 3, false, "Blue123", "Blue123@outlook.com", "fmd2m4k$");
                    Long val = db.addApiToApiTable(3, 3, false, "Blue123", "Blue123@outlook.com", "fmd2m4k$");
                    if(val > 0){
                        Toast.makeText(MainMenuActivity.this,"Data Added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }

                    dataAdded = true;
                }
                else{
                    // User Table -- Display
                    Toast.makeText(getApplicationContext(), "User Table:", Toast.LENGTH_LONG).show();
                    long key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_USER);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getAccountFromUserTable(i), Toast.LENGTH_SHORT).show();


                    // App Table -- Display
                    Toast.makeText(getApplicationContext(), "App Table:", Toast.LENGTH_LONG).show();
                    key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_APP);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getAppFromAppTable(i), Toast.LENGTH_SHORT).show();

                    // Type Table -- Display
                    Toast.makeText(getApplicationContext(), "Type Table:", Toast.LENGTH_LONG).show();
                    key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_TYPE);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getTypeFromTypeTable(i), Toast.LENGTH_SHORT).show();

                    // Period Table -- Display
                    Toast.makeText(getApplicationContext(), "Period Table:", Toast.LENGTH_LONG).show();
                    key = DatabaseUtils.queryNumEntries(db.getReadableDatabase(), db.TABLE_PERIOD);
                    for (int i = 1; i <= key; i++)
                        Toast.makeText(MainMenuActivity.this, db.getPeriodFromPeriodTable(i), Toast.LENGTH_SHORT).show();

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
                }
                //Toast.makeText(getApplicationContext(), "Update Clicked!", Toast.LENGTH_LONG).show();
            }
        });

        // Click Register Apps Button
        registerAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, RegisterAppsActivity.class);
                startActivity(intent);
            }
        });

        // Click Edit Goals Button
        editGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EditGoalsActivity.class);
                startActivity(intent);
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
                Intent moveToLogin = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
                finish(); // Prevent user from returning to this page
                return true;

            // Edit Account
            case R.id.two:
                Intent intent2 = new Intent(MainMenuActivity.this, EditAccountActivity.class);
                startActivity(intent2);
                return true;

            // Delete Account
            case R.id.three:
                Toast.makeText(getApplicationContext(), "Delete Account Clicked!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
