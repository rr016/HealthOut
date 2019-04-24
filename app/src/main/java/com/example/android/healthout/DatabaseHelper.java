package com.example.android.healthout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "HealthOut.db";

    // Table Names
    public static final String TABLE_USER = "User";
    public static final String TABLE_APP = "App";

    //Common column names
    public static final String ID = "ID";

    // User Table - column names
    public static final String USER_ID = "ID";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "Password";

    // User Table - column names
    public static final String APP_ID = "ID";
    public static final String APP_NAME = "Name";

    // Table Create Statements
    // User Table create statement
    //private static final String CREATE_TABLE_USER = "CREATE TABLE "
    //  + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_EMAIL
    // + " TEXT," + USER_PASSWORD + " TEXT," + null + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE TABLE_USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_EMAIL TEXT, USER_PASSWORD TEXT)");
        database.execSQL("CREATE TABLE TABLE_APP (ID INTEGER PRIMARY KEY AUTOINCREMENT, APP_NAME TEXT)");
        // creating required tables
        //database.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //switch(oldVersion) {
           // case 1:
              //  database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                // we want both updates, so no break statement here...
            //case 2:
            //    database.execSQL("DROP TABLE IF EXISTS " + TABLE_APP);
        //}
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_APP);
        onCreate(database);
    }

    // User Table -- methods
    public long addUserToUserTable(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, password);
        long res = db.insert(TABLE_USER, null, cv);
        db.close();
        return res;
    }

    public boolean checkUserInUserTable (String email, String password){
        String[] columns = {USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
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

    // App Table -- methods
    public long addAppToAppTable(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(APP_NAME, name);
        long res = db.insert(TABLE_APP, null, cv);
        db.close();
        return res;
    }
}
