package com.zybooks.josephszaboapp;

public class InventoryItem {

    private String itemName; // Name of inventory item
    private int quantity; // Quantity of inventory item
    private String unit; // Unit measurement of the inventory item

    // Constructor
    public InventoryItem(String itemName, int quantity, String unit) {
        this.itemName = itemName; // Fix the typo here
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getter for itemName
    public String getName() {
        return itemName;
    }

    // Setter for itemName
    public void setName(String itemName) {
        this.itemName = itemName;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter for unit
    public String getUnit() {
        return unit;
    }

    // Setter for unit
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
