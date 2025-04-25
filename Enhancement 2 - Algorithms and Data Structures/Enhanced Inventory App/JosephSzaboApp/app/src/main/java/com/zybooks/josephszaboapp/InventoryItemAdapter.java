package com.zybooks.josephszaboapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.InventoryViewHolder> {

    private List<InventoryItem> inventoryList;

    public InventoryItemAdapter(List<InventoryItem> inventoryList) {
        this.inventoryList = inventoryList;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item, parent, false);
        return new InventoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        InventoryItem item = inventoryList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        holder.unitTextView.setText(item.getUnit());

        // Handle delete click
        holder.deleteButton.setOnClickListener(v -> {
            InventoryDatabaseHelper dbHelper = new InventoryDatabaseHelper(v.getContext());
            dbHelper.deleteItem(item.getName(), item.getUnit());

            // Remove item from list and notify adapter
            inventoryList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, inventoryList.size());
        });

        // Handle edit click
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditItemActivity.class);
            intent.putExtra("item_name", item.getName());
            intent.putExtra("quantity", item.getQuantity());
            intent.putExtra("unit", item.getUnit());
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    // Method to update the inventory list and notify the adapter
    public void updateInventoryList(List<InventoryItem> newInventoryList) {
        this.inventoryList = newInventoryList; // Update the internal list
        notifyDataSetChanged(); // Notify the adapter that the data has changed and it should refresh the view
    }

    // ViewHolder class for holding individual item views
    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, quantityTextView, unitTextView;
        public View editButton, deleteButton;

        public InventoryViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            quantityTextView = itemView.findViewById(R.id.item_quantity);
            unitTextView = itemView.findViewById(R.id.item_unit);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

}