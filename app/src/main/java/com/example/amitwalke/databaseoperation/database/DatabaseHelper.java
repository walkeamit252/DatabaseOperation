
package com.example.amitwalke.databaseoperation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TEST_DB.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;

    public interface Column {
        String PRODUCT_ID = "product_id";
        String PRODUCT_NAME = "product_name";
        String PRODUCT_IMAGE_URL = "product_image";
    }

    public interface Test {
        String TABLE_NAME = "test";

        String CREATE_TABLE_CART_SCRIPT = "CREATE TABLE " + TABLE_NAME
                + " (" + Column.PRODUCT_ID + " TEXT PRIMARY KEY, "
                + Column.PRODUCT_NAME + " TEXT, "
                + Column.PRODUCT_IMAGE_URL + " TEXT " + ");";
    }


    public interface Status {
        int ADD_SUCCESS = 1;
        int REMOVE_SUCCESS = 2;
        int FAILURE = 3;
        int ERROR = 4;
    }


    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTables(db);
        createTables(db);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DatabaseHelper(context);
        return sInstance;
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(Test.CREATE_TABLE_CART_SCRIPT);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Test.TABLE_NAME);
    }

    public static void clearInstance() {
        sInstance = null;
    }

}
