package com.hirkanico.dailyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.hirkanico.dailyplanner.classes.DailyTask;
import com.hirkanico.dailyplanner.library.DBManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditDailyTask extends AppCompatActivity {

    EditText editTaskName;
    EditText editTaskTime;
    EditText editTaskDuration;
    EditText editTaskDate;

    TextView txtShowTodayDate;
    TextView txtTaskDate;
    TextView txtDayRepeat;

    String daySequence;

    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;

    RadioButton chkLow;
    RadioButton chkHigh;
    RadioButton chkMedium;

    String taskId;

    boolean isNotification = false;

    private RadioGroup radioGroup;

    AppCompatButton btnAddTask;

    DBManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_layout);

        monday = findViewById(R.id.chkMonday);
        tuesday = findViewById(R.id.chkTuesday);
        wednesday = findViewById(R.id.chkWednesday);
        thursday = findViewById(R.id.chkThursday);
        friday = findViewById(R.id.chkFriday);
        saturday = findViewById(R.id.chkSaturday);
        sunday = findViewById(R.id.chkSunday);

        chkLow = findViewById(R.id.chkLow);
        chkMedium = findViewById(R.id.chkMedium);
        chkHigh = findViewById(R.id.chkHigh);

        radioGroup = findViewById(R.id.priorityRadioGroup);
        radioGroup.clearCheck();

        database = new DBManager(EditDailyTask.this);
        database.open();

        txtShowTodayDate = findViewById(R.id.txtShowDate);
        txtTaskDate = findViewById(R.id.txtTaskDate);
        txtDayRepeat = findViewById(R.id.txtDayRepeat);

        txtShowTodayDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        AppCompatButton btnSubmitNewReminder = findViewById(R.id.btnSubmitNewReminder);

        editTaskName = findViewById(R.id.editEditTaskName);
        editTaskTime = findViewById(R.id.editEditTaskTime);
        editTaskDuration = findViewById(R.id.editEditTaskDuration);
        editTaskDate = findViewById(R.id.editEditTaskDate);

        txtTaskDate.setOnClickListener(v -> selectDate());

        editTaskTime.setOnClickListener(v -> selectTime());

        database.open();

        getDataFromDB();

        btnSubmitNewReminder.setOnClickListener(view1 -> {

            if (editTaskTime.getText().toString().equals("") || editTaskDuration.getText().toString().equals("")) {
                Toast.makeText(EditDailyTask.this, "Time and Duration Must be determine", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(EditDailyTask.this, "No priority has been selected", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            String priority = "";
            if(radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkLow).getId())
                priority = "low";


            if(radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkMedium).getId())
                priority = "medium";


            if (radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkHigh).getId())
                priority = "high";

            if (isNotification){
                checkDaySequence();
                database.updateAllPlane(taskId,editTaskName.getText().toString(), "-", editTaskDuration.getText().toString(), daySequence, priority);
            }else{
                database.updateAllPlane(taskId,editTaskName.getText().toString(), editTaskDate.getText().toString(), editTaskDuration.getText().toString(), "-", priority);
            }

            editTaskName.setText("");
            editTaskTime.setText("");
            editTaskDuration.setText("");
            monday.setChecked(false);
            tuesday.setChecked(false);
            wednesday.setChecked(false);
            thursday.setChecked(false);
            friday.setChecked(false);
            saturday.setChecked(false);
            sunday.setChecked(false);
            radioGroup.clearCheck();
            Toast.makeText(EditDailyTask.this, "Task Updated", Toast.LENGTH_SHORT).show();


        });
    }

    public void selectTime() {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting our hour, minute.
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditDailyTask.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // on below line we are setting selected time
                        // in our text view.
                        editTaskTime.setText(new StringBuilder().append(hourOfDay).append(":").append(minute).toString());
                    }
                }, hour, minute, false);
        // at last we are calling show to
        // display our time picker dialog.
        timePickerDialog.show();
    }

    public void selectDate() {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                EditDailyTask.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        editTaskTime.setText(new StringBuilder().append(dayOfMonth).append("-").append(monthOfYear + 1).append("-").append(year).toString());

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }

    public void checkDaySequence() {
        daySequence = "";
        if (monday.isChecked()) {
            daySequence += "1-";
        }
        if (tuesday.isChecked()) {
            daySequence += "2-";
        }
        if (wednesday.isChecked()) {
            daySequence += "3-";
        }
        if (thursday.isChecked()) {
            daySequence += "4-";
        }
        if (friday.isChecked()) {
            daySequence += "5-";
        }
        if (saturday.isChecked()) {
            daySequence += "6-";
        }
        if (sunday.isChecked()) {
            daySequence += "7-";
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromDB();
    }

    public void getDataFromDB(){
        if (getIntent().getExtras() != null) {
            taskId = getIntent().getStringExtra("taskId");
            //Log.d("commodityId : ", commodityId);
            DailyTask getTask = database.getSingleTask(taskId);

            editTaskName.setText(getTask.taskName);
            editTaskTime.setText(getTask.taskTime);
            editTaskDuration.setText(getTask.planDuration);

            if (getTask.planPriority.equals("low"))
                chkLow.setChecked(true);
            if (getTask.planPriority.equals("medium"))
                chkMedium.setChecked(true);
            if (getTask.planPriority.equals("high"))
                chkHigh.setChecked(true);

            if (getTask.planRepeat.equals("-")){

                isNotification = true;

                txtTaskDate.setVisibility(View.VISIBLE);
                editTaskDate.setVisibility(View.VISIBLE);
                editTaskDate.setText(getTask.taskDate);

                txtDayRepeat.setVisibility(View.INVISIBLE);
                monday.setVisibility(View.INVISIBLE);
                tuesday.setVisibility(View.INVISIBLE);
                wednesday.setVisibility(View.INVISIBLE);
                thursday.setVisibility(View.INVISIBLE);
                friday.setVisibility(View.INVISIBLE);
                saturday.setVisibility(View.INVISIBLE);
                sunday.setVisibility(View.INVISIBLE);

            }else {
                txtTaskDate.setVisibility(View.INVISIBLE);
                editTaskDate.setVisibility(View.INVISIBLE);

                txtDayRepeat.setVisibility(View.VISIBLE);
                monday.setVisibility(View.VISIBLE);
                tuesday.setVisibility(View.VISIBLE);
                wednesday.setVisibility(View.VISIBLE);
                thursday.setVisibility(View.VISIBLE);
                friday.setVisibility(View.VISIBLE);
                saturday.setVisibility(View.VISIBLE);
                sunday.setVisibility(View.VISIBLE);

                if (getTask.planRepeat.contains("1"))
                    monday.setChecked(true);
                if (getTask.planRepeat.contains("2"))
                    tuesday.setChecked(true);
                if (getTask.planRepeat.contains("3"))
                    wednesday.setChecked(true);
                if (getTask.planRepeat.contains("4"))
                    thursday.setChecked(true);
                if (getTask.planRepeat.contains("5"))
                    friday.setChecked(true);
                if (getTask.planRepeat.contains("6"))
                    saturday.setChecked(true);
                if (getTask.planRepeat.contains("7"))
                    sunday.setChecked(true);
            }
        }
    }
}
