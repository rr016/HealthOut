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

import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

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
        db = new com.example.android.healthout.database.DatabaseHelper(this);

        currentPasswordEditText = findViewById(R.id.editText_current_password);
        newEmailEditText = findViewById(R.id.editText_new_email);
        newPasswordEditText = findViewById(R.id.editText_new_password);
        confirmNewPasswordEditText = findViewById(R.id.editText_confirm_new_password);
        applyButton = findViewById(R.id.button_apply_account);

        // Click Apply Button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCurrentPassword = currentPasswordEditText.getText().toString();
                String sNewEmail = newEmailEditText.getText().toString();
                String sNewPassword = newPasswordEditText.getText().toString();
                String sConfirmNewPassword = confirmNewPasswordEditText.getText().toString();

                boolean currentPasswordValid = sCurrentPassword.equals(user.getPassword());  // whether current password is correct
                boolean newEmailValid = user.isEmailValid(sNewEmail);   // whether new email is of valid format
                boolean newPasswordValid = user.isPasswordValid(sNewPassword, sConfirmNewPassword); // whether new passwords are of valid format and match
                long changedEmail = 0;
                long changedPassword = 0;

                if (currentPasswordValid == true) // if current password is valid
                {
                    if (newEmailValid == true)  // then if new email is of valid format
                        changedEmail = db.changeEmailInUserTable(user.getUser_id(), sNewEmail);
                    if (newPasswordValid == true) // and/or if passwords are of valid format and match
                        changedPassword = db.changePasswordInUserTable(user.getUser_id(), sNewPassword);
                }


                if (currentPasswordValid == true && (newEmailValid == true || newPasswordValid == true)){
                    if (changedEmail == 1 && changedPassword == 1) // if no errors when changing email and/or password
                    {
                        user.setEmail(sNewEmail);
                        user.setPassword(sNewPassword);
                        Toast.makeText(getApplicationContext(), "Account update succesful", Toast.LENGTH_LONG).show();
                        Intent moveToMainMenu = new Intent(EditAccountActivity.this, MainMenuActivity.class).putExtra("user", user);
                        startActivity(moveToMainMenu);
                        finish(); // Prevent user from returning to this page
                    }
                    else if (changedEmail == 1 && changedPassword == 0) // if no errors when changing email
                    {
                        user.setEmail(sNewEmail);
                        Toast.makeText(getApplicationContext(), "Email update succesful", Toast.LENGTH_LONG).show();
                        Intent moveToMainMenu = new Intent(EditAccountActivity.this, MainMenuActivity.class).putExtra("user", user);
                        startActivity(moveToMainMenu);
                        finish(); // Prevent user from returning to this page
                    }
                    else if (changedEmail == 0 && changedPassword == 1) // if no errors when changing password
                    {
                        user.setPassword(sNewPassword);
                        Toast.makeText(getApplicationContext(), "Password update succesful", Toast.LENGTH_LONG).show();
                        Intent moveToMainMenu = new Intent(EditAccountActivity.this, MainMenuActivity.class).putExtra("user", user);
                        startActivity(moveToMainMenu);
                        finish(); // Prevent user from returning to this page
                    }
                }
                else if (currentPasswordValid == false){
                    Toast.makeText(getApplicationContext(), "Incorrect current password", Toast.LENGTH_LONG).show();
                }
                else if (sNewEmail.length() > 0 && sNewPassword.length() > 0 && newEmailValid == false && newPasswordValid == false){
                    Toast.makeText(getApplicationContext(), "Invalid new email and new password", Toast.LENGTH_LONG).show();
                }
                else if (sNewEmail.length() > 0 && newEmailValid == false){
                    Toast.makeText(getApplicationContext(), "Invalid new email", Toast.LENGTH_LONG).show();
                }
                else if (sNewPassword.length() > 0 && newPasswordValid == false){
                    if (sNewPassword.equals(sConfirmNewPassword) == false)
                        Toast.makeText(getApplicationContext(), "New passwords don't match", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Invalid new password", Toast.LENGTH_LONG).show();
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
