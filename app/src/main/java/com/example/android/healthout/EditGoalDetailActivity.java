package com.example.android.healthout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditGoalDetailActivity extends AppCompatActivity {

    Button applyButton;
    Spinner appSpinner;
    Spinner goalTypeSpinner;
    EditText targetEditText;
    Spinner periodSpinner;
    String sApp;
    String sGoalType;
    String sTarget;
    String sPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        appSpinner = findViewById(R.id.spinner_app);
        goalTypeSpinner = findViewById(R.id.spinner_goal_type);
        targetEditText = findViewById(R.id.editText_target);
        periodSpinner = findViewById(R.id.spinner_period);
        applyButton = findViewById(R.id.button_apply_goal);

        // Click Apply Button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sApp = appSpinner.getSelectedItem().toString();
                sGoalType = goalTypeSpinner.getSelectedItem().toString();
                sTarget = targetEditText.getText().toString();
                sPeriod = periodSpinner.getSelectedItem().toString();

                Goal addedGoal = new Goal(sApp, 0, sGoalType, 0, sTarget, sPeriod, 0, null);

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
                Intent intent1 = new Intent(EditGoalDetailActivity.this, MainMenuActivity.class);
                startActivity(intent1);
                return true;

            // Logout
            case R.id.one:
                Intent moveToLogin = new Intent(EditGoalDetailActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
                finish(); // Prevent user from returning to this page
                return true;

            // Edit Account
            case R.id.two:
                Intent intent2 = new Intent(EditGoalDetailActivity.this, EditAccountActivity.class);
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
