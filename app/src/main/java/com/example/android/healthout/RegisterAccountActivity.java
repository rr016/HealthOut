package com.example.android.healthout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;

import java.util.Objects;

public class RegisterAccountActivity extends AppCompatActivity {
    DatabaseHelper db;

    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    String sUsername = "";
    String sPassword = "";
    String sConfirmPassword = "";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register_account);

        user = (User)getIntent().getSerializableExtra("user");
        db = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.edittext_username2);
        passwordEditText = findViewById(R.id.edittext_password2);
        confirmPasswordEditText = findViewById(R.id.edittext_confirm_password);
        signUpButton = findViewById(R.id.button_sign_up);

        // Click Sign Up Button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUsername = usernameEditText.getText().toString();
                sPassword = passwordEditText.getText().toString();
                sConfirmPassword = confirmPasswordEditText.getText().toString();

                if (sPassword.equals(sConfirmPassword)) {
                    long val = db.addUserToUserTable(sUsername, sPassword);
                    if (val > 0) {
                        Toast.makeText(RegisterAccountActivity.this, "Account Registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                        finish(); // Prevent user from returning to this page
                    } else if (val == -2)
                        Toast.makeText(RegisterAccountActivity.this, "Username taken", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(RegisterAccountActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterAccountActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
