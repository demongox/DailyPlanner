package com.hirkanico.dailyplanner.library;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hirkanico.dailyplanner.classes.DailyTask;

import java.util.ArrayList;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertNewPlane(String planTitle, String planDate, String planTime, String dailyRepeat) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.PLAN_TITLE, planTitle);
        contentValue.put(DatabaseHelper.NEW_PLAN_DOING_DATE, planDate);
        contentValue.put(DatabaseHelper.NEW_PLAN_DOING_TIME, planTime);
        contentValue.put(DatabaseHelper.PLAN_REPEAT_DAY, dailyRepeat);

        database.insert(DatabaseHelper.ALL_PLAN_TABLE_NAME, null, contentValue);
    }

    public void insertDailyPlane(String planId, String planDate, String planTime, String isDone) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.DAILY_PLAN_ID, planId);
        contentValue.put(DatabaseHelper.PLAN_DATE, planDate);
        contentValue.put(DatabaseHelper.PLAN_TITLE, planTime);
        contentValue.put(DatabaseHelper.PLAN_IS_DONE, isDone);

        database.insert(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, null, contentValue);
    }

    public Cursor fetchAllPlan() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.PLAN_TITLE, DatabaseHelper.NEW_PLAN_DOING_DATE, DatabaseHelper.NEW_PLAN_DOING_TIME, DatabaseHelper.PLAN_REPEAT_DAY};
        Cursor cursor = database.query(DatabaseHelper.ALL_PLAN_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor searchByAllDayRepeat() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.PLAN_TITLE, DatabaseHelper.NEW_PLAN_DOING_DATE, DatabaseHelper.NEW_PLAN_DOING_TIME, DatabaseHelper.PLAN_REPEAT_DAY};
        Cursor cursor = database.query(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, columns, DatabaseHelper.PLAN_REPEAT_DAY + "=?",
                new String[]{String.valueOf("all")}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<DailyTask> fetchDailyPlan() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.DAILY_PLAN_ID, DatabaseHelper.PLAN_DATE, DatabaseHelper.PLAN_TIME, DatabaseHelper.PLAN_IS_DONE};
        //Cursor cursor = database.query(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, columns, null, null, null, null, null);

        String MY_QUERY = "SELECT * FROM " + DatabaseHelper.DAILY_PLANNER_TABLE_NAME +
                " a Left JOIN " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " b ON a.plan_id=b.id";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{});

        Log.v("allPlan: ", "");

        ArrayList<DailyTask> allDailyTask = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID));
            //String planId = cursor.getString(Integer.parseInt(DatabaseHelper.DAILY_PLAN_ID));
            @SuppressLint("Range") String plan_title =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE));
            @SuppressLint("Range") String date =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_DATE));
            @SuppressLint("Range") String time =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME));
            @SuppressLint("Range") String isDone =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_IS_DONE));

//            assert false;
            //DailyTask daliy = new DailyTask(id, plan_title, date, time, isDone);
            allDailyTask.add(new DailyTask(id, plan_title, date, time, isDone));
            Log.d("Dbmanager : ", id + "-" + plan_title + "-" + date + "- " + time);
        }

        return allDailyTask;
    }

    public int updateAllPlane(long _id, String title, String date, String time, String repeat) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.PLAN_TITLE, title);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_DATE, date);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_TIME, time);
        contentValues.put(DatabaseHelper.PLAN_REPEAT_DAY, repeat);

        int i = database.update(DatabaseHelper.ALL_PLAN_TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public int updateDailyPlane(long _id, String allPlanId, String date, String time, String isDone) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.PLAN_TITLE, allPlanId);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_DATE, date);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_TIME, time);
        contentValues.put(DatabaseHelper.PLAN_REPEAT_DAY, isDone);

        int i = database.update(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public void deleteAllPlane(long _id) {
        database.delete(DatabaseHelper.ALL_PLAN_TABLE_NAME, DatabaseHelper.ID + "=" + _id, null);
    }

    public void deleteDailyPlane(long _id) {
        database.delete(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, DatabaseHelper.ID + "=" + _id, null);
    }

}