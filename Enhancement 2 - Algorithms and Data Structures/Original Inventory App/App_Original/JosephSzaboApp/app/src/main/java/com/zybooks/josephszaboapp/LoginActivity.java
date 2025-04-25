package com.zybooks.josephszaboapp;

import com.zybooks.josephszaboapp.DatabaseHelper;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameEditText, passwordEditText; // UI components for user input
    private Button createAccountButton; // BUtton to create new account
    private DatabaseHelper dbHelper; // Database helper for handling user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        createAccountButton = findViewById(R.id.create_account_button);

        dbHelper = new DatabaseHelper(this); // Initialize database helper

        loginButton.setOnClickListener(v -> handleLogin());  // Set up click listener for the login button
        createAccountButton.setOnClickListener(v -> createNewAccount()); // Set up click listener for the "create account" button
    }

    // Method to handle login
    private void handleLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if either the username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
        } else {
            // Query the database for the user with the entered credentials
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String[] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD};
            String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
            String[] selectionArgs = {username, password};
            // Execute the query
            Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            // If a matching user is found in the database
            if (cursor != null && cursor.getCount() > 0) {
                // Successful login
                cursor.close();
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                checkSmsPermission(); // Check if SMS permission is granted
                // Navigate to the next screen
                Intent intent = new Intent(LoginActivity.this, DataActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Failed login
                cursor.close();
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to create a new account
    private void createNewAccount() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if either the username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
        } else {
            // Insert the new user into the database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERNAME, username);
            values.put(DatabaseHelper.COLUMN_PASSWORD, password);

            // Insert the new user and get the row ID
            long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
            // Check if the insert was successful
            if (newRowId != -1) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                // You may want to log the user in immediately after creating the account
            } else {
                Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to check if the app has permission to send SMS
    private void checkSmsPermission() {
        // Check if the app has SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // If permission not granted, launch SMSPermissionActivity
            Intent intent = new Intent(LoginActivity.this, SMSPermissionActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Permission already granted, proceed to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the LoginActivity
        }
    }
}