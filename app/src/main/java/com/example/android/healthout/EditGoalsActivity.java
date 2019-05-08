package com.example.android.healthout;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

public class EditGoalsActivity extends AppCompatActivity {
    DatabaseHelper db;

    Button addNewButton;
    ListView listView;
    LayoutInflater inflater;

    User user;
    String goalItem[];
    String item_type[];
    String item_period[];
    String item_app[];
    String item_target[];
    Integer imageID[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

        user = (User)getIntent().getSerializableExtra("user");
        db = new DatabaseHelper(this);

        addNewButton = findViewById(R.id.button_add_new);
        listView = findViewById(R.id.ListView_edit_goals);
        inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // For Clickable ListView Goals
        goalItem = new String[user.goalList.size()];
        item_type = new String[user.goalList.size()];
        item_period = new String[user.goalList.size()];
        item_app = new String[user.goalList.size()];
        item_target = new String[user.goalList.size()];
        imageID = new Integer[user.goalList.size()];


        if (user.goalList.size() < 1){
            goalItem = new String[] {"No goals created"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goalItem);
            listView.setAdapter(adapter);
        }
        else {
            for (int i = 0; i < goalItem.length; i++) {
                item_type[i] = user.goalList.get(i).getType_name();
                item_period[i] = user.goalList.get(i).getPeriod_length();
                item_app[i] = user.goalList.get(i).getApp_name();
                item_target[i] = user.goalList.get(i).getTarget_value();

                switch(user.goalList.get(i).getType_name()) {
                    case "Steps Walked":
                        imageID[i] = R.drawable.icon_goaltype_steps;
                        break;
                    case "Miles Walked":
                        imageID[i] = R.drawable.icon_goaltype_steps;
                        break;
                    case "Calories Burned":
                        imageID[i] = R.drawable.icon_goaltype_calories_burned;
                        break;
                    case "Calories Consumed":
                        imageID[i] = R.drawable.icon_goaltype_calories_consumed;
                        break;
                    case "Pulse":
                        imageID[i] = R.drawable.icon_goaltype_pulse;
                        break;
                    case "Blood Pressure":
                        imageID[i] = R.drawable.icon_goaltype_blood_pressure;
                        break;
                    default:
                        imageID[i] = R.drawable.icon_delete_account;
                        break;
                }
            }

            CustomListEditGoals adapter = new CustomListEditGoals(EditGoalsActivity.this,
                    item_type, item_period, item_app, item_target, imageID);
            listView.setAdapter(adapter);

            // Click a Goal
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent moveToEditGoalDetail = new Intent(EditGoalsActivity.this, EditGoalDetailActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("user", (User)getIntent().getSerializableExtra("user"));
                    extras.putLong("goal_id", user.goalList.get(position).getGoal_id());
                    extras.putInt("position_index", position);
                    moveToEditGoalDetail.putExtras(extras);
                    startActivity(moveToEditGoalDetail);
                }
            });
        }

        // Click Add New Goal
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToEditGoalDetail = new Intent(EditGoalsActivity.this, EditGoalDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("user", (User)getIntent().getSerializableExtra("user"));
                extras.putLong("goal_id", -1);
                moveToEditGoalDetail.putExtras(extras);
                startActivity(moveToEditGoalDetail);
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
