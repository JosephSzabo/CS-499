package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameEditText, quantityEditText, unitEditText;     // Declare EditText variables for user input


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        // Initialize UI components
        itemNameEditText = findViewById(R.id.item_name);
        quantityEditText = findViewById(R.id.item_quantity);
        unitEditText = findViewById(R.id.item_description);
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
        int quantity = Integer.parseInt(quantityString);

        // Save the new item to the database
        InventoryDatabaseHelper dbHelper = new InventoryDatabaseHelper(this);
        dbHelper.addItem(quantity, itemName, unit);

        // Show a success message
        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();

        // Go back to DataActivity and refresh the inventory list
        Intent intent = new Intent(AddItemActivity.this, DataActivity.class);
        intent.putExtra("refresh", true); // Send a flag to refresh the list in DataActivity
        startActivity(intent);
        finish();  // Close AddItemActivity and return to DataActivity
    }
}
