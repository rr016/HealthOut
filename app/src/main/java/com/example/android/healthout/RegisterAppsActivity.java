package com.example.android.healthout;

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
