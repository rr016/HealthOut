package com.example.android.healthout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity {

    EditText currentPasswordEditText;
    EditText newEmailEditText;
    EditText newPasswordEditText;
    EditText confirmNewPasswordEditText;
    Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

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
                Intent intent = new Intent(EditAccountActivity.this, MainMenuActivity.class);
                startActivity(intent);
                return true;

            // Logout
            case R.id.one:
                Toast toast1 = Toast.makeText(getApplicationContext(), "Logout Clicked!", Toast.LENGTH_LONG);
                toast1.show();
                return true;

            // Delete Account
            case R.id.three:
                Toast toast3 = Toast.makeText(getApplicationContext(), "Delete Account Clicked!", Toast.LENGTH_LONG);
                toast3.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
