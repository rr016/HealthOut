package com.example.android.healthout;

import android.content.Intent;
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
                    Long val =  db.addPeriodToPeriodTable("Yearly");
                    if(val > 0){
                        Toast.makeText(MainMenuActivity.this,"Data Added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainMenuActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }

                    dataAdded = true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data Already Added", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), "Update Clicked!", Toast.LENGTH_LONG).show();
            }
        });

        // Click Register Apps Button
        registerAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "App Table:", Toast.LENGTH_LONG).show();
                for (int i = 1; i <= 3; i++)
                    Toast.makeText(MainMenuActivity.this,db.getAppFromAppTable(i), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Type Table:", Toast.LENGTH_LONG).show();
                for (int i = 1; i <= 6; i++)
                    Toast.makeText(MainMenuActivity.this,db.getTypeFromTypeTable(i), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Period Table:", Toast.LENGTH_LONG).show();
                for (int i = 1; i <= 4; i++)
                    Toast.makeText(MainMenuActivity.this,db.getPeriodFromPeriodTable(i), Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainMenuActivity.this, RegisterAppsActivity.class);
                //startActivity(intent);
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
                Toast toast1 = Toast.makeText(getApplicationContext(), "Logout Clicked!", Toast.LENGTH_LONG);
                toast1.show();
                return true;

            // Edit Account
            case R.id.two:
                Intent intent2 = new Intent(MainMenuActivity.this, EditAccountActivity.class);
                startActivity(intent2);
                return true;

            // Delete Account
            case R.id.three:
                Toast toast3 = Toast.makeText(getApplicationContext(), "Delete Account Clicked!", Toast.LENGTH_LONG);
                toast3.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
