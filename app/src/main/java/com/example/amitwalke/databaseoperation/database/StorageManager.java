package com.example.amitwalke.databaseoperation.database;

import com.example.amitwalke.databaseoperation.model.CartItem;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class StorageManager {

    private DatabaseHelper mDbHelper;
    private static final String TAG = "StorageManager";
    private static StorageManager sInstance;

    private StorageManager(Context ctx) {
        mDbHelper = DatabaseHelper.getInstance(ctx.getApplicationContext());
    }

    public static StorageManager getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new StorageManager(ctx);
        }
        return sInstance;
    }

    public int addToLocalCart(CartItem product) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Column.PRODUCT_ID, product.productId);
        values.put(DatabaseHelper.Column.PRODUCT_NAME, product.productName);
        values.put(DatabaseHelper.Column.PRODUCT_IMAGE_URL, product.getProductUrl());

        long id = db.insert(DatabaseHelper.Test.TABLE_NAME, null, values);
        Log.i(TAG, "addToLocalCart: InsertStatus==> "+id);
        if (id > -1)
            return DatabaseHelper.Status.ADD_SUCCESS;
        else
            return DatabaseHelper.Status.FAILURE;
    }

    public int removeFromCart(String productId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            db.execSQL("delete from " + DatabaseHelper.Test.TABLE_NAME + " where " + DatabaseHelper.Column.PRODUCT_ID + " = '" + productId + "'");
            return DatabaseHelper.Status.REMOVE_SUCCESS;
        } catch (Exception e) {
            return DatabaseHelper.Status.ERROR;
        }
    }

    public ArrayList<CartItem> getProductsFromLocalCart() {
        Cursor cursor = null;
        ArrayList<CartItem> prodList = null;
        try {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + DatabaseHelper.Test.TABLE_NAME, null);
            prodList = new ArrayList<>();
            if (cursor.moveToFirst()) {

                final int prodId = cursor.getColumnIndex(DatabaseHelper.Column.PRODUCT_ID);
                final int prodName = cursor.getColumnIndex(DatabaseHelper.Column.PRODUCT_NAME);
                final int prodImage = cursor.getColumnIndex(DatabaseHelper.Column.PRODUCT_IMAGE_URL);
                do {
                    CartItem product = new CartItem();
                    product.productId = cursor.getString(prodId);
                    product.productName = cursor.getString(prodName);
                    product.productUrl = cursor.getString(prodImage);
                    prodList.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return prodList;
    }


    public boolean isItemPresent(String id){
        Cursor cursor=null;
        try{
            SQLiteDatabase db=mDbHelper.getReadableDatabase();
            cursor=db.rawQuery("select * from "+DatabaseHelper.Test.TABLE_NAME+" where "+DatabaseHelper.Column.PRODUCT_ID+"="+"'"+id+"'",null);
            if(cursor.getCount()>=1){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            Log.i(TAG, "isItemPresent: Exception==>"+e.getMessage());
            return false;
        }
    }


    public void deleteAllCartProducts() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("delete from " + DatabaseHelper.Test.TABLE_NAME);
    }



    private StorageManager(){}

}
