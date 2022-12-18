package com.example.laba2authform;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "userList";
    private static final String COL0 = "ID";
    private static final String COL1 = "user_name";
    private static final String COL2 = "password";
    private final Context context;


    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT, "
                + COL2 + " TEXT " +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1, String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);


        //result = -1 is data inserted incorrectly
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data inserted incorrectly returns false
        if (result == -1) {
            Toast.makeText(context, "Failed to insert data!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Succeed to insert data!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0
                + " FROM " + TABLE_NAME
                + " WHERE " + COL1
                + " = '" + itemName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getPass(String itemID, String item) {


        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 +
                " FROM " + TABLE_NAME +
                " WHERE " + COL0 + " = '" + itemID + "'" + " AND " + COL1 + " = '" + item + "'";

        Cursor pass = db.rawQuery(query, null);

        return pass;
    }

    public boolean login(String username, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " ='" + username + "'" + " AND " + COL2 + " ='" + pass + "'";
        Cursor id = db.rawQuery(query, null);
        if (!Objects.equals(id, null) && id.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateItem(String newItem, String newPass, int id, String oldItem, String oldPass) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME
                + " SET " + COL1 + " = '" + newItem + "', " + COL2 + " = '" + newPass + "'"
                + " WHERE " + COL0 + " = '"
                + id + "'" + " AND " + COL1 + " = '" + oldItem + "'" + " AND " + COL2 + " = '" + oldPass + "'";

        try {
            db.execSQL(query);
            Log.d(TAG, " updateItem: query: " + query);
            Log.d(TAG, " updateItem: Setting Item To: " + newItem);
            return true;
        } catch (SQLException sqlException) {
            Log.d(TAG, " tried updateItem: query: " + query + " and crushed");
            return false;
        }


    }

    public boolean deleteItem(int id, String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + item + "'";

        try {
            db.execSQL(query);
            Log.d(TAG, " deleteItem: query: " + query);
            Log.d(TAG, " deleteItem: Deleting: " + item + " from " + TABLE_NAME);
            return true;
        } catch (SQLException sqlException) {
            Log.d(TAG, " tried deleteItem: query: " + query + " and crushed");

            return false;
        }

    }

}
