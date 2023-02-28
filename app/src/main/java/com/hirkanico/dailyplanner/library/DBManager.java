package com.hirkanico.dailyplanner.library;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hirkanico.dailyplanner.classes.DailyTask;

import java.lang.reflect.Array;
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

    public void insertNewPlane(String planTitle, String planDate, String planTime, String planDuration, String dailyRepeat, String priority) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.PLAN_TITLE, planTitle);
        contentValue.put(DatabaseHelper.NEW_PLAN_DOING_DATE, planDate);
        contentValue.put(DatabaseHelper.NEW_PLAN_DOING_TIME, planTime);
        contentValue.put(DatabaseHelper.NEW_PLAN_DURATION_TIME, planDuration);
        contentValue.put(DatabaseHelper.PLAN_REPEAT_DAY, dailyRepeat);
        contentValue.put(DatabaseHelper.PLAN_PRIORITY, priority);

        database.insert(DatabaseHelper.ALL_PLAN_TABLE_NAME, null, contentValue);
    }

    public void insertDailyPlane(String planId, String planDate, String planTime, String planDuration, String planPriority) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.DAILY_PLAN_ID, planId);
        contentValue.put(DatabaseHelper.PLAN_DATE, planDate);
        contentValue.put(DatabaseHelper.PLAN_TIME, planTime);
        contentValue.put(DatabaseHelper.NEW_PLAN_DURATION_TIME, planDuration);
        contentValue.put(DatabaseHelper.PLAN_IS_DONE, "false");
        contentValue.put(DatabaseHelper.PLAN_PRIORITY, planPriority);

        database.insert(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, null, contentValue);
    }

    @SuppressLint("Range")
    public ArrayList<DailyTask> fetchAllPlan() {
        String MY_QUERY = "SELECT * FROM " + DatabaseHelper.ALL_PLAN_TABLE_NAME;

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{});

        ArrayList<DailyTask> allDailyTask = new ArrayList<>();

        while(cursor.moveToNext()){
            Log.v("Id" , cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID));
            @SuppressLint("Range") String plan_title =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE));
            @SuppressLint("Range") String date =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_DATE));
            @SuppressLint("Range") String time =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME));
            @SuppressLint("Range") String repeat =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_REPEAT_DAY));
            @SuppressLint("Range") String duration =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEW_PLAN_DURATION_TIME));
            @SuppressLint("Range") String priority =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_PRIORITY));

            allDailyTask.add(new DailyTask(id, plan_title, date, time, "-", duration, repeat, priority));
        }

        return allDailyTask;
    }

    public Cursor fetchDailyPlanForWidget(String today) {

        String MY_QUERY = "SELECT a.id, a.plan_id,a.plan_date, a.plan_time, a.plan_duration,a.plan_is_done,a.plan_priority, b.plan_title FROM " + DatabaseHelper.DAILY_PLANNER_TABLE_NAME +
                " a Left JOIN " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " b ON a.plan_id=b.id WHERE a." + DatabaseHelper.PLAN_IS_DONE + " = 'false' AND a." +
                DatabaseHelper.PLAN_DATE + " = '" + today + "' OR a." + DatabaseHelper.PLAN_DATE + " = '-'";

        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{});
        return cursor;
    }

    @SuppressLint("Range")
    public ArrayList<DailyTask> searchByAllDayRepeat(String dayNumber, String todayDate) {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.PLAN_TITLE, DatabaseHelper.NEW_PLAN_DOING_DATE, DatabaseHelper.NEW_PLAN_DOING_TIME, DatabaseHelper.PLAN_REPEAT_DAY};
        //Cursor cursor = database.query(DatabaseHelper.ALL_PLAN_TABLE_NAME, columns, DatabaseHelper.PLAN_REPEAT_DAY + "=?", new String[]{String.valueOf("all")}, null, null, null, null);
        //Cursor cursor = database.query(DatabaseHelper.ALL_PLAN_TABLE_NAME, columns, null, null, null, null, null);
        //Log.v("DayNumber " , dayNumber);

        String MY_QUERY = "SELECT * FROM " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " WHERE " + DatabaseHelper.PLAN_REPEAT_DAY +
                " LIKE '%"+dayNumber+"%' OR " + DatabaseHelper.PLAN_DATE + " = '"+todayDate+"'";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{});

        ArrayList<DailyTask> allDailyTask = new ArrayList<>();

        while(cursor.moveToNext()){
            //Log.v("Id" , cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID));
            @SuppressLint("Range") String plan_title =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE));
            @SuppressLint("Range") String date =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_DATE));
            @SuppressLint("Range") String time =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME));
            @SuppressLint("Range") String duration =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEW_PLAN_DURATION_TIME));
            @SuppressLint("Range") String priority =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_PRIORITY));
            //@SuppressLint("Range") String isDone =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_IS_DONE));

            allDailyTask.add(new DailyTask(id, plan_title, date, time, "-", duration, priority));
        }

        return allDailyTask;
    }

    public ArrayList<DailyTask> fetchDailyPlan(String today) {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.DAILY_PLAN_ID, DatabaseHelper.PLAN_DATE, DatabaseHelper.PLAN_TIME, DatabaseHelper.PLAN_IS_DONE};
        //Cursor cursor = database.query(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, columns, null, null, null, null, null);

        String MY_QUERY = "SELECT a.id, a.plan_id,a.plan_date, a.plan_time, a.plan_duration,a.plan_is_done, a.plan_priority, b.plan_title FROM " + DatabaseHelper.DAILY_PLANNER_TABLE_NAME +
                " a Left JOIN " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " b ON a.plan_id=b.id WHERE a." +
                //" b ON a.plan_id=b.id WHERE a." + DatabaseHelper.PLAN_IS_DONE + " = 'false' AND a." +
                DatabaseHelper.PLAN_DATE + " = '" + today + "' OR a." + DatabaseHelper.PLAN_DATE + " = '-'";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{});

        //Log.v("allPlan: ", "");

        ArrayList<DailyTask> allDailyTask = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID));
            @SuppressLint("Range") String plan_title =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE));
            @SuppressLint("Range") String date =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_DATE));
            @SuppressLint("Range") String time =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME));
            @SuppressLint("Range") String duration =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEW_PLAN_DURATION_TIME));
            @SuppressLint("Range") String isDone =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_IS_DONE));
            @SuppressLint("Range") String priority =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_PRIORITY));

            allDailyTask.add(new DailyTask(id, plan_title, date, time, isDone, duration, priority));
        }

        return allDailyTask;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllTodayInsertedTasks(String today){

        ArrayList<String> allInsertedId = new ArrayList<>();

        String MY_QUERY = "SELECT * FROM " + DatabaseHelper.DAILY_PLANNER_TABLE_NAME +
                " a Left JOIN " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " b ON a.plan_id=b.id WHERE  a." + DatabaseHelper.PLAN_DATE + " = '"+today+"'";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{});

        while (cursor.moveToNext()) {
            allInsertedId.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAILY_PLAN_ID)));
        }

        //database.close();

        return allInsertedId;
    }

    public DailyTask getSingleTask(String _id){
        String MY_QUERY = "SELECT * FROM " + DatabaseHelper.ALL_PLAN_TABLE_NAME +
                " WHERE " + DatabaseHelper.ID + " = '" + _id + "'";

        DailyTask allDailyTask = null;
        try (Cursor cursor = database.rawQuery(MY_QUERY, new String[]{})) {
            while (cursor.moveToNext()) {
                //Log.v("Id" , cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID));
                @SuppressLint("Range") String plan_title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_DATE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME));
                @SuppressLint("Range") String repeat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_REPEAT_DAY));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEW_PLAN_DURATION_TIME));
                @SuppressLint("Range") String priority = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_PRIORITY));

                allDailyTask = new DailyTask(id, plan_title, date, time, "-", duration, repeat, priority);
            }
        }

        //database.close();

        return allDailyTask;
    }

    public int updateAllPlane(String _id, String title, String date, String time, String repeat, String priority) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.PLAN_TITLE, title);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_DATE, date);
        contentValues.put(DatabaseHelper.NEW_PLAN_DOING_TIME, time);
        contentValues.put(DatabaseHelper.PLAN_REPEAT_DAY, repeat);
        contentValues.put(DatabaseHelper.PLAN_PRIORITY, priority);

        int i = database.update(DatabaseHelper.ALL_PLAN_TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public int updateDailyPlane(String _id) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.PLAN_IS_DONE, "true");

        int i = database.update(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public void deleteFromAllPlaneTable(String _id) {
        database.delete(DatabaseHelper.ALL_PLAN_TABLE_NAME, DatabaseHelper.ID + "=" + _id, null);
    }

    public void deleteDailyPlaneWithAllPlanId(String _id) {
        database.delete(DatabaseHelper.DAILY_PLANNER_TABLE_NAME, DatabaseHelper.DAILY_PLAN_ID + "='" + _id +"'", null);
    }

}