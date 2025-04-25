package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class SMSPermissionActivity extends AppCompatActivity {

    private Button grantPermissionButton, denyPermissionButton;  // Declare buttons for granting and denying permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_permission_activity); // This is your layout file

        // Initialize the buttons from the layout
        grantPermissionButton = findViewById(R.id.grant_permission_button);
        denyPermissionButton = findViewById(R.id.deny_permission_button);

        // Check if SMS permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            Toast.makeText(this, "SMS Permission Already Granted", Toast.LENGTH_SHORT).show();
        }

        // If user clicks Grant Permission
        grantPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request the SMS permission
                ActivityCompat.requestPermissions(SMSPermissionActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        });

        // If user clicks Deny
        denyPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send them back to the main screen
                Intent intent = new Intent(SMSPermissionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}