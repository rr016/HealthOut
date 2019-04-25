package com.example.android.healthout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class RegisterAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_apps);
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
                Intent intent1 = new Intent(RegisterAppsActivity.this, MainMenuActivity.class);
                startActivity(intent1);
                return true;

            // Logout
            case R.id.one:
                Intent moveToLogin = new Intent(RegisterAppsActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
                finish(); // Prevent user from returning to this page
                return true;

            // Edit Account
            case R.id.two:
                Intent intent2 = new Intent(RegisterAppsActivity.this, EditAccountActivity.class);
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
