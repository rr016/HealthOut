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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity {
    DatabaseHelper db;

    EditText currentPasswordEditText;
    EditText newEmailEditText;
    EditText newPasswordEditText;
    EditText confirmNewPasswordEditText;
    Button applyButton;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        user = (User)getIntent().getSerializableExtra("user");
        db = new DatabaseHelper(this);

        currentPasswordEditText = findViewById(R.id.editText_current_password);
        newEmailEditText = findViewById(R.id.editText_new_email);
        newPasswordEditText = findViewById(R.id.editText_new_password);
        confirmNewPasswordEditText = findViewById(R.id.editText_confirm_new_password);
        applyButton = findViewById(R.id.button_apply_account);

        // Click Apply Button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Apply Clicked!", Toast.LENGTH_LONG);
                toast.show();
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
                Intent moveToMainMenu = new Intent(EditAccountActivity.this, MainMenuActivity.class).putExtra("user", user);
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
                        Intent moveToLogin = new Intent(EditAccountActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

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

                        Intent moveToLogin = new Intent(EditAccountActivity.this, LoginActivity.class);
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
