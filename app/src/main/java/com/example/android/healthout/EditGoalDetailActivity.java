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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.Goal;
import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

import java.util.List;

public class EditGoalDetailActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    long goal_id;
    int position_index;

    Spinner appSpinner;
    Spinner goalTypeSpinner;
    EditText targetEditText;
    Spinner periodSpinner;
    String sApp;
    String sGoalType;
    String sTarget;
    String sPeriod;
    Button applyButton;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        user = (User)extras.getSerializable("user");
        goal_id = extras.getLong("goal_id");
        position_index = extras.getInt("position_index");

        appSpinner = findViewById(R.id.spinner_app);
        goalTypeSpinner = findViewById(R.id.spinner_goal_type);
        targetEditText = findViewById(R.id.editText_target);
        periodSpinner = findViewById(R.id.spinner_period);
        applyButton = findViewById(R.id.button_apply_goal);
        removeButton = findViewById(R.id.button_remove_goal);

        loadAppSpinnerData();
        loadGoalTypeSpinnerData();
        loadPeriodSpinnerData();

        if (goal_id > 0){
            removeButton.setVisibility(View.VISIBLE);

            int temp = (int) user.goalList.get(position_index).getApp_id()-1;
            appSpinner.setSelection(temp);
            temp = (int) user.goalList.get(position_index).getType_id()-1;
            goalTypeSpinner.setSelection(temp);
            targetEditText.setText(user.goalList.get(position_index).getTarget_value());
            temp = (int) user.goalList.get(position_index).getPeriod_id()-1;
            periodSpinner.setSelection(temp);
        }

        // Click Apply Button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sApp = appSpinner.getSelectedItem().toString();
                sGoalType = goalTypeSpinner.getSelectedItem().toString();
                sTarget = targetEditText.getText().toString();
                sPeriod = periodSpinner.getSelectedItem().toString();

                if(sTarget.isEmpty() || sTarget.matches(".*[a-z].*")){
                    Toast.makeText(getApplicationContext(), "Error: target field empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(goal_id < 0){
                        // new goal
                        db.addGoalToGoalTable(user.getUser_id(), db.getAppIdFromAppTable(sApp),
                                db.getTypeIdFromTypeTable(sGoalType), db.getPeriodIdFromPeriodTable(sPeriod), sTarget);
                    }
                    else{
                        // changing goal
                        db.changeGoalInGoalTable(goal_id, db.getAppIdFromAppTable(sApp),
                                db.getTypeIdFromTypeTable(sGoalType), db.getPeriodIdFromPeriodTable(sPeriod), sTarget);
                    }

                    user.goalList = db.getGoalListFromGoalTable(user.getUser_id()); // update goalList

                    Intent moveToEditGoals = new Intent(EditGoalDetailActivity.this, EditGoalsActivity.class).putExtra("user", user);
                    // Prevent user from returning to this page
                    moveToEditGoals.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(moveToEditGoals);
                }
            }
        });

        // Click Remove Button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(removeButton.getContext())
                        .setTitle("Confirm Remove")
                        .setMessage("Are you sure you want to remove this goal?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteGoalFromGoalTable(goal_id);
                        user.goalList = db.getGoalListFromGoalTable(user.getUser_id()); // update goalList

                        Intent moveToEditGoals = new Intent(EditGoalDetailActivity.this, EditGoalsActivity.class).putExtra("user", user);
                        // Prevent user from returning to this page
                        moveToEditGoals.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToEditGoals);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
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
                Intent moveToMainMenu = new Intent(EditGoalDetailActivity.this, MainMenuActivity.class).putExtra("user", user);
                startActivity(moveToMainMenu);
                return true;

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
                        Intent moveToLogin = new Intent(EditGoalDetailActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(EditGoalDetailActivity.this, EditAccountActivity.class).putExtra("user", user);
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
                        db.deleteAccountFromUserTable(user.getUser_id());
                        Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();

                        Intent moveToLogin = new Intent(EditGoalDetailActivity.this, LoginActivity.class);
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

    private void loadAppSpinnerData(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, user.allAppsList);
        appSpinner.setAdapter(dataAdapter);
    }

    private void loadGoalTypeSpinnerData(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, user.allTypesList);
        goalTypeSpinner.setAdapter(dataAdapter);
    }

    private void loadPeriodSpinnerData(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, user.allPeriodList);
        periodSpinner.setAdapter(dataAdapter);
    }
}
