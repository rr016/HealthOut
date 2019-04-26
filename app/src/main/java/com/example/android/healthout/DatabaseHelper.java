package com.example.android.healthout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Logcat tag
    private static final String LOG_CAT = "DatabaseHelper";

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
    public static final String TABLE_LOG = "Log";

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
    public static final String GOAL_ID = "goal_id";
    public static final String GOAL_TARGET_VALUE = "target_value";

    // API Table - column names
    public static final String API_ID = "api_id";
    public static final String API_REGISTERED = "registered";
    public static final String API_USERNAME = "api_username";
    public static final String API_EMAIL = "api_email";
    public static final String API_PASSWORD = "api_password";

    // Log Table - column names
    public static final String LOG_ID = "log_id";
    public static final String LOG_STEPS_WALKED = "steps_walked";
    public static final String LOG_MILES_WALKED = "miles_walked";
    public static final String LOG_CALORIES_BURNED = "calories_burned";
    public static final String LOG_CALORIES_CONSUMED = "calories_consumed";
    public static final String LOG_PULSE = "pulse";
    public static final String LOG_BLOOD_PRESSURE = "blood_pressure";
    public static final String LOG_EPOCH_TIMESTAMP = "epoch_timestamp";


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
                " (" + GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, " + APP_ID + " INTEGER, "
                + TYPE_ID + " INTEGER, " + PERIOD_ID + " INTEGER, " + GOAL_TARGET_VALUE + " TEXT)");
        // API Table -- created
        database.execSQL("CREATE TABLE " + TABLE_API +
                " (" + API_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, " + APP_ID + " INTEGER, "
                + API_REGISTERED + " BOOLEAN, " + API_USERNAME + " TEXT, " + API_EMAIL + " TEXT, " + API_PASSWORD + " TEXT)");
        // Fitbit Table -- created
        database.execSQL("CREATE TABLE " + TABLE_LOG +
                " (" + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, "
                + APP_ID + " INTEGER, "+ LOG_STEPS_WALKED + " INTEGER, " + LOG_MILES_WALKED + " DOUBLE, "
                + LOG_CALORIES_BURNED + " INTEGER, " + LOG_CALORIES_CONSUMED + " INTEGER, "
                + LOG_PULSE + " INTEGER, " + LOG_BLOOD_PRESSURE + " TEXT, " + LOG_EPOCH_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_APP);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_API);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);

        onCreate(database);
    }

    // User Table -- methods
    public long addUserToUserTable(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        long res = db.insert(TABLE_USER, null, values);
        db.close();
        return res;
    }

    public String getEmailFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(1);
    }

    public String getPasswordFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(2);
    }

    public long getUserIdFromUserTable(String email, String password) {
        String[] columns = {USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_EMAIL + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        long id = cursor.getColumnIndex(USER_ID);
        cursor.close();
        db.close();

        return id;
    }

    public String getAccountFromUserTable(long user_id){
        return getEmailFromUserTable(user_id) + ", " + getPasswordFromUserTable(user_id);
    }

    public Boolean deleteAccountFromUserTable(long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, USER_ID + "=" + user_id, null) > 0;
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
        ContentValues values = new ContentValues();
        values.put(APP_NAME, name);
        long res = db.insert(TABLE_APP, null, values);
        db.close();
        return res;
    }

    public String getAppFromAppTable(long app_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_ID + " = " + app_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String app = c.getString(1);
        return app;
    }

    // Type Table -- methods
    public long addTypeToTypeTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_NAME, name);
        long res = db.insert(TABLE_TYPE, null, values);
        db.close();
        return res;
    }

    public String getTypeFromTypeTable(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_ID + " = " + type_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String type = c.getString(1);
        return type;
    }

    // Period Table -- methods
    public long addPeriodToPeriodTable(String period_length) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERIOD_LENGTH, period_length);
        long res = db.insert(TABLE_PERIOD, null, values);
        db.close();
        return res;
    }

    public String getPeriodFromPeriodTable(long period_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_ID + " = " + period_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

       //if (c != null)
            c.moveToFirst();

        String period = c.getString(1);
        return period;
    }

    // Goal Table -- methods
    public long addGoalToGoalTable(long user_id, long app_id, long type_id, long period_id, String target_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(TYPE_ID, type_id);
        values.put(PERIOD_ID, period_id);
        values.put(GOAL_TARGET_VALUE, target_value);
        long res = db.insert(TABLE_GOAL, null, values);
        db.close();
        return res;
    }

    public Long getUserIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(1);
    }

    public String getUserEmailFromGoalTable(long goal_id) {
        return getEmailFromUserTable(getUserIdFromGoalTable(goal_id));
    }

    public Long getAppIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(2);
    }

    public String getAppNameFromGoalTable(long goal_id) {
        return getAppFromAppTable(getAppIdFromGoalTable(goal_id));
    }

    public Long getTypeIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(3);
    }

    public String getTypeNameFromGoalTable(long goal_id) {
        return getTypeFromTypeTable(getTypeIdFromGoalTable(goal_id));
    }

    public Long getPeriodIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(4);
    }

    public String getPeriodLengthFromGoalTable(long goal_id) {
        return getPeriodFromPeriodTable(getPeriodIdFromGoalTable(goal_id));
    }

    public String getTargetValueIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(5);
    }

    public String getGoalInfoFromGoalTable(long goal_id){
        String goalInfo = getUserEmailFromGoalTable(goal_id) + ", " + getAppNameFromGoalTable(goal_id)
                + ", " + getTypeNameFromGoalTable(goal_id) + ", " + getPeriodLengthFromGoalTable(goal_id)
                + ", " + getTargetValueIdFromGoalTable(goal_id);
        return goalInfo;
    }

    // API Table -- methods
    public long addApiToApiTable(long user_id, long app_id, boolean registered, String app_username, String app_email, String app_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(API_REGISTERED, registered);
        values.put(API_USERNAME, app_username);
        values.put(API_EMAIL, app_email);
        values.put(API_PASSWORD, app_email);
        long res = db.insert(TABLE_API, null, values);
        db.close();
        return res;
    }

    public Long getUserIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(1);
    }

    public String getAppNameFromApiTable(long api_id) {
        return getAppFromAppTable(getUserIdFromApiTable(api_id));
    }

    public Long getAppIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(2);
    }

    public String getUserEmailFromApiTable(long api_id) {
        return getEmailFromUserTable(getUserIdFromApiTable(api_id));
    }

    public boolean isRegisteredInApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getInt(3) > 0;
    }

    public String getAppUsernameFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(4);
    }

    public String getAppEmailFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(5);
    }

    public String getAppPasswordFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(6);
    }

    public String getApiInfoFromApiTable(long api_id){
        String api_info = getUserEmailFromApiTable(api_id) + ", " + getAppNameFromApiTable(api_id)
                + ", " + isRegisteredInApiTable(api_id) + ", " + getAppUsernameFromApiTable(api_id)
                + ", " + getAppEmailFromApiTable(api_id) + ", " + getAppPasswordFromApiTable(api_id);
        return api_info;
    }

    // Log Table -- methods
    public long addLogToLogTable(long user_id, long app_id, long steps_walked, double miles_walked, long calories_burned, long calories_consumed, long pulse, String blood_pressure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_STEPS_WALKED, steps_walked);
        values.put(LOG_MILES_WALKED, miles_walked);
        values.put(LOG_CALORIES_BURNED, calories_burned);
        values.put(LOG_CALORIES_CONSUMED, calories_consumed);
        values.put(LOG_PULSE, pulse);
        values.put(LOG_BLOOD_PRESSURE, blood_pressure);
        long res = db.insert(TABLE_LOG, null, values);
        db.close();
        return res;
    }

    public Long getUserIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(1);
    }

    public String getUserEmailFromLogTable(long log_id) {
        String email = getEmailFromUserTable(getUserIdFromLogTable(log_id));
        return email;
    }

    public Long getAppIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(2);
    }

    public String getAppNameFromLogTable(long log_id) {
        String name = getAppFromAppTable(getAppIdFromLogTable(log_id));
        return name;
    }

    public long getStepsWalkedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(3);
    }

    public double getMilesWalkedFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getDouble(4);
    }

    public long getCaloriesBurnedFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(5);
    }

    public long getCaloriesConsumedFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(6);
    }

    public long getPulseFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(7);
    }

    public String getBloodPressureFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getString(8);
    }

    public long getTimestampFromFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(9);
    }

    public String getLogInfoFromLogTable(long log_id){
        String log_info = getUserEmailFromLogTable(log_id) + ", " + getAppNameFromLogTable(log_id)
                + ", " + getStepsWalkedFromLogTable(log_id) + ", " + getMilesWalkedFromFromLogTable(log_id)
                + ", " + getCaloriesBurnedFromFromLogTable(log_id) + ", " + getCaloriesConsumedFromFromLogTable(log_id) + ", "
                + getPulseFromFromLogTable(log_id) + ", " + getBloodPressureFromFromLogTable(log_id) + ", "
                + getTimestampFromFromLogTable(log_id);
        return log_info;
    }

}
