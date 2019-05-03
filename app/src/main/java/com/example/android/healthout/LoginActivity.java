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

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;
    Button forgotButton;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.edittext_email);
        passwordEditText = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register);
        forgotButton = findViewById(R.id.button_forget);

        // Clicking Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  sEmail = emailEditText.getText().toString();
                String sPassword = passwordEditText.getText().toString();

                Boolean res = db.checkUserInUserTable(sEmail, sPassword);
                if(res == true){
                    user.setEmail(sEmail);
                    user.setPassword(sPassword);
                    user.setUser_id(db.getUserIdFromUserTable(sEmail, sPassword));
                    user.setLogged_in(true);

                    user.allAppsList = db.getAllAppsFromAppTable();
                    user.allTypesList = db.getAllTypesFromTypeTable();
                    user.allPeriodList = db.getAllPeriodsFromPeriodTable();

                    user.goalList = db.getGoalListFromGoalTable(user.getUser_id());

                    Toast.makeText(LoginActivity.this,"Login Sucessful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class).putExtra("user", user);
                    startActivity(intent);
                    finish(); // Prevent user from returning to this page
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clicking Register Button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterAccountActivity.class);
                startActivity(intent);
            }
        });

        // Clicking Forget Password Button
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Forgot Password Clicked!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
