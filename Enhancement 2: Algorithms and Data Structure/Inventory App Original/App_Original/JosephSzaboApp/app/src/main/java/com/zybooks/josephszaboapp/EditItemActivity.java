package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText itemNameEditText, quantityEditText, unitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        // Initialize UI components
        itemNameEditText = findViewById(R.id.item_name_edit);
        quantityEditText = findViewById(R.id.item_quantity_edit);
        unitEditText = findViewById(R.id.item_unit_edit);

        // Get the passed item details
        Intent intent = getIntent();
        String oldName = intent.getStringExtra("itemName");
        int oldQuantity = intent.getIntExtra("itemQuantity", 0);
        String oldUnit = intent.getStringExtra("itemUnit");

        // Set the current values in the EditText fields
        itemNameEditText.setText(oldName);
        quantityEditText.setText(String.valueOf(oldQuantity));
        unitEditText.setText(oldUnit);
    }

    // Handle saving the edited item
    public void saveItem(View view) {
        // Get the updated values from EditText
        String newName = itemNameEditText.getText().toString();
        String newQuantityString = quantityEditText.getText().toString();
        String newUnit = unitEditText.getText().toString();

        // Validation
        if (newName.isEmpty() || newQuantityString.isEmpty() || newUnit.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the quantity
        int newQuantity = Integer.parseInt(newQuantityString);

        // Get the old values from the intent
        Intent intent = getIntent();
        String oldName = intent.getStringExtra("itemName");
        int oldQuantity = intent.getIntExtra("itemQuantity", 0);
        String oldUnit = intent.getStringExtra("itemUnit");

        // UPdate the item in the database
        InventoryDatabaseHelper dbHelper = new InventoryDatabaseHelper(this);
        dbHelper.updateItem(oldName, oldQuantity, oldUnit, newName, newQuantity, newUnit); // Update the item

        // Show a success message
        Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();

        // Return to DataActivity and refresh the inventory list
        Intent returnIntent = new Intent(EditItemActivity.this, DataActivity.class);
        returnIntent.putExtra("refresh", true); // Flag to refresh the list
        startActivity(returnIntent);
        finish(); // Close EditItemActivity
    }
}
