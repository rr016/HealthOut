package com.example.android.healthout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;
    Button forgotButton;
    String sEmail;
    String sPassword;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.edittext_email);
        passwordEditText = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register);
        forgotButton = findViewById(R.id.button_forget);

        // Clicking Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmail = emailEditText.getText().toString();
                sPassword = passwordEditText.getText().toString();

                user.setEmail(sEmail);
                user.setPassword(sPassword);
                user.setUser_id(0);
                user.setLogged_in(true);

                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);

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
