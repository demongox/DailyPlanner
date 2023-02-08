package com.hirkanico.dailyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.DailyTask;
import com.hirkanico.dailyplanner.library.DBManager;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAddTask;
    ImageButton btnReminder;
    ImageButton btnSearch;

    DBManager database;

    ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnReminder = findViewById(R.id.btnReminder);
        btnSearch = findViewById(R.id.btnSearch);

        Calendar date = Calendar.getInstance();
        String dayToday = android.text.format.DateFormat.format("EEEE", date).toString();


        Log.v("Day name", dayToday);
        Log.v("Day name number", String.valueOf(date.get(Calendar.DAY_OF_WEEK)-1));
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

        database = new DBManager(MainActivity.this);
        database.open();

        listener = index -> {
            Toast.makeText(MainActivity.this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
            Log.v("","");
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.showTodayTasks);

        ArrayList<DailyTask> allTodayTask = database.fetchDailyPlan();
        DailyPlannerAdapter adapter = new DailyPlannerAdapter(MainActivity.this, allTodayTask, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

    }
}