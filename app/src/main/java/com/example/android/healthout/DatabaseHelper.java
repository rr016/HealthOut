package com.example.android.healthout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    
    // Database Name
    public static final String DATABASE_NAME = "HealthOut.db";
    
    // Table Names
    public static final String TABLE_USER = "User";
    
    // USER Table - column names
    public static final String USER_ID = "ID";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "Password";

    // Table Create Statements
    // User Table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_EMAIL
            + " TEXT," + USER_PASSWORD + " TEXT," + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL("CREATE TABLE User (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");
        // creating required tables
        database.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(database);
    }

    public long addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, password);
        long res =db.insert("User", null, cv);
        db.close();
        return res;
    }

    public boolean checkUser (String email, String password){
        String[] columns = {USER_ID };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_EMAIL + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0)
            return true;
        else
            return false;

    }
}
