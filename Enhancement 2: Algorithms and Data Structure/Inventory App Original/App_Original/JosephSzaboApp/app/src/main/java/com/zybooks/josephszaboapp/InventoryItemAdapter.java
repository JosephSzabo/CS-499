package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.InventoryItemViewHolder> {

    private List<InventoryItem> inventoryItems; // UList to hold inventory data

    // Constructor to initialize the adapter with inventory items
    public InventoryItemAdapter(List<InventoryItem> inventoryItems) {
        if (inventoryItems == null) {
            inventoryItems = new ArrayList<>(); // Initialize an empty list if null
        }
        this.inventoryItems = inventoryItems;  // Set the list of inventory items
    }
    // Method to create a new ViewHolder for each item
    @Override
    public InventoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the layout for individual items in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_item, parent, false);
        return new InventoryItemViewHolder(view);
    }
    // Method to bind data to the ViewHolder
    @Override
    public void onBindViewHolder(InventoryItemViewHolder holder, int position) {
        InventoryItem item = inventoryItems.get(position);

        // Set the item data to the respective views in the ViewHolder
        holder.itemName.setText(item.getItemName());
        holder.itemQty.setText(String.valueOf(item.getQuantity()));
        holder.itemUnit.setText(item.getUnit());

        // Handle Edit button click
        holder.editButton.setOnClickListener(v -> {
            // Create an Intent to navigate to EditItemActivity
            Intent intent = new Intent(holder.itemView.getContext(), EditItemActivity.class);

            intent.putExtra("itemName", item.getItemName());
            intent.putExtra("itemQuantity", item.getQuantity());
            intent.putExtra("itemUnit", item.getUnit());
            holder.itemView.getContext().startActivity(intent);  // Start the EditItemActivity
        });

        // Handle Delete button click
        holder.deleteButton.setOnClickListener(v -> {
            // Get the context from the item view of the ViewHolder
            InventoryDatabaseHelper dbHelper = new InventoryDatabaseHelper(holder.itemView.getContext());

            // Perform deletion action using the dbHelper
            dbHelper.deleteItem(item.getItemName(), item.getQuantity(), item.getUnit());
            inventoryItems.remove(position);  // Remove from the list
            notifyItemRemoved(position);     // Notify RecyclerView of the removal
        });
    }
    // Return the total number of items in the inventory list
    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }
    // ViewHolder class to hold references to views for each item
    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder {
        // Declare views to display the item name, quantity, unit, and buttons
        TextView itemName, itemQty, itemUnit;
        ImageButton editButton, deleteButton;

        // Constructor to initialize the ViewHolder
        public InventoryItemViewHolder(View itemView) {
            super(itemView);
            // Find and reference each view in the layout
            itemName = itemView.findViewById(R.id.item_name);
            itemQty = itemView.findViewById(R.id.item_qty);
            itemUnit = itemView.findViewById(R.id.item_unit);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
