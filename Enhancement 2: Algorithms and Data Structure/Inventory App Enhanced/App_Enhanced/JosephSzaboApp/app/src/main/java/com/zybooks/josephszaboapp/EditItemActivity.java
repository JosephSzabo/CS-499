package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText itemNameEditText, quantityEditText, unitEditText;
    private InventoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        itemNameEditText = findViewById(R.id.item_name);
        quantityEditText = findViewById(R.id.item_quantity);
        unitEditText = findViewById(R.id.item_description);

        // Initialize the Database Helper
        dbHelper = new InventoryDatabaseHelper(this);

        // Get the old values from the intent
        Intent intent = getIntent();
        String oldName = intent.getStringExtra("itemName");
        String oldUnit = intent.getStringExtra("itemUnit");
        int oldQuantity = intent.getIntExtra("itemQuantity", 0);

        // Save the old values in the EditText fields
        itemNameEditText.setText(oldName);
        quantityEditText.setText(String.valueOf(oldQuantity));
        unitEditText.setText(oldUnit);
    }

    public void saveItem(View view) {
        String newName = itemNameEditText.getText().toString();
        String newQuantityString = quantityEditText.getText().toString();
        String newUnit = unitEditText.getText().toString();

        if (newName.isEmpty() || newQuantityString.isEmpty() || newUnit.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int newQuantity = Integer.parseInt(newQuantityString);

        // Get old values from intent
        Intent intent = getIntent();
        String oldName = intent.getStringExtra("itemName");
        String oldUnit = intent.getStringExtra("itemUnit");
        //int oldQuantity = intent.getIntExtra("itemQuantity", 0);

        //String oldKey = oldName + "-" + oldUnit;
        //String newKey = newName + "-" + newUnit;

        // Remove the old item and add the new one
        //inventoryMap.remove(oldKey);
        //inventoryMap.put(newKey, newQuantity);

        // Update the database directly using the editItem method
        dbHelper.editItem(oldName, oldUnit, newName, newUnit, newQuantity);

        Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent(EditItemActivity.this, DataActivity.class);
        returnIntent.putExtra("refresh", true);
        startActivity(returnIntent);
        finish();
    }
}
