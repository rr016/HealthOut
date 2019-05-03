package com.example.android.healthout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.healthout.dataEntities.AppLog;
import com.example.android.healthout.dataEntities.Goal;
import com.example.android.healthout.dataEntities.ThirdPartyAppAndApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    // App Table - column names
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
    public static final String LOG_DATE = "date";


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
        // Log Table -- created
        database.execSQL("CREATE TABLE " + TABLE_LOG +
                " (" + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, "
                + APP_ID + " INTEGER, "+ LOG_STEPS_WALKED + " INTEGER, " + LOG_MILES_WALKED + " DOUBLE, "
                + LOG_CALORIES_BURNED + " INTEGER, " + LOG_CALORIES_CONSUMED + " INTEGER, "
                + LOG_PULSE + " INTEGER, " + LOG_BLOOD_PRESSURE + " TEXT, " + LOG_DATE + " STRING)");

        // App Table -- preload data
        ContentValues cvApp = new ContentValues();
        cvApp.put(APP_NAME, "FitBit");
        database.insert(TABLE_APP, null, cvApp);
        cvApp.put(APP_NAME, "Google Fit");
        database.insert(TABLE_APP, null, cvApp);

        // Type Table -- preload data
        ContentValues cvType = new ContentValues();
        cvType.put(TYPE_NAME, "Steps Walked");
        database.insert(TABLE_TYPE, null, cvType);
        cvType.put(TYPE_NAME, "Miles Walked");
        database.insert(TABLE_TYPE, null, cvType);
        cvType.put(TYPE_NAME, "Calories Burned");
        database.insert(TABLE_TYPE, null, cvType);
        cvType.put(TYPE_NAME, "Calories Consumed");
        database.insert(TABLE_TYPE, null, cvType);
        cvType.put(TYPE_NAME, "Pulse");
        database.insert(TABLE_TYPE, null, cvType);
        cvType.put(TYPE_NAME, "Blood Pressure");
        database.insert(TABLE_TYPE, null, cvType);

        // Period Table -- preload data
        ContentValues cvPeriod = new ContentValues();
        cvPeriod.put(PERIOD_LENGTH, "Daily");
        database.insert(TABLE_PERIOD, null, cvPeriod);
        cvPeriod.put(PERIOD_LENGTH, "Weekly");
        database.insert(TABLE_PERIOD, null, cvPeriod);
        cvPeriod.put(PERIOD_LENGTH, "Monthly");
        database.insert(TABLE_PERIOD, null, cvPeriod);
        cvPeriod.put(PERIOD_LENGTH, "Yearly");
        database.insert(TABLE_PERIOD, null, cvPeriod);
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

    /*****************************************************************************************************************************/
    /*************************************************** User Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addUserToUserTable(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result;
    }

    public String getUserEmailFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(USER_EMAIL));
    }

    public String getUserPasswordFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
    }

    public long getUserIdFromUserTable(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID};
        String selection = USER_EMAIL + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        long id = -1;
        if(cursor.getCount() > 0) {
            Log.d(LOG_CAT, "YOLO SWAG");
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndex(USER_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    public String getAccountFromUserTable(long user_id){
        return getUserEmailFromUserTable(user_id) + ", " + getUserPasswordFromUserTable(user_id);
    }

    public long changeEmailInUserTable(long user_id, String new_email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, new_email);
        long result = db.update(TABLE_USER, values, USER_ID + "=" + user_id, null);
        db.close();
        return result;
    }

    public long changePasswordInUserTable(long user_id, String new_password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PASSWORD, new_password);
        long result = db.update(TABLE_USER, values, USER_ID + "=" + user_id, null);
        db.close();
        return result;
    }

    public boolean deleteAccountFromUserTable(long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, USER_ID + "='" + user_id + "'", null) > 0;
    }

    public boolean checkUserInUserTable(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID};
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

    /*****************************************************************************************************************************/
    /**************************************************** App Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addAppToAppTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_NAME, name);
        long result = db.insert(TABLE_APP, null, values);
        db.close();
        return result;
    }

    public String getAppNameFromAppTable(long app_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_ID + " = " + app_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        String app = cursor.getString(cursor.getColumnIndex(APP_NAME));
        return app;
    }

    public long getAppIdFromAppTable(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_NAME + " = '" + name + "'";

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        long id = -1;
        if(cursor.getCount() > 0) {
            Log.d(LOG_CAT, "YOLO SWAG");
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndex(APP_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    public List<String> getAllAppsFromAppTable(){
        List<String> appList = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_APP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                appList.add(cursor.getString(cursor.getColumnIndex(APP_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return appList;
    }

    /*****************************************************************************************************************************/
    /*************************************************** Type Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addTypeToTypeTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_NAME, name);
        long result = db.insert(TABLE_TYPE, null, values);
        db.close();
        return result;
    }

    public String getTypeNameFromTypeTable(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_ID + " = " + type_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        String type = cursor.getString(cursor.getColumnIndex(TYPE_NAME));
        return type;
    }

    public long getTypeIdFromTypeTable(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_NAME + " = '" + name + "'";

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        long id = -1;
        if(cursor.getCount() > 0) {
            Log.d(LOG_CAT, "YOLO SWAG");
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndex(TYPE_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    public List<String> getAllTypesFromTypeTable(){
        List<String> typeList = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                typeList.add(cursor.getString(cursor.getColumnIndex(TYPE_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return typeList;
    }

    /*****************************************************************************************************************************/
    /************************************************* Period Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addPeriodToPeriodTable(String period_length) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERIOD_LENGTH, period_length);
        long result = db.insert(TABLE_PERIOD, null, values);
        db.close();
        return result;
    }

    public String getPeriodLengthFromPeriodTable(long period_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_ID + " = " + period_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        String period = cursor.getString(cursor.getColumnIndex(PERIOD_LENGTH));
        return period;
    }

    public long getPeriodIdFromPeriodTable(String length) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_LENGTH + " = '" + length + "'";

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        long id = -1;
        if(cursor.getCount() > 0) {
            Log.d(LOG_CAT, "YOLO SWAG");
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndex(PERIOD_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    public List<String> getAllPeriodsFromPeriodTable(){
        List<String> periodList = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                periodList.add(cursor.getString(cursor.getColumnIndex(PERIOD_LENGTH)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return periodList;
    }

    /*****************************************************************************************************************************/
    /*************************************************** Goal Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addGoalToGoalTable(long user_id, long app_id, long type_id, long period_id, String target_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(TYPE_ID, type_id);
        values.put(PERIOD_ID, period_id);
        values.put(GOAL_TARGET_VALUE, target_value);
        long result = db.insert(TABLE_GOAL, null, values);
        db.close();
        return result;
    }

    public Long getUserIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    public String getUserEmailFromGoalTable(long goal_id) {
        return getUserEmailFromUserTable(getUserIdFromGoalTable(goal_id));
    }

    public Long getAppIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    public String getAppNameFromGoalTable(long goal_id) {
        return getAppNameFromAppTable(getAppIdFromGoalTable(goal_id));
    }

    public Long getTypeIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(TYPE_ID));
    }

    public String getTypeNameFromGoalTable(long goal_id) {
        return getTypeNameFromTypeTable(getTypeIdFromGoalTable(goal_id));
    }

    public Long getPeriodIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(PERIOD_ID));
    }

    public String getPeriodLengthFromGoalTable(long goal_id) {
        return getPeriodLengthFromPeriodTable(getPeriodIdFromGoalTable(goal_id));
    }

    public String getTargetValueIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(GOAL_TARGET_VALUE));
    }

    public String getGoalInfoFromGoalTable(long goal_id){
        String goalInfo = getUserEmailFromGoalTable(goal_id) + ", " + getAppNameFromGoalTable(goal_id)
                + ", " + getTypeNameFromGoalTable(goal_id) + ", " + getPeriodLengthFromGoalTable(goal_id)
                + ", " + getTargetValueIdFromGoalTable(goal_id);
        return goalInfo;
    }

    public long changeAppIdInGoalTable(long goal_id, long new_app_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_ID, new_app_id);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
        return result;
    }

    public long changeTypeIdInGoalTable(long goal_id, long new_type_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_ID, new_type_id);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
        return result;
    }

    public long changePeriodIdInGoalTable(long goal_id, long new_period_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERIOD_ID, new_period_id);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
        return result;
    }

    public long changeTargetValueInGoalTable(long goal_id, String new_target_value){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(GOAL_TARGET_VALUE, new_target_value);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
        return result;
    }

    public long changeGoalInGoalTable(long goal_id, long new_app_id, long new_type_id, long new_period_id, String new_target_value){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_ID, new_app_id);
        values.put(TYPE_ID, new_type_id);
        values.put(PERIOD_ID, new_period_id);
        values.put(GOAL_TARGET_VALUE, new_target_value);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
        return result;
    }

    public boolean deleteGoalFromUserTable(long goal_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_GOAL, GOAL_ID + "='" + goal_id + "'", null) > 0;
    }

    public List<Goal> getGoalListFromGoalTable(long user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Goal> goalList = new ArrayList<Goal>();

        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + USER_ID + " = " + user_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            Goal currentGoal = new Goal();
            currentGoal.setGoal_id(cursor.getLong(cursor.getColumnIndex(GOAL_ID)));
            currentGoal.setApp_id(cursor.getLong(cursor.getColumnIndex(APP_ID)));
            currentGoal.setApp_name(getAppNameFromAppTable(currentGoal.getApp_id()));
            currentGoal.setType_id(cursor.getLong(cursor.getColumnIndex(TYPE_ID)));
            currentGoal.setType_name(getTypeNameFromTypeTable(currentGoal.getType_id()));
            currentGoal.setPeriod_id(cursor.getLong(cursor.getColumnIndex(PERIOD_ID)));
            currentGoal.setPeriod_length(getPeriodLengthFromPeriodTable(currentGoal.getPeriod_id()));
            currentGoal.setTarget_value(cursor.getString(cursor.getColumnIndex(GOAL_TARGET_VALUE)));
            goalList.add(currentGoal);
        }
        cursor.close();
        return goalList;
    }

    /*****************************************************************************************************************************/
    /**************************************************** API Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addApiToApiTable(long user_id, long app_id, boolean registered, String app_username, String app_email, String app_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(API_REGISTERED, registered);
        values.put(API_USERNAME, app_username);
        values.put(API_EMAIL, app_email);
        values.put(API_PASSWORD, app_email);
        long result = db.insert(TABLE_API, null, values);
        db.close();
        return result;
    }

    public Long getUserIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    public String getAppNameFromApiTable(long api_id) {
        return getAppNameFromAppTable(getUserIdFromApiTable(api_id));
    }

    public Long getAppIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    public String getUserEmailFromApiTable(long api_id) {
        return getUserEmailFromUserTable(getUserIdFromApiTable(api_id));
    }

    public boolean isRegisteredInApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(cursor.getColumnIndex(API_REGISTERED)) > 0;
    }

    public String getAppUsernameFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(API_USERNAME));
    }

    public String getAppEmailFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(API_EMAIL));
    }

    public String getAppPasswordFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(API_PASSWORD));
    }

    public String getApiInfoFromApiTable(long api_id){
        String api_info = getUserEmailFromApiTable(api_id) + ", " + getAppNameFromApiTable(api_id)
                + ", " + isRegisteredInApiTable(api_id) + ", " + getAppUsernameFromApiTable(api_id)
                + ", " + getAppEmailFromApiTable(api_id) + ", " + getAppPasswordFromApiTable(api_id);
        return api_info;
    }

    public long changeRegisteredInApiTable(long api_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        boolean registered = isRegisteredInApiTable(api_id);
        if (registered == true) // if true then will change to false
            values.put(API_REGISTERED, false);
        else                    // if false then will change to true
            values.put(API_REGISTERED, true);

        long result = db.update(TABLE_API, values, API_ID + "=" + api_id, null);
        db.close();
        return result;
    }

    public long changeAppUsernameInApiTable(long api_id, String new_api_username){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(API_USERNAME, new_api_username);
        long result = db.update(TABLE_API, values, API_ID + "=" + api_id, null);
        db.close();
        return result;
    }

    public long changeAppEmailInApiTable(long api_id, String new_api_email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(API_EMAIL, new_api_email);
        long result = db.update(TABLE_API, values, API_ID + "=" + api_id, null);
        db.close();
        return result;
    }

    public long changeAppPasswordInApiTable(long api_id, String new_api_password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(API_PASSWORD, new_api_password);
        long result = db.update(TABLE_API, values, API_ID + "=" + api_id, null);
        db.close();
        return result;
    }

    public List<ThirdPartyAppAndApi> getAppAndApiListFromLogTable(long user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ThirdPartyAppAndApi> appAndApiList = new ArrayList<ThirdPartyAppAndApi>();

        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + USER_ID + " = " + user_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            ThirdPartyAppAndApi currentAppAndApi = new ThirdPartyAppAndApi();
            currentAppAndApi.setApi_id(cursor.getLong(cursor.getColumnIndex(API_ID)));
            currentAppAndApi.setApp_id(cursor.getLong(cursor.getColumnIndex(APP_ID)));
            currentAppAndApi.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
            currentAppAndApi.setRegistered(cursor.getInt(cursor.getColumnIndex(API_REGISTERED)) > 0);
            currentAppAndApi.setApp_username(cursor.getString(cursor.getColumnIndex(API_USERNAME)));
            currentAppAndApi.setApp_email(cursor.getString(cursor.getColumnIndex(API_EMAIL)));
            currentAppAndApi.setApp_password(cursor.getString(cursor.getColumnIndex(API_PASSWORD)));
            appAndApiList.add(currentAppAndApi);
        }

        cursor.close();
        return appAndApiList;
    }

    /*****************************************************************************************************************************/
    /**************************************************** Log Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    public long addLogToLogTable(long user_id, long app_id, long steps_walked, double miles_walked, long calories_burned,
                                 long calories_consumed, long pulse, String blood_pressure, String date) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToLast();

        ContentValues values = new ContentValues();

        if (cursor.getCount() > 0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date lastDate = sdf.parse(cursor.getString(cursor.getColumnIndex(LOG_DATE)));
            Date currentDate = sdf.parse(date);

            long daysBetween = (currentDate.getTime() - lastDate.getTime())/(24*60*60*1000);

            values.put(USER_ID, user_id);
            values.put(APP_ID, app_id);
            values.put(LOG_STEPS_WALKED, 0);
            values.put(LOG_MILES_WALKED, 0);
            values.put(LOG_CALORIES_BURNED, 0);
            values.put(LOG_CALORIES_CONSUMED, 0);
            values.put(LOG_PULSE, 0);
            values.put(LOG_BLOOD_PRESSURE, "0");

            Log.e("Here!!!", "" + daysBetween);

            for (long i = -daysBetween + 1; i < 0; i++){
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(date));
                cal.add(Calendar.DAY_OF_YEAR, (int) i);
                //Date tempDate = sdf.parse(s.format(new Date(cal.getTimeInMillis())));
                values.put(LOG_DATE, sdf.format(new Date(cal.getTimeInMillis())));
                Log.e("Here!!!", "" + sdf.format(new Date(cal.getTimeInMillis())));
                db.insert(TABLE_LOG, null, values);
            }
        }

        selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE + " = " + "'" + date + "'";

        cursor = db.rawQuery(selectQuery, null);

        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_STEPS_WALKED, steps_walked);
        values.put(LOG_MILES_WALKED, miles_walked);
        values.put(LOG_CALORIES_BURNED, calories_burned);
        values.put(LOG_CALORIES_CONSUMED, calories_consumed);
        values.put(LOG_PULSE, pulse);
        values.put(LOG_BLOOD_PRESSURE, blood_pressure);
        values.put(LOG_DATE, date);

        long result = -1;
        int count = cursor.getCount();

        if (count > 1){
            Log.e(LOG_CAT, "Error! Too many instances with same date: " + count);
        }
        else if (count < 1){
            result = db.insert(TABLE_LOG, null, values);
        }
        else{
            result = db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE + " = " + "'" + date + "'", null);
        }
        db.close();
        return result;
    }

    public Long getUserIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    public String getUserEmailFromLogTable(long log_id) {
        return getUserEmailFromUserTable(getUserIdFromLogTable(log_id));
    }

    public Long getAppIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    public String getAppNameFromLogTable(long log_id) {
        String name = getAppNameFromAppTable(getAppIdFromLogTable(log_id));
        return name;
    }

    public long getStepsWalkedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(LOG_STEPS_WALKED));
    }

    public double getMilesWalkedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getDouble(cursor.getColumnIndex(LOG_MILES_WALKED));
    }

    public long getCaloriesBurnedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_BURNED));
    }

    public long getCaloriesConsumedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_CONSUMED));
    }

    public long getPulseFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(LOG_PULSE));
    }

    public String getBloodPressureFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(LOG_BLOOD_PRESSURE));
    }

    public String getDateFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(LOG_DATE));
    }

    public String getLogInfoFromLogTable(long log_id){
        String log_info = getUserEmailFromLogTable(log_id) + ", " + getAppNameFromLogTable(log_id)
                + ", " + getStepsWalkedFromLogTable(log_id) + ", " + String.format("%.2f", getMilesWalkedFromLogTable(log_id))
                + ", " + getCaloriesBurnedFromLogTable(log_id) + ", " + getCaloriesConsumedFromLogTable(log_id) + ", "
                + getPulseFromLogTable(log_id) + ", " + getBloodPressureFromLogTable(log_id) + ", "
                + getDateFromLogTable(log_id);
        return log_info;
    }

    public AppLog getAppLogFromLogTable(long user_id, long app_id, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        AppLog appLog = new AppLog();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE + " = " + "'" + date + "'";


        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.moveToFirst();
        if (count > 0){
            appLog.setLog_id(cursor.getLong(cursor.getColumnIndex(LOG_ID)));
            appLog.setSteps_walked(cursor.getLong(cursor.getColumnIndex(LOG_STEPS_WALKED)));
            appLog.setMiles_walked(cursor.getDouble(cursor.getColumnIndex(LOG_MILES_WALKED)));
            appLog.setCalories_burned(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_BURNED)));
            appLog.setCalories_consumed(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_CONSUMED)));
            appLog.setPulse(cursor.getLong(cursor.getColumnIndex(LOG_PULSE)));
            appLog.setBlood_pressure(cursor.getString(cursor.getColumnIndex(LOG_BLOOD_PRESSURE)));
            appLog.setDate(cursor.getString(cursor.getColumnIndex(LOG_DATE)));
        }
        else{
            Log.e(LOG_CAT, "appLog not found for: " + date);
        }

        cursor.close();
        return appLog;
    }

    public List<AppLog> getAppLogListFromLogTable(long user_id, long app_id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<AppLog> appLogList = new ArrayList<AppLog>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            AppLog currentAppLog = new AppLog();
            currentAppLog.setLog_id(cursor.getLong(cursor.getColumnIndex(LOG_ID)));
            currentAppLog.setSteps_walked(cursor.getLong(cursor.getColumnIndex(LOG_STEPS_WALKED)));
            currentAppLog.setMiles_walked(cursor.getDouble(cursor.getColumnIndex(LOG_MILES_WALKED)));
            currentAppLog.setCalories_burned(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_BURNED)));
            currentAppLog.setCalories_consumed(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_CONSUMED)));
            currentAppLog.setPulse(cursor.getLong(cursor.getColumnIndex(LOG_PULSE)));
            currentAppLog.setBlood_pressure(cursor.getString(cursor.getColumnIndex(LOG_BLOOD_PRESSURE)));
            currentAppLog.setDate(cursor.getString(cursor.getColumnIndex(LOG_DATE)));
            appLogList.add(currentAppLog);
        }
        cursor.close();
        return appLogList;
    }
}
