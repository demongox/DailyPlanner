package com.hirkanico.dailyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.ClickListenerForEditDelete;
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

    ClickListenerForEditDelete listener;

    TextView txtShowDate;

    RecyclerView recyclerView;

    ArrayList<DailyTask> allTodayTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_tasks_layout);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnReminder = findViewById(R.id.btnReminder);
        btnSearch = findViewById(R.id.btnSearch);

        txtShowDate = findViewById(R.id.txtShowDate);

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

        listener = (index, kind) -> {
            String doneTaskId = allTodayTask.get(index).id;
            //String all_plan_id = allTodayTask.get(index).all_plan_id;
            //Toast.makeText(ShowAllTasks.this,"clicked item index is "+doneTaskId+" - "+ kind, Toast.LENGTH_LONG).show();
            if (kind.equals("delete")){
                database.deleteFromAllPlaneTable(doneTaskId);

                database.deleteDailyPlaneWithAllPlanId(doneTaskId);
            }

            if (kind.equals("edit")){
                Intent intent = new Intent(ShowAllTasks.this, EditDailyTask.class);
                intent.putExtra("taskId", doneTaskId); //Optional parameters
                startActivity(intent);
            }

            fillAllTasks();
        };

        fillAllTasks();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillAllTasks();
    }

    private void fillAllTasks(){

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        txtShowDate.setText(today);

        allTodayTask = database.fetchAllPlan();

        recyclerView = findViewById(R.id.showTodayTasks);

        AllPlanAdapter adapter = new AllPlanAdapter(ShowAllTasks.this, allTodayTask, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllTasks.this));
        recyclerView.setAdapter(adapter);
    }
}