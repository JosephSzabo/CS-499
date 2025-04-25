package com.zybooks.josephszaboapp;

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

        public InventoryViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            quantityTextView = itemView.findViewById(R.id.item_quantity);
            unitTextView = itemView.findViewById(R.id.item_unit);
        }
    }
}