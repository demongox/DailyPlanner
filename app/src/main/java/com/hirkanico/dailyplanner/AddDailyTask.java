package com.hirkanico.dailyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.hirkanico.dailyplanner.library.DBManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddDailyTask extends AppCompatActivity {

    EditText editTaskName;
    EditText editTaskTime;
    EditText editTaskDuration;

    TextView txtShowDate;

    String daySequence;

    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;

    AppCompatButton btnAddTask;

    DBManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);

        monday = findViewById(R.id.chkMonday);
        tuesday = findViewById(R.id.chkTuesday);
        wednesday = findViewById(R.id.chkWednesday);
        thursday = findViewById(R.id.chkThursday);
        friday = findViewById(R.id.chkFriday);
        saturday = findViewById(R.id.chkSaturday);
        sunday = findViewById(R.id.chkSunday);


        database = new DBManager(AddDailyTask.this);
        database.open();

        txtShowDate = findViewById(R.id.txtShowDate);

        txtShowDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        AppCompatButton btnSubmitNewReminder = findViewById(R.id.btnSubmitNewReminder);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskTime = findViewById(R.id.editTaskDate);
        editTaskDuration = findViewById(R.id.editTaskTime);

       //editTaskDuration.setOnClickListener(v -> selectTime());

        editTaskTime.setOnClickListener(v -> selectTime());

        database.open();

        btnSubmitNewReminder.setOnClickListener(view1 -> {

            if (editTaskTime.getText().toString().equals("") || editTaskDuration.getText().toString().equals("")) {
                Toast.makeText(AddDailyTask.this, "Time and Duration Must be determine", Toast.LENGTH_SHORT).show();
                return;
            }

            checkDaySequence();

            database.insertNewPlane(editTaskName.getText().toString(), "-", editTaskTime.getText().toString(), editTaskDuration.getText().toString(), daySequence);
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
            Toast.makeText(AddDailyTask.this, "Task added", Toast.LENGTH_SHORT).show();
        });
    }

    public void selectTime() {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting our hour, minute.
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddDailyTask.this,
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
                AddDailyTask.this,
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

    public void checkDaySequence(){
        daySequence = "";
        if(monday.isChecked()){
            daySequence += "1-";
        }
        if(tuesday.isChecked()){
            daySequence += "2-";
        }
        if(wednesday.isChecked()){
            daySequence += "3-";
        }
        if(thursday.isChecked()){
            daySequence += "4-";
        }
        if(friday.isChecked()){
            daySequence += "5-";
        }
        if(saturday.isChecked()){
            daySequence += "6-";
        }
        if(sunday.isChecked()){
            daySequence += "7-";
        }
    }
}