package com.hirkanico.dailyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.DailyTask;
import com.hirkanico.dailyplanner.library.DBManager;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAddTask;
    ImageButton btnReminder;
    ImageButton btnSearch;

    AppCompatButton btnShowAllTasks;

    private static Context mContext;

    DBManager database;

    ClickListener listener;

    ArrayList<DailyTask> todayTask;
    RecyclerView recyclerView;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    ExecutorService executorService =
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnReminder = findViewById(R.id.btnReminder);
        btnSearch = findViewById(R.id.btnSearch);

        btnShowAllTasks = findViewById(R.id.btnShowAllTasks);

        mContext  = MainActivity.this;

       //String dayToday = android.text.format.DateFormat.format("EEEE", date).toString();


        //og.v("Day name", dayToday);
        //Log.v("Day name number", String.valueOf(date.get(Calendar.DAY_OF_WEEK)-1));
/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DayOfWeek dow = DayOfWeek.of(1);
            DayOfWeek dow1 = DayOfWeek.of(2);
            DayOfWeek dow2 = DayOfWeek.of(3);
            DayOfWeek dow3 = DayOfWeek.of(4);
            DayOfWeek dow4 = DayOfWeek.of(5);
            DayOfWeek dow5 = DayOfWeek.of(6);
            DayOfWeek dow6 = DayOfWeek.of(7);
            Log.v("Day" ,dow.toString());
            Log.v("Day" ,dow1.toString());
            Log.v("Day" ,dow2.toString());
            Log.v("Day" ,dow3.toString());
            Log.v("Day" ,dow4.toString());
            Log.v("Day" ,dow5.toString());
            Log.v("Day" ,dow6.toString());
        }
*/
        btnAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddDailyTask.class);
            startActivity(intent);
        });

        btnReminder.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Reminder.class);
            startActivity(intent);
        });

        btnShowAllTasks.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowAllTasks.class);
            startActivity(intent);
        });

        database = new DBManager(MainActivity.this);
        database.open();

        listener = index -> {
            //Toast.makeText(MainActivity.this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
            String doneTaskId = todayTask.get(index).id;
            database.updateDailyPlane(doneTaskId);
            fillTodayLists();
        };

        fillTodayLists();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillTodayLists();
    }

    private void fillTodayLists(){

        Calendar date = Calendar.getInstance();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.v("today", today);
        String todayNumber = String.valueOf(date.get(Calendar.DAY_OF_WEEK));
        Log.v("today", todayNumber);

        ArrayList<DailyTask> allTodayTask = database.searchByAllDayRepeat(todayNumber,today);
        ArrayList<String> allInsertedTask = database.getAllTodayInsertedTasks(today);

        for (int i = 0; i < allInsertedTask.size(); i++) {
            for (int j = 0; j < allTodayTask.size(); j++) {
                if (allInsertedTask.get(i).equals(allTodayTask.get(j).id)) {
                    allTodayTask.remove(j);
                    Log.e("array 2 ", allTodayTask.toString());
                    j = -1;
                }
            }
        }

        for (DailyTask allTask: allTodayTask) {
            database.insertDailyPlane(allTask.taskName, today, allTask.taskTime,allTask.planDuration);
            //todayTask.add(new DailyTask(allTask.id, allTask.taskName, today, allTask.taskTime, allTask.planDuration, "false"));
        }

        todayTask = database.fetchDailyPlan(today);

        recyclerView = (RecyclerView) findViewById(R.id.showTodayTasks);

        //ArrayList<DailyTask> allTodayTask = database.fetchDailyPlan();

        DailyPlannerAdapter adapter = new DailyPlannerAdapter(MainActivity.this, todayTask, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
}