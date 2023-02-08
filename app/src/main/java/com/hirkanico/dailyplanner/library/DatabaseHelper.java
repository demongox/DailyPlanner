package com.hirkanico.dailyplanner.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String ALL_PLAN_TABLE_NAME = "all_plan";
    public static final String DAILY_PLANNER_TABLE_NAME = "daily_planner";

    // Table columns
    public static final String ID = "id";
    public static final String PLAN_TITLE = "plan_title";
    public static final String NEW_PLAN_DOING_DATE = "plan_date";
    public static final String NEW_PLAN_DOING_TIME = "plan_time";
    public static final String NEW_PLAN_DURATION_TIME = "plan_duration";
    public static final String PLAN_REPEAT_DAY = "plan_repeat_day";

    public static final String DAILY_PLAN_ID = "plan_id";
    public static final String PLAN_DATE = "plan_date";
    public static final String PLAN_TIME = "plan_time";
    public static final String PLAN_IS_DONE = "plan_is_done";

    // Database Information
    static final String DB_NAME = "DAILY_PLANNER.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_ALL_PLAN_TABLE = "create table if not exists " + ALL_PLAN_TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PLAN_TITLE + " TEXT NOT NULL, "
            + NEW_PLAN_DOING_DATE + " TEXT NOT NULL, " + NEW_PLAN_DOING_TIME + " TEXT NOT NULL, "
            + NEW_PLAN_DURATION_TIME + " TEXT NOT NULL, " + PLAN_REPEAT_DAY + " TEXT NOT NULL);";

    private static final String CREATE_DAILY_PLANNER_TABLE = "create table if not exists " + DAILY_PLANNER_TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DAILY_PLAN_ID + " TEXT NOT NULL, "
            + PLAN_DATE + " TEXT NOT NULL, " + PLAN_TIME + " TEXT NOT NULL, "
            + NEW_PLAN_DURATION_TIME + " TEXT NOT NULL, "+ PLAN_IS_DONE + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALL_PLAN_TABLE);
        db.execSQL(CREATE_DAILY_PLANNER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /*
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ALL_PLAN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_DAILY_PLANNER_TABLE);
        onCreate(db);
        */
    }

}
