package com.example.android.healthout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class EditGoalsActivity extends AppCompatActivity {
    DatabaseHelper db;

    Button removeButton;
    ImageButton addNewButton;
    LinearLayout linearLayout;
    LayoutInflater inflater;

    User user;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

        user = (User)getIntent().getSerializableExtra("user");
        db = new DatabaseHelper(this);

        removeButton = findViewById(R.id.button_remove);
        addNewButton = findViewById(R.id.button_add_new);
        linearLayout = findViewById(R.id.linearLayout_goals);
        inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //
        //
        // Display all goals to screen
        // (allow them to be clickable)
        //
        //


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
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                /*
                Intent moveToEditGoalDetail = new Intent(EditGoalsActivity.this, EditGoalDetailActivity.class).putExtra("user", user);
                startActivity(moveToEditGoalDetail);
                */

                View view = inflater.inflate(R.layout.goal_item, null);
                if(count == 1) {
                    TextView text = view.findViewById(R.id.textview_goaltype_goal_1);
                    text.setTag("goaltype_goal_1");
                    text.setText("YOLO SWAG");

                    count++;
                }
                linearLayout.addView(view);
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
                Intent moveToMainMenu = new Intent(EditGoalsActivity.this, MainMenuActivity.class).putExtra("user", user);
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
                        Intent moveToLogin = new Intent(EditGoalsActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(EditGoalsActivity.this, EditAccountActivity.class).putExtra("user", user);
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

                        Intent moveToLogin = new Intent(EditGoalsActivity.this, LoginActivity.class);
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
