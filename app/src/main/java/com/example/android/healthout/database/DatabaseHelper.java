package com.example.android.healthout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.healthout.dataEntities.AppLog;
import com.example.android.healthout.dataEntities.Goal;

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
    private static final String DATABASE_NAME = "HealthOut.db";

    // Table Names
    private static final String TABLE_USER = "User";
    private static final String TABLE_APP = "App";
    private static final String TABLE_TYPE = "Type";
    private static final String TABLE_PERIOD = "Period";
    private static final String TABLE_GOAL = "Goal";
    private static final String TABLE_API = "API";
    private static final String TABLE_LOG = "Log";
    private static final String TABLE_DATE = "Date";

    // User Table - column names
    private static final String USER_ID = "user_id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "Password";

    // App Table - column names
    private static final String APP_ID = "app_id";
    private static final String APP_NAME = "Name";

    // Type Table - column names
    private static final String TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";

    // Period Table - column names
    private static final String PERIOD_ID = "period_id";
    private static final String PERIOD_LENGTH = "period_length";

    // Goal Table - column names
    private static final String GOAL_ID = "goal_id";
    private static final String GOAL_TARGET_VALUE = "target_value";

    // API Table - column names
    private static final String API_ID = "api_id";
    private static final String API_REGISTERED = "registered";
    private static final String API_USERNAME = "api_username";
    private static final String API_EMAIL = "api_email";
    private static final String API_PASSWORD = "api_password";

    // Log Table - column names
    private static final String LOG_ID = "log_id";
    private static final String LOG_STEPS_WALKED = "steps_walked";
    private static final String LOG_MILES_WALKED = "miles_walked";
    private static final String LOG_CALORIES_BURNED = "calories_burned";
    private static final String LOG_CALORIES_CONSUMED = "calories_consumed";
    private static final String LOG_PULSE = "pulse";
    private static final String LOG_DATE_LONG = "date_long";
    private static final String LOG_DATE_STRING = "date_string";

    // Date Table - column names
    private static final String DATE_ID = "date_id";
    private static final String DATE_FIRST = "first";
    private static final String DATE_LAST = "last";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // User Table -- created
        database.execSQL("CREATE TABLE " + TABLE_USER +
                " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_USERNAME + " TEXT, " + USER_PASSWORD + " TEXT)");
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
                + LOG_PULSE + " INTEGER, " + LOG_DATE_LONG + " LONG, " + LOG_DATE_STRING + " TEXT)");
        // Date Table -- created
        database.execSQL("CREATE TABLE " + TABLE_DATE +
                " (" + DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, " + APP_ID + " INTEGER, "
                + DATE_FIRST + " LONG, " + DATE_LAST + " LONG)");

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

        // Period Table -- preload data
        ContentValues cvPeriod = new ContentValues();
        cvPeriod.put(PERIOD_LENGTH, "Daily");
        database.insert(TABLE_PERIOD, null, cvPeriod);
        cvPeriod.put(PERIOD_LENGTH, "Weekly");
        database.insert(TABLE_PERIOD, null, cvPeriod);
        cvPeriod.put(PERIOD_LENGTH, "Monthly");
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

    public long addUserToUserTable(String username, String password) {
        long result = -2;

        if (!checkUsernameInUserTable(username)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_USERNAME, username);
            values.put(USER_PASSWORD, password);
            result = db.insert(TABLE_USER, null, values);
            db.close();
        }

        return result;
    }

    private String getUserusernameFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getString(cursor.getColumnIndex(USER_USERNAME));
    }

    public String getUserPasswordFromUserTable(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
    }

    public long getUserIdFromUserTable(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID};
        String selection = USER_USERNAME + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        long id = -1;
        if (cursor.getCount() > 0) {
            Log.d(LOG_CAT, "YOLO SWAG");
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndex(USER_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    public long changeusernameInUserTable(long user_id, String new_username){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, new_username);
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

    private boolean checkUsernameInUserTable(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID};
        String selection = USER_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public void deleteAccountFromUserTable(long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, USER_ID + "='" + user_id + "'", null);
        
        deleteUserFromGoalTable(user_id);
        deleteUserFromApiTable(user_id);
        deleteUserFromDateTable(user_id);
        deleteUserFromLogTable(user_id);
    }

    public boolean checkUserInUserTable(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID};
        String selection = USER_USERNAME + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
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

    private String getAppNameFromAppTable(long app_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_ID + " = " + app_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getString(cursor.getColumnIndex(APP_NAME));
    }

    public long getAppIdFromAppTable(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_APP + " WHERE "
                + APP_NAME + " = '" + name + "'";

        //Log.e(LOG_CAT, selectQuery);
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

    private String getTypeNameFromTypeTable(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_ID + " = " + type_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(TYPE_NAME));
    }

    public long getTypeIdFromTypeTable(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                + TYPE_NAME + " = '" + name + "'";

        //Log.e(LOG_CAT, selectQuery);
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

    private String getPeriodLengthFromPeriodTable(long period_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_ID + " = " + period_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(PERIOD_LENGTH));
    }

    public long getPeriodIdFromPeriodTable(String length) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PERIOD + " WHERE "
                + PERIOD_LENGTH + " = '" + length + "'";

        //Log.e(LOG_CAT, selectQuery);
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

    public void addGoalToGoalTable(long user_id, long app_id, long type_id, long period_id, String target_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(TYPE_ID, type_id);
        values.put(PERIOD_ID, period_id);
        values.put(GOAL_TARGET_VALUE, target_value);
        long result = db.insert(TABLE_GOAL, null, values);
        db.close();
    }

    private Long getUserIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    private String getUserusernameFromGoalTable(long goal_id) {
        return getUserusernameFromUserTable(getUserIdFromGoalTable(goal_id));
    }

    private Long getAppIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    private String getAppNameFromGoalTable(long goal_id) {
        return getAppNameFromAppTable(getAppIdFromGoalTable(goal_id));
    }

    private Long getTypeIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(TYPE_ID));
    }

    private String getTypeNameFromGoalTable(long goal_id) {
        return getTypeNameFromTypeTable(getTypeIdFromGoalTable(goal_id));
    }

    private Long getPeriodIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(PERIOD_ID));
    }

    private String getPeriodLengthFromGoalTable(long goal_id) {
        return getPeriodLengthFromPeriodTable(getPeriodIdFromGoalTable(goal_id));
    }

    private String getTargetValueIdFromGoalTable(long goal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOAL + " WHERE "
                + GOAL_ID + " = " + goal_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(GOAL_TARGET_VALUE));
    }

    public String getGoalInfoFromGoalTable(long goal_id){
        return getUserusernameFromGoalTable(goal_id) + ", " + getAppNameFromGoalTable(goal_id)
                + ", " + getTypeNameFromGoalTable(goal_id) + ", " + getPeriodLengthFromGoalTable(goal_id)
                + ", " + getTargetValueIdFromGoalTable(goal_id);
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

    public void changeGoalInGoalTable(long goal_id, long new_app_id, long new_type_id, long new_period_id, String new_target_value){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_ID, new_app_id);
        values.put(TYPE_ID, new_type_id);
        values.put(PERIOD_ID, new_period_id);
        values.put(GOAL_TARGET_VALUE, new_target_value);
        long result = db.update(TABLE_GOAL, values, GOAL_ID + "=" + goal_id, null);
        db.close();
    }

    public void deleteGoalFromGoalTable(long goal_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOAL, GOAL_ID + "='" + goal_id + "'", null);
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

    public void deleteUserFromGoalTable (long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOAL, USER_ID + "='" + user_id + "'", null);
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
        values.put(API_PASSWORD, app_password);
        long result = db.insert(TABLE_API, null, values);
        db.close();
        return result;
    }

    private Long getUserIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    private String getAppNameFromApiTable(long api_id) {
        return getAppNameFromAppTable(getUserIdFromApiTable(api_id));
    }

    public Long getAppIdFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    private String getUserusernameFromApiTable(long api_id) {
        return getUserusernameFromUserTable(getUserIdFromApiTable(api_id));
    }

    private boolean isRegisteredInApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getInt(cursor.getColumnIndex(API_REGISTERED)) > 0;
    }

    private String getAppUsernameFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getString(cursor.getColumnIndex(API_USERNAME));
    }

    private String getAppEmailFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(API_EMAIL));
    }

    private String getAppPasswordFromApiTable(long api_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_API + " WHERE "
                + API_ID + " = " + api_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getString(cursor.getColumnIndex(API_PASSWORD));
    }

    public String getApiInfoFromApiTable(long api_id){
        return getUserusernameFromApiTable(api_id) + ", " + getAppNameFromApiTable(api_id)
                + ", " + isRegisteredInApiTable(api_id) + ", " + getAppUsernameFromApiTable(api_id)
                + ", " + getAppEmailFromApiTable(api_id) + ", " + getAppPasswordFromApiTable(api_id);
    }

    public long changeRegisteredInApiTable(long api_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        boolean registered = isRegisteredInApiTable(api_id);
        if (registered) // if true then will change to false
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

    private void deleteUserFromApiTable (long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API, USER_ID + "='" + user_id + "'", null);
    }

    /*****************************************************************************************************************************/
    /**************************************************** Date Table    methods **************************************************/
    /*****************************************************************************************************************************/

    private void addDateToDateTable (long user_id, long app_id, long date_long){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_DATE + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();

        if (cursor.getCount() < 1){
            values.put(USER_ID, user_id);
            values.put(APP_ID, app_id);
            values.put(DATE_FIRST, date_long);
            values.put(DATE_LAST, date_long);
            db.insert(TABLE_DATE, null, values);
        }
        else {
            cursor.moveToFirst();
            long firstDate = cursor.getLong(cursor.getColumnIndex(DATE_FIRST));
            long lastDate = cursor.getLong(cursor.getColumnIndex(DATE_LAST));

            boolean updatedFirst = false;
            boolean updatedLast = false;
            if (date_long < firstDate){
                values.put(DATE_FIRST, date_long);
                updatedFirst = true;
            }

            if (date_long > lastDate){
                values.put(DATE_LAST, date_long);
                updatedLast = true;
            }

            if (updatedFirst || updatedLast){
                db.update(TABLE_DATE, values, USER_ID + " = " + user_id + " and "
                        + APP_ID + " = " + app_id, null);
                //Log.e("Database_Debug","updateFirst: " + updatedFirst + " -- updateLast: " + updatedLast);
            }
        }
    }

    private long getDaysBetweenInDateTable (long user_id, long app_id, long date_long){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_DATE + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        long daysBetween = 0;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            long firstDate = cursor.getLong(cursor.getColumnIndex(DATE_FIRST));
            long lastDate = cursor.getLong(cursor.getColumnIndex(DATE_LAST));

            //Log.e("Database_Debug", date_long + " vs " + firstDate);
            daysBetween = (date_long - firstDate)/(24*60*60*1000);
            if (daysBetween < 0){
                return daysBetween;
            }
            //Log.e("Database_Debug", date_long + " vs " + lastDate);
            daysBetween = (date_long - lastDate)/(24*60*60*1000);
            if (daysBetween > 0){
                return daysBetween;
            }
        }
        return daysBetween;
    }

    public void deleteUserFromDateTable (long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATE, USER_ID + "='" + user_id + "'", null);
    }

    /*****************************************************************************************************************************/
    /**************************************************** Log Table    methods ***************************************************/
    /*****************************************************************************************************************************/

    private void fillInEmptyDaysToLogTable(long user_id, long app_id, long date_long){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_STEPS_WALKED, 0);
        values.put(LOG_MILES_WALKED, 0);
        values.put(LOG_CALORIES_BURNED, 0);
        values.put(LOG_CALORIES_CONSUMED, 0);
        values.put(LOG_PULSE, 0);

        if (cursor.getCount() > 0){
            Date inputedDate = new Date(date_long);

            long daysBetween = getDaysBetweenInDateTable(user_id, app_id, date_long);

            //Log.e("Database_Debug", "daysBetween: " + daysBetween);

            if (daysBetween < -1){
                for (long i = daysBetween; i < 0; i++){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(inputedDate);
                    cal.add(Calendar.DAY_OF_YEAR, (int) -i);
                    values.put(LOG_DATE_LONG, cal.getTimeInMillis());
                    values.put(LOG_DATE_STRING, sdf.format(new Date(cal.getTimeInMillis())));
                    //Log.e("Database_Debug", "" + sdf.format(new Date(cal.getTimeInMillis())));
                    db.insert(TABLE_LOG, null, values);
                }
            }
            else if (daysBetween > 1){
                for (long i = -daysBetween + 1; i < 0; i++){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(inputedDate);
                    cal.add(Calendar.DAY_OF_YEAR, (int) -i);
                    values.put(LOG_DATE_LONG, cal.getTimeInMillis());
                    values.put(LOG_DATE_STRING, sdf.format(new Date(cal.getTimeInMillis())));
                    //Log.e("Database_Debug", "" + sdf.format(new Date(cal.getTimeInMillis())));
                    db.insert(TABLE_LOG, null, values);
                }
            }
        }
        else{
            long today = new Date().getTime();
            long daysBetween = (date_long - today)/(24*60*60*1000);
            //Log.e("Database_Debug", "No dates in Date Table: " + daysBetween);

            if (daysBetween < 0){
                for (long i = daysBetween; i < 0; i++){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date inputedDate = new Date(date_long);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(inputedDate);
                    cal.add(Calendar.DAY_OF_YEAR, (int) -i);
                    values.put(LOG_DATE_LONG, cal.getTimeInMillis());
                    values.put(LOG_DATE_STRING, sdf.format(new Date(cal.getTimeInMillis())));
                    //("Database_Debug", "" + sdf.format(new Date(cal.getTimeInMillis())));
                    db.insert(TABLE_LOG, null, values);
                }
            }
        }

    }

    public long addLogToLogTable(long user_id, long app_id, long steps_walked, double miles_walked, long calories_burned,
                                 long calories_consumed, long pulse, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_STEPS_WALKED, steps_walked);
        values.put(LOG_MILES_WALKED, miles_walked);
        values.put(LOG_CALORIES_BURNED, calories_burned);
        values.put(LOG_CALORIES_CONSUMED, calories_consumed);
        values.put(LOG_PULSE, pulse);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);

        long result = -1;
        int count = cursor.getCount();

        //Log.e("Database_Debug", "count: " + count);
        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
        return result;
    }


    public void addStepsWalkedToLogTable(long user_id, long app_id, long steps_walked, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_STEPS_WALKED, steps_walked);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);

        int count = cursor.getCount();

        //Log.e("Database_Debug", "count: " + count);
        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
    }

    public void addMilesWalkedToLogTable(long user_id, long app_id, double miles_walked, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_MILES_WALKED, miles_walked);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);

        int count = cursor.getCount();

        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
    }

    public void addCaloriesBurnedToLogTable(long user_id, long app_id, long calories_burned, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_CALORIES_BURNED, calories_burned);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);

        int count = cursor.getCount();

        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
    }

    public void addCaloriesConsumedToLogTable(long user_id, long app_id, long calories_consumed, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_CALORIES_CONSUMED, calories_consumed);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);
        
        int count = cursor.getCount();

        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
    }

    public void addPulseToLogTable(long user_id, long app_id, long pulse, long date_long, String date_string) {
        SQLiteDatabase db = this.getWritableDatabase();

        fillInEmptyDaysToLogTable(user_id, app_id, date_long);

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = '" + date_string + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(APP_ID, app_id);
        values.put(LOG_PULSE, pulse);
        values.put(LOG_DATE_LONG, date_long);
        values.put(LOG_DATE_STRING, date_string);

        int count = cursor.getCount();

        if (count < 1){
            db.insert(TABLE_LOG, null, values);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Added Log: " + date_string);
        }
        else{
            db.update(TABLE_LOG, values, USER_ID + " = " + user_id + " and "
                    + APP_ID + " = " + app_id + " and " + LOG_DATE_STRING + " = " + "'" + date_string + "'", null);
            addDateToDateTable(user_id, app_id, date_long);
            //Log.e("Database_Debug", "Update Log: " + date_string);
        }
        db.close();
    }

    private Long getUserIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(USER_ID));
    }

    private String getUserusernameFromLogTable(long log_id) {
        return getUserusernameFromUserTable(getUserIdFromLogTable(log_id));
    }

    private Long getAppIdFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(APP_ID));
    }

    private String getAppNameFromLogTable(long log_id) {
        return getAppNameFromAppTable(getAppIdFromLogTable(log_id));
    }

    private long getStepsWalkedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(LOG_STEPS_WALKED));
    }

    private double getMilesWalkedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getDouble(cursor.getColumnIndex(LOG_MILES_WALKED));
    }

    private long getCaloriesBurnedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_BURNED));
    }

    private long getCaloriesConsumedFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_CONSUMED));
    }

    private long getPulseFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(LOG_PULSE));
    }

    private long getDateFromLogTable(long log_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + LOG_ID + " = " + log_id;

        //Log.e(LOG_CAT, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return cursor.getLong(cursor.getColumnIndex(LOG_DATE_LONG));
    }

    public String getLogInfoFromLogTable(long log_id){
        return getUserusernameFromLogTable(log_id) + ", " + getAppNameFromLogTable(log_id)
                + ", " + getStepsWalkedFromLogTable(log_id) + ", " + String.format("%.2f", getMilesWalkedFromLogTable(log_id))
                + ", " + getCaloriesBurnedFromLogTable(log_id) + ", " + getCaloriesConsumedFromLogTable(log_id) + ", "
                + getPulseFromLogTable(log_id) + ", " + getDateFromLogTable(log_id);
    }

    public List<AppLog> getAppLogListFromLogTable(long user_id, long app_id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<AppLog> appLogList = new ArrayList<AppLog>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " WHERE "
                + USER_ID + " = " + user_id + " and " + APP_ID + " = " + app_id;

        String[] columns = {LOG_ID, LOG_STEPS_WALKED, LOG_MILES_WALKED, LOG_CALORIES_BURNED,
                LOG_CALORIES_CONSUMED, LOG_PULSE, LOG_DATE_LONG, LOG_DATE_STRING};
        String selection = USER_ID + "=?" + " and " + APP_ID + "=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(app_id)};
        Cursor cursor = db.query(TABLE_LOG, columns, selection, selectionArgs, null, null, LOG_DATE_LONG + " ASC");

        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            do {
                AppLog currentAppLog = new AppLog();
                currentAppLog.setLog_id(cursor.getLong(cursor.getColumnIndex(LOG_ID)));
                currentAppLog.setSteps_walked(cursor.getLong(cursor.getColumnIndex(LOG_STEPS_WALKED)));
                currentAppLog.setMiles_walked(cursor.getDouble(cursor.getColumnIndex(LOG_MILES_WALKED)));
                currentAppLog.setCalories_burned(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_BURNED)));
                currentAppLog.setCalories_consumed(cursor.getLong(cursor.getColumnIndex(LOG_CALORIES_CONSUMED)));
                currentAppLog.setPulse(cursor.getLong(cursor.getColumnIndex(LOG_PULSE)));
                currentAppLog.setDate_long(cursor.getLong(cursor.getColumnIndex(LOG_DATE_LONG)));
                currentAppLog.setDate_string(cursor.getString(cursor.getColumnIndex(LOG_DATE_STRING)));
                appLogList.add(currentAppLog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return appLogList;
    }

    public void deleteUserFromLogTable (long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOG, USER_ID + "='" + user_id + "'", null);
    }
}
