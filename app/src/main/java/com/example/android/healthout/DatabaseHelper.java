package com.example.android.healthout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String TABLE_TYPE = "Type";
    public static final String TABLE_PERIOD = "Period";
    public static final String TABLE_GOAL = "Goal";
    public static final String TABLE_API = "API";
    public static final String TABLE_FITBIT = "Fitbit";

    // User Table - column names
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "Password";

    // User Table - column names
    public static final String APP_ID = "app_id";
    public static final String APP_NAME = "Name";

    // Type Table - column names
    public static final String TYPE_ID = "type_id";
    public static final String TYPE_NAME = "type_name";

    // Period Table - column names
    public static final String PERIOD_ID = "period_id";
    public static final String PERIOD_LENGTH = "period_length";

    // Goal Table - column names
    public static final String GOAL_TARGET_VALUE = "target_value";

    // API Table - column names
    public static final String API_REGISTERED = "registered";
    public static final String API_USERNAME = "api_username";
    public static final String API_EMAIL = "api_email";
    public static final String API_PASSWORD = "api_password";

    // Fitbit Table - column names
    public static final String FITBIT_STEPS_WALKED = "steps_walked";
    public static final String FITBIT_MILES_WALKED = "miles_walked";
    public static final String FITBIT_CALORIES_BURNED = "calories_burned";
    public static final String FITBIT_CALORIES_CONSUMED = "calories_consumed";
    public static final String FITBIT_PULSE = "pulse";
    public static final String FITBIT_BLOOD_PRESSURE = "blood_pressure";
    public static final String FITBIT_EPOCH_TIMESTAMP = "epoch_timestamp";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // User Table -- created
        database.execSQL("CREATE TABLE " + TABLE_USER +
                " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT)");
        // App Table -- created
        database.execSQL("CREATE TABLE " + TABLE_APP +
                " (" + APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + APP_NAME + " TEXT)");
        // Type Table -- created
        database.execSQL("CREATE TABLE " + TABLE_TYPE +
                " (" + TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE_NAME + " TEXT)");
        // Period Table -- created
        database.execSQL("CREATE TABLE " + TABLE_PERIOD +
                " (" + PERIOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERIOD_LENGTH + " TEXT)");
        // Goal Table -- created
        database.execSQL("CREATE TABLE " + TABLE_GOAL +
                " (" + USER_ID + " INTEGER, " + APP_ID + " INTEGER, " + TYPE_ID + " INTEGER, "
                + PERIOD_ID + " INTEGER, " + GOAL_TARGET_VALUE + " TEXT)");
        // API Table -- created
        database.execSQL("CREATE TABLE " + TABLE_API +
                " (" + USER_ID + " INTEGER, " + APP_ID + " INTEGER, " + API_REGISTERED + " BOOLEAN, "
                + API_USERNAME + " TEXT, " + API_EMAIL + " TEXT, " + API_PASSWORD + " TEXT)");
        // Fitbit Table -- created
        database.execSQL("CREATE TABLE " + TABLE_FITBIT +
                " (" + USER_ID + " INTEGER, " + FITBIT_STEPS_WALKED + " INTEGER, " + FITBIT_MILES_WALKED + " INTEGER, "
                + FITBIT_CALORIES_BURNED + " INTEGER, " + FITBIT_CALORIES_CONSUMED + " INTEGER, "
                + FITBIT_PULSE + " INTEGER, " + FITBIT_BLOOD_PRESSURE + " TEXT, " + FITBIT_EPOCH_TIMESTAMP + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_APP);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_API);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FITBIT);

        onCreate(database);
    }

    // User Table -- methods
    public long addUserToUserTable(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, password);
        long res = db.insert(TABLE_USER, null, cv);
        db.close();
        return res;
    }

    public boolean checkUserInUserTable(String email, String password) {
        String[] columns = {USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_EMAIL + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;

    }

    // App Table -- methods
    public long addAppToAppTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(APP_NAME, name);
        long res = db.insert(TABLE_APP, null, cv);
        db.close();
        return res;
    }

    public String getAppFromAppTable(long app_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_ID + " = " + app_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String appName = c.getString(1);

        return appName;
    }

    // Type Table -- methods
    public long addTypeToTypeTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TYPE_NAME, name);
        long res = db.insert(TABLE_TYPE, null, cv);
        db.close();
        return res;
    }

    public String getTypeFromTypeTable(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_ID + " = " + type_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String typeName = c.getString(1);

        return typeName;
    }

    // Period Table -- methods
    public long addPeriodToPeriodTable(String period_length) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PERIOD_LENGTH, period_length);
        long res = db.insert(TABLE_PERIOD, null, cv);
        db.close();
        return res;
    }

    public String getPeriodFromPeriodTable(long period_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_ID + " = " + period_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String periodName = c.getString(1);

        return periodName;
    }
}
