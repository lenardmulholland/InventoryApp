package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract.ProductEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

public class InventoryCursorAdapter extends CursorAdapter{
    private Context mContext;

    public InventoryCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productNameTextView = view.findViewById(R.id.product_name);
        TextView productPriceTextView = view.findViewById(R.id.product_price);
        TextView productQuantityTextView = view.findViewById(R.id.product_quantity);

        int productNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        String productName = cursor.getString(productNameColumnIndex);
        String productPrice = cursor.getString(productPriceColumnIndex);
        int productQuantity = cursor.getInt(productQuantityColumnIndex);

        productNameTextView.setText(productName);
        productPriceTextView.setText(context.getString(R.string.main_activity_product_price_label) + productPrice);
        productQuantityTextView.setText(context.getString(R.string.main_activity_product_quantity_label)
                + String.valueOf(productQuantity));

        Button saleButton = view.findViewById(R.id.sale_button);
        int columnIdIndex = cursor.getColumnIndex(ProductEntry._ID);
        saleButton.setOnClickListener(new OnItemClickListener(cursor.getInt(columnIdIndex)) {
        });
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int columnIdIndex;

        public OnItemClickListener(int columnIdIndex) {
            super();
            this.columnIdIndex = columnIdIndex;
        }

        @Override
        public void onClick(View view) {
            int columnIndex = columnIdIndex;

            SQLiteOpenHelper helper = new InventoryDbHelper(mContext);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT " + ProductEntry.COLUMN_PRODUCT_QUANTITY + " FROM " + ProductEntry.TABLE_NAME + " WHERE " +
                    ProductEntry._ID + " = " + columnIndex + "", null);

            if (cursor != null && cursor.moveToFirst()) {
                String itemQuantity = cursor.getString(cursor.getColumnIndex("quantity"));
                cursor.close();

                if (mContext instanceof MainActivity) {
                    ((MainActivity) mContext).saleDecreaseQuantity(columnIndex, Integer.valueOf(itemQuantity));
                }
            }
            db.close();
        }
    }
}
