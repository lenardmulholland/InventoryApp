package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract;
import com.example.android.inventoryapp.data.InventoryDbHelper;

// Used code from Udacity's Pet Starting Point App (https://github.com/udacity/ud845-Pets)
// as the base for the code and adjusted the code accordingly

public class MainActivity extends AppCompatActivity {

    private InventoryDbHelper newDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){
        SQLiteDatabase db = newDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryContract.ProductEntry._ID,
                InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE};

        Cursor cursor = db.query(
                InventoryContract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.inventory_text_view);

        try {
            displayView.setText("The inventory table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(InventoryContract.ProductEntry._ID + " - " +
                    InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME + " - " +
                    InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE + " - " +
                    InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                    InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
                    InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + "\n");

            int idColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertProduct(){
        SQLiteDatabase db = newDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME, "Good Night Moon");
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE, 6);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, 5);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Amazon.com");
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "");

        long newRowId = db.insert(InventoryContract.ProductEntry.TABLE_NAME, null, values);

        Log.v("MainActivity", "New Row ID" + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
