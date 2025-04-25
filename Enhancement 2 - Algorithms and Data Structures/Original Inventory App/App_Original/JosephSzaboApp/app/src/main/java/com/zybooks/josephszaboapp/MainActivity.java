package com.zybooks.josephszaboapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryItemAdapter adapter;
    private InventoryDatabaseHelper dbHelper;
    private List<InventoryItem> inventoryList;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_PERMISSION_SHOWN = "permission_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);  // Make sure this is the correct layout file

        // Initialize the Database Helper and RecyclerView
        dbHelper = new InventoryDatabaseHelper(this);
        recyclerView = findViewById(R.id.inventory_grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize RecyclerView
        inventoryList = dbHelper.getAllItems();
        adapter = new InventoryItemAdapter(inventoryList);
        recyclerView.setAdapter(adapter);

        // Check if the SMS permission has already been requested
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean permissionShown = sharedPreferences.getBoolean(KEY_PERMISSION_SHOWN, false);

        // If permission prompt has not been shown yet, show the SMS permission activity
        if (!permissionShown) {
            // Show the SMS Permission Activity
            Intent intent = new Intent(MainActivity.this, SMSPermissionActivity.class);
            startActivity(intent);

            // Mark the permission prompt as shown in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_PERMISSION_SHOWN, true);
            editor.apply();
        } else {
            // Permission has already been shown, so check if it's granted
            checkSmsPermission();
        }
    }

    // Check if SMS permission is granted
    private void checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with low stock check and SMS sending
            checkLowStockAndSendSms();
        } else {
            // Permission not granted, request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    // Check for low stock items and send SMS
    private void checkLowStockAndSendSms() {
        for (InventoryItem item : inventoryList) {
            if (item.getQuantity() <= 0) {
                // Send SMS for low inventory
                sendSms("Inventory for " + item.getItemName() + " is empty. There are " + item.getQuantity() + " left.");
            }
        }
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with sending SMS for low stock
                checkLowStockAndSendSms();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Function to send SMS
    private void sendSms(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("1234567890", null, message, null, null); // Replace with actual phone number
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed to send", Toast.LENGTH_SHORT).show();
        }
    }
}
