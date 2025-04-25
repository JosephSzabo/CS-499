package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataActivity extends AppCompatActivity {

    // declare variables
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

        // Initialize the DatabaseHelper
        dbHelper = new InventoryDatabaseHelper(this);

        // Fetch inventory items from the database
        inventoryList = dbHelper.getAllItems();

        // Set up the adapter
        adapter = new InventoryItemAdapter(inventoryList);
        recyclerView.setAdapter(adapter);

        // Check if we need to refresh the inventory list
        if (getIntent().getBooleanExtra("refresh", false)) {
            refreshInventoryList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // When coming back from EditItemActivity, refresh the list
        if (getIntent().getBooleanExtra("refresh", false)) {
            refreshInventoryList();
        }
    }

    // Method to refresh the inventory list from the database
    private void refreshInventoryList() {
        // Clear current data and load the updated list from the database
        inventoryList.clear();
        inventoryList.addAll(dbHelper.getAllItems());
        adapter.notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    // Method to be called when "Add New Item" button is clicked
    public void onAddNewItemClick(View view) {
        // Open the AddItemActivity
        Intent intent = new Intent(DataActivity.this, AddItemActivity.class);
        startActivity(intent);
    }
}
