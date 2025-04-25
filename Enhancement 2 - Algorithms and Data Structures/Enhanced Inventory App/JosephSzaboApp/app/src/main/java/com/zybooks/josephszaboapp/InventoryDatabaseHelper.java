package com.zybooks.josephszaboapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {

    // Database Constants

    private static final String DATABASE_NAME = "inventory_database";
    private static final int DATABASE_VERSION = 2;
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
    public long addItem(String itemName, int quantity, String unit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_name", itemName);
        contentValues.put("quantity", quantity);  // Ensure this is an int
        contentValues.put("unit", unit);

        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = db.insert("inventory", null, contentValues);  // Insert the item into the database
        db.close();

        return rowId;  // Return the row ID of the inserted item
    }
/*
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
*/
    // Retrieve all items from the inventory
public List<InventoryItem> getAllItems() {
    List<InventoryItem> inventoryItems = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    // Query to get all rows from the inventory table
    Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ITEM_NAME, COLUMN_ITEM_QUANTITY, COLUMN_ITEM_UNIT},
            null, null, null, null, null);

    if (cursor != null && cursor.moveToFirst()) {
        do {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_UNIT));

            inventoryItems.add(new InventoryItem(name, quantity, unit));  // Add each item to the list
        } while (cursor.moveToNext());
        cursor.close();
    }

    return inventoryItems;
}

    // Method to retrieve inventory as a HashMap
    public HashMap<String, Integer> getInventoryMap() {
        HashMap<String, Integer> inventoryMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all rows from the inventory table
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ITEM_NAME, COLUMN_ITEM_QUANTITY, COLUMN_ITEM_UNIT}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract values from the cursor
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_UNIT));

                // Construct the key
                String key = name + "-" + unit;

                // Add the item to the map (name + unit as the key)
                inventoryMap.put(key, quantity);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return inventoryMap;  // Return the populated map
    }

    // Update inventory in the database based on HashMap
    public void updateDatabase(Map<String, Integer> inventoryMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // First, delete all the items in the database
            db.execSQL("DELETE FROM " + TABLE_NAME);

            // Then insert the new items from the map
            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                String[] keyParts = entry.getKey().split("-");
                String itemName = keyParts[0];
                String unit = keyParts[1];
                int quantity = entry.getValue();

                ContentValues values = new ContentValues();
                values.put(COLUMN_ITEM_NAME, itemName);
                values.put(COLUMN_ITEM_QUANTITY, quantity);
                values.put(COLUMN_ITEM_UNIT, unit);

                db.insert(TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    // Edit item in the database (for updating inventory quantities or details)
    public void editItem(String oldItemName, String oldUnit, String newItemName, String newUnit, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, newItemName);
        values.put(COLUMN_ITEM_UNIT, newUnit);
        values.put(COLUMN_ITEM_QUANTITY, newQuantity);

        db.update(TABLE_NAME, values, COLUMN_ITEM_NAME + "=? AND " + COLUMN_ITEM_UNIT + "=?", new String[]{oldItemName, oldUnit});
        db.close();
    }

    // Delete item from the database
    public void deleteItem(String itemName, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ITEM_NAME + "=? AND " + COLUMN_ITEM_UNIT + "=?", new String[]{itemName, unit});
        db.close();
    }
}
