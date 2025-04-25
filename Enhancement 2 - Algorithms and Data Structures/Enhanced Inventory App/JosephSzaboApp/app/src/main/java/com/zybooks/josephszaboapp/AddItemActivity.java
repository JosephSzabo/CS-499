package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameEditText, quantityEditText, unitEditText;
    private InventoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        // Initialize UI components
        itemNameEditText = findViewById(R.id.item_name);
        quantityEditText = findViewById(R.id.item_quantity);
        unitEditText = findViewById(R.id.item_description);

        // Initialize Database Helper
        dbHelper = new InventoryDatabaseHelper(this);
    }

    // Handle adding the new item
    public void addItem(View view) {
        // Get values from the input fields
        String itemName = itemNameEditText.getText().toString();
        String quantityString = quantityEditText.getText().toString();
        String unit = unitEditText.getText().toString();

        // Validation
        if (itemName.isEmpty() || quantityString.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method if validation fails
        }

        // Convert the quantity input from String to integer
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add item to the database
        long rowId = dbHelper.addItem(itemName, quantity, unit); // Insert into the database

        if (rowId != -1) {
            // Item added successfully
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();

            // Create an Intent to return to DataActivity and refresh the inventory list
            Intent returnIntent = new Intent(AddItemActivity.this, MainActivity.class);
            returnIntent.putExtra("refresh", true);
            startActivity(returnIntent);
            finish(); // Close AddItemActivity
        } else {
            // Error adding item
            Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
        }
    }
}
