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

    Button updateButton;
    Button registerAppsButton;
    Button editGoalsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        updateButton = findViewById(R.id.button_update);
        registerAppsButton = findViewById(R.id.button_register_apps);
        editGoalsButton = findViewById(R.id.button_edit_goals);

        // Click Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Update Clicked!", Toast.LENGTH_LONG);
                toast.show();
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
                Toast toast = Toast.makeText(getApplicationContext(), "Edit Goals Clicked!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    /************************ MENU BAR ************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
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
                Toast toast2 = Toast.makeText(getApplicationContext(), "Edit Account Clicked!", Toast.LENGTH_LONG);
                toast2.show();
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
