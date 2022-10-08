package com.example.laba2authform;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.temporal.TemporalAccessor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "userList";
    private static final String COL0 = "ID";
    private static final String COL1 = "user_name";
    private final Context context;


    public DatabaseHelper(@Nullable Context context){
        super(context,TABLE_NAME,null , 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +" ("
                + COL0 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT " + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);


        //result = -1 is data inserted incorrectly
        long result = db.insert(TABLE_NAME, null , contentValues);

        //if data inserted incorrectly returns false
        if (result == -1){
            Toast.makeText(context, "Failed to insert data!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "Succeed to insert data!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public  Cursor getItemId(String itemName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COL1
                    + " = '" + itemName + "'";
        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public void updateItem(String newItem, int id , String oldItem){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
                        " = '" + newItem + "' WHERE " + COL0 + " = '"
                        + id + "'" + " AND " + COL1 + " = '" + oldItem
                        + "'";
        Log.d(TAG, " updateItem: query: " + query);
        Log.d(TAG, " updateItem: Setting Item To: " + newItem);
        db.execSQL(query);
    }
    public void deleteItem(int id, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME +" WHERE "
                        + COL0 + " = '" + id + "'" +
                        " AND " + COL1 + " = '" + item + "'";
        Log.d(TAG, " deleteItem: query: " + query);
        Log.d(TAG, " deleteItem: Deleting: " + item + " from " + TABLE_NAME);
        db.execSQL(query);
    }

}
