package com.example.android.inventoryapp;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class EditorActivity extends AppCompatActivity{
    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private TextView mProductQuantityTextView;
    private EditText mProductSupplierNameEditText;
    private EditText mProductSupplierPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        Uri currentProductUri = intent.getData();

        if(currentProductUri == null){
            setTitle(getString(R.string.editor_activity_title_add_book));
        } else{
            setTitle(getString(R.string.editor_activity_title_edit_book));
        }

        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mProductPriceEditText = (EditText) findViewById(R.id.edit_product_price);
        mProductQuantityTextView = (TextView) findViewById(R.id.edit_product_quantity);
        mProductSupplierNameEditText = (EditText) findViewById(R.id.edit_product_supplier_name);
        mProductSupplierPhoneEditText = (EditText) findViewById(R.id.edit_product_supplier_phone);
    }

    private void insertProduct() {
        String productNameString = mProductNameEditText.getText().toString().trim();
        String productPriceString = mProductPriceEditText.getText().toString().trim();
        String productQuantityString = mProductQuantityTextView.getText().toString().trim();
        String productSupplierNameString = mProductSupplierNameEditText.getText().toString().trim();
        String productSupplierPhoneString = mProductSupplierPhoneEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE, productPriceString);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantityString);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, productSupplierNameString);
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, productSupplierPhoneString);

        Uri newUri = getContentResolver().insert(InventoryContract.ProductEntry.CONTENT_URI, values);

        if (newUri == null){
            Toast.makeText(this, getString(R.string.editor_insert_product_failed), Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.editor_insert_product_successful), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                insertProduct();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
