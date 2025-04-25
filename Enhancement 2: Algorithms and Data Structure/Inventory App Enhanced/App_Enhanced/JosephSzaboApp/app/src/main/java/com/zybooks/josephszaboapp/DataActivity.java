package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataActivity extends AppCompatActivity {

    // Declare variables
    private RecyclerView recyclerView;
    private InventoryItemAdapter adapter;
    private InventoryDatabaseHelper dbHelper;
    private List<InventoryItem> inventoryList;

    // onCreate method called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);  // Set the layout for this activity

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.inventory_grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        dbHelper = new InventoryDatabaseHelper(this);

        // Load the inventory data (called in both onCreate and onResume)
        loadInventoryData();

        // Set up the adapter
        adapter = new InventoryItemAdapter(inventoryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if we need to refresh the inventory list
        if (getIntent().getBooleanExtra("refresh", false)) {
            loadInventoryData(); // Reload data from the database
        }
    }

    // Method to load the inventory data from the database
    private void loadInventoryData() {
        // Fetch updated inventory items from the database
        inventoryList = dbHelper.getAllItems();
        // Update the adapter with the new data
        if (adapter != null) {
            adapter.updateInventoryList(inventoryList);  // Make sure the adapter has this method
        }
    }

    // Method to refresh the inventory list from the database (called in AddItemActivity)
    private void refreshInventoryList() {
        loadInventoryData();  // Reload data from the database
    }

    // Method to be called when "Add New Item" button is clicked
    public void onAddNewItemClick(View view) {
        // Open the AddItemActivity
        Intent intent = new Intent(DataActivity.this, AddItemActivity.class);
        startActivity(intent);
    }

    // Edit an existing inventory item
    public void editInventoryItem(String oldItemName, String oldUnit, int oldQuantity,
                                  String newItemName, int newQuantity, String newUnit) {
        dbHelper.editItem(oldItemName, oldUnit, newItemName, newUnit, newQuantity);
        refreshInventoryList();  // Refresh after editing
    }

    // Delete an inventory item
    public void deleteInventoryItem(String name, int quantity, String unit) {
        dbHelper.deleteItem(name, unit);
        refreshInventoryList();  // Refresh after deleting
    }
}
