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

public class EditGoalsActivity extends AppCompatActivity {

    Button removeButton;
    Button addNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

        removeButton = findViewById(R.id.button_remove);
        addNewButton = findViewById(R.id.button_add_new);

        // Click Remove Button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Remove Clicked!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // Click Add New Goal
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditGoalsActivity.this, EditGoalDetailActivity.class);
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
            // Home - Main Menu
            case R.id.home:
                Intent intent1 = new Intent(EditGoalsActivity.this, MainMenuActivity.class);
                startActivity(intent1);
                return true;

            // Logout
            case R.id.one:
                Toast toast1 = Toast.makeText(getApplicationContext(), "Logout Clicked!", Toast.LENGTH_LONG);
                toast1.show();
                return true;

            // Edit Account
            case R.id.two:
                Intent intent2 = new Intent(EditGoalsActivity.this, EditAccountActivity.class);
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
