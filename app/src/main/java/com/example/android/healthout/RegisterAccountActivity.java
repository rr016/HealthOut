package com.example.android.healthout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterAccountActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    String sEmail;
    String sPassword;
    String sConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_account);

        emailEditText = findViewById(R.id.edittext_email2);
        passwordEditText = findViewById(R.id.edittext_password2);
        confirmPasswordEditText = findViewById(R.id.edittext_confirm_password);
        signUpButton = findViewById(R.id.button_sign_up);

        // Click Sign Up Button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}