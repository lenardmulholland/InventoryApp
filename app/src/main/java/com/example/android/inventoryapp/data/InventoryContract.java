package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

// Used code from Udacity's Pet Starting Point App (https://github.com/udacity/ud845-Pets)
// as the base for the code and adjusted the code accordingly

public class InventoryContract {

    private InventoryContract (){}

    public static final class ProductEntry implements BaseColumns {
        public final static String TABLE_NAME = "products";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product_name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplier_phone";
    }
}
