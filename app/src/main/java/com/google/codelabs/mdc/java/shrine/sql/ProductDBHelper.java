package com.google.codelabs.mdc.java.shrine.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.codelabs.mdc.java.shrine.model.ProductEntry;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by karan on 4/19/2018.
 */

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Cart.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Cart";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "text";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_COLOR = "colors";

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_PRICE + " TEXT NOT NULL," +
                COLUMN_COLOR + " TEXT NOT NULL," +
                COLUMN_IMAGE + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean newProduct(ProductEntry productEntry) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String color = null;
        values.put(COLUMN_TITLE, productEntry.getTitle());
        values.put(COLUMN_PRICE, productEntry.getPrice());
        values.put(COLUMN_IMAGE, productEntry.getUrl());
        color = productEntry.getColor().get(0) + "," +
                productEntry.getColor().get(1) + "," +
                productEntry.getColor().get(2);

        values.put(COLUMN_COLOR, color.toString());

        long result = database.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else return true;

    }

    public List<ProductEntry> productList() {
        List<ProductEntry> productEntryList = new LinkedList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ProductEntry productEntry;

        if (cursor.moveToFirst()) {
            do {
                productEntry = new ProductEntry();

                productEntry.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                productEntry.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                productEntry.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                productEntry.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                productEntry.setColor(Arrays.asList(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR)).split(",")));
                productEntryList.add(productEntry);
            } while (cursor.moveToNext());
        }
        return productEntryList;
    }

    public boolean deleteProduct(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
        return true;
    }

    /*public void updateNote(long noteId, Note updatedNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  " + TABLE_NAME + " SET text ='" + updatedNote.getText() + "', time ='" + updatedNote.getTimestamp() + "', image ='" + updatedNote.getImage() + "'  WHERE _id='" + noteId + "'");
    }*/
}
