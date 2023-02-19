package com.hirkanico.dailyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.DailyTask;
import com.hirkanico.dailyplanner.library.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowAllTasks extends AppCompatActivity {

    ImageButton btnAddTask;
    ImageButton btnReminder;
    ImageButton btnSearch;

    DBManager database;

    ClickListener listener;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_tasks_layout);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnReminder = findViewById(R.id.btnReminder);
        btnSearch = findViewById(R.id.btnSearch);

        btnAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(ShowAllTasks.this, AddDailyTask.class);
            startActivity(intent);
        });

        btnReminder.setOnClickListener(view -> {
            Intent intent = new Intent(ShowAllTasks.this, Reminder.class);
            startActivity(intent);
        });

        database = new DBManager(ShowAllTasks.this);
        database.open();

        listener = index -> {
            //Toast.makeText(MainActivity.this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
            //String doneTaskId = allTasks.get(index).id;
            //database.updateDailyPlane(doneTaskId);
            //fillAllTasks();
        };

        fillAllTasks();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillAllTasks();
    }

    private void fillAllTasks(){

        Calendar date = Calendar.getInstance();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ArrayList<DailyTask> allTodayTask = database.fetchAllPlan(today);

        recyclerView = findViewById(R.id.showTodayTasks);

        DailyPlannerAdapter adapter = new DailyPlannerAdapter(ShowAllTasks.this, allTodayTask, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllTasks.this));
        recyclerView.setAdapter(adapter);
    }
}