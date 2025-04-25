package com.zybooks.josephszaboapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {

    // Database Constants

    private static final String DATABASE_NAME = "inventory_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "inventory";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";
    private static final String COLUMN_ITEM_UNIT = "unit";

    // Constructor to initialize the SQLiteOpenHelper
    public InventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the 'inventory' table
        // Define columns
        String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_QUANTITY + " INTEGER,"
                + COLUMN_ITEM_UNIT + " TEXT" + ")";
        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    // onUpgrade method is called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add new item to the inventory
    public void addItem(int quantity, String name, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();  // Get writable database
        ContentValues values = new ContentValues(); // Create a new ContentValues object to store the values to be inserted
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_UNIT, unit);
        db.insert(TABLE_NAME, null, values);
        db.close(); // Close the database connection after insertion
    }

    // Update an existing item in the inventory
    public void updateItem(String oldName, int oldQuantity, String oldUnit, String newName, int newQuantity, String newUnit) {
        SQLiteDatabase db = this.getWritableDatabase();// Get writable database
        ContentValues values = new ContentValues(); // Create a new ContentValues object to store the updated values
        values.put(COLUMN_ITEM_NAME, newName);
        values.put(COLUMN_ITEM_QUANTITY, newQuantity);
        values.put(COLUMN_ITEM_UNIT, newUnit);

        // Construct the WHERE clause for the update query
        String whereClause = COLUMN_ITEM_NAME + " = ? AND " + COLUMN_ITEM_QUANTITY + " = ? AND " + COLUMN_ITEM_UNIT + " = ?";
        String[] whereArgs = { oldName, String.valueOf(oldQuantity), oldUnit };  // Arguments to match the item to be updated

        // Log to verify the update query
        Log.d("Database", "Updating item with whereClause: " + whereClause + " whereArgs: " + Arrays.toString(whereArgs));

        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    // Retrieve all items from the inventory
    public List<InventoryItem> getAllItems() {
        List<InventoryItem> inventoryItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all rows from the inventory table
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ITEM_NAME, COLUMN_ITEM_QUANTITY, COLUMN_ITEM_UNIT}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get column indices
                int nameIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
                int quantityIndex = cursor.getColumnIndex(COLUMN_ITEM_QUANTITY);
                int unitIndex = cursor.getColumnIndex(COLUMN_ITEM_UNIT);

                // Ensure valid column indices
                if (nameIndex != -1 && quantityIndex != -1 && unitIndex != -1) {
                    // Extract values from the cursor
                    String name = cursor.getString(nameIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String unit = cursor.getString(unitIndex);

                    // Create InventoryItem object and add to list
                    inventoryItems.add(new InventoryItem(name, quantity, unit));
                } else {
                    // Log error if columns are not found
                    Log.e("DatabaseError", "One or more columns are missing in the query result.");
                }

            } while (cursor.moveToNext()); // Move to the next row

            cursor.close();
        }

        return inventoryItems;  // Return the populated list of inventory items
    }

    // Delete an item from the inventory based on item name, quantity, and unit
    public void deleteItem(String name, int quantity, String unit) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database

        // Construct the WHERE clause for the delete query
        String whereClause = COLUMN_ITEM_NAME + " = ? AND " + COLUMN_ITEM_QUANTITY + " = ? AND " + COLUMN_ITEM_UNIT + " = ?";
        String[] whereArgs = { name, String.valueOf(quantity), unit };

        // Execute the delete query
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close(); // Close the database connection after deletion
    }
}
