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
        setContentView(R.layout.edit_item); // Your actual layout

        itemNameEditText = findViewById(R.id.item_name_edit);
        quantityEditText = findViewById(R.id.item_quantity_edit);
        unitEditText = findViewById(R.id.item_unit_edit);

        dbHelper = new InventoryDatabaseHelper(this);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String itemName = intent.getStringExtra("item_name");
            int quantity = intent.getIntExtra("quantity", 0); // 0 is default
            String unit = intent.getStringExtra("unit");

            // Set text fields with the received data
            itemNameEditText.setText(itemName);
            quantityEditText.setText(String.valueOf(quantity));
            unitEditText.setText(unit);
        }
    }


    public void saveItem(View view) {
        String newName = itemNameEditText.getText().toString();
        String newUnit = unitEditText.getText().toString();
        int newQuantity;

        try {
            newQuantity = Integer.parseInt(quantityEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get old values from intent
        Intent intent = getIntent();
        String oldName = intent.getStringExtra("item_name");
        String oldUnit = intent.getStringExtra("unit");

        // Update the item in the database
        dbHelper.editItem(oldName, oldUnit, newName, newUnit, newQuantity);

        // Return to inventory screen
        Intent returnIntent = new Intent(EditItemActivity.this, DataActivity.class);
        returnIntent.putExtra("refresh", true);  // Optional: tell DataActivity to refresh
        startActivity(returnIntent);
        finish();
    }

}
