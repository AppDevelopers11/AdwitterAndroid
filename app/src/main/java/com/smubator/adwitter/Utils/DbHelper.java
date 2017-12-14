package com.smubator.adwitter.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tech_Wizard_Lenovo on 11/14/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase database;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Dwitter";

    // Contacts table name
    private static final String TABLE_CONTACTS = "DTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "day";
    private static final String KEY_TIME = "time";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT," + KEY_DATE + " TEXT,"
//                + KEY_TIME + " TEXT," + KEY_LINK + " TEXT" + KEY_CHECKED + " TEXT" + ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);

        db.execSQL("create table " + TABLE_CONTACTS + " (id INTEGER PRIMARY KEY AUTOINCREMENT,day TEXT,time TEXT)");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void addTweet(String day, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, day);
        values.put(KEY_TIME, time);
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public boolean update(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.update(TABLE_CONTACTS, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Cursor readData() {
//        String[] allColumns = new String[] { DbHelper.KEY_ID,
//                DbHelper.KEY_MESSAGE,DbHelper.KEY_DATE,DbHelper.KEY_TIME,DbHelper.KEY_LINK };
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor c = db.query(DbHelper.TABLE_CONTACTS, allColumns, null,
//                null, null, null, null);
//        if (c != null) {
//            c.moveToFirst();
//        }
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor;
    }

    public void deleteData(int memberID) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DbHelper.TABLE_CONTACTS, DbHelper.KEY_ID + "="
                + memberID, null);
    }
}