package com.example.android.healthout;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

import java.util.Date;

public class InputLogActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;

    Spinner appSpinner;
    Spinner goalTypeSpinner;
    EditText logEditText;
    Button inputButton;
    CalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_log);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        user = (User)extras.getSerializable("user");

        appSpinner = findViewById(R.id.spinner_appname_log);
        goalTypeSpinner = findViewById(R.id.spinner_goal_type_log);
        logEditText = findViewById(R.id.editText_log_data);
        inputButton = findViewById(R.id.button_input);
        calendarView = findViewById(R.id.calendar);

        loadAppSpinnerData();
        loadGoalTypeSpinnerData();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                calendarView.setDate((new Date(year-1900, month, day)).getTime());
                calendarView.setDate((new Date(year-1900, month, day)).getTime());
                //eventOccursOn = c.getTimeInMillis(); //this is what you want to use later
            }
        });

        // Click Input Button
        inputButton.setOnClickListener(new View.OnClickListener() {
            String sApp = appSpinner.getSelectedItem().toString();

            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                long selectedDate = calendarView.getDate();
                long today = new Date().getTime();

                String logText = logEditText.getText().toString();

                if (selectedDate < today && logText.length() > 0){
                    switch ((int) db.getTypeIdFromTypeTable(goalTypeSpinner.getSelectedItem().toString())) {
                        case 1:
                            db.addStepsWalkedToLogTable(user.getUser_id(), db.getAppIdFromAppTable(sApp), Long.valueOf(logText), selectedDate, user.getDateString(selectedDate));
                            break;
                        case 2:
                            db.addMilesWalkedToLogTable(user.getUser_id(), db.getAppIdFromAppTable(sApp), Double.valueOf(logText), selectedDate, user.getDateString(selectedDate));
                            break;
                        case 3:
                            db.addCaloriesBurnedToLogTable(user.getUser_id(), db.getAppIdFromAppTable(sApp), Long.valueOf(logText), selectedDate, user.getDateString(selectedDate));
                            break;
                        case 4:
                            db.addCaloriesConsumedToLogTable(user.getUser_id(), db.getAppIdFromAppTable(sApp), Long.valueOf(logText), selectedDate, user.getDateString(selectedDate));
                            break;
                        case 5:
                            db.addPulseToLogTable(user.getUser_id(), db.getAppIdFromAppTable(sApp), Long.valueOf(logText), selectedDate, user.getDateString(selectedDate));
                            break;
                    }

                    Toast.makeText(getApplicationContext(), "Log data added", Toast.LENGTH_SHORT).show();
                }
                else if (logText.length() < 1){
                    Toast.makeText(getApplicationContext(), "Error: no data entered", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error: future date selected", Toast.LENGTH_SHORT).show();
                }
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
                Intent moveToMainMenu = new Intent(InputLogActivity.this, MainMenuActivity.class).putExtra("user", user);
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
                        Intent moveToLogin = new Intent(InputLogActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(InputLogActivity.this, EditAccountActivity.class).putExtra("user", user);
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

                        Intent moveToLogin = new Intent(InputLogActivity.this, LoginActivity.class);
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
}
