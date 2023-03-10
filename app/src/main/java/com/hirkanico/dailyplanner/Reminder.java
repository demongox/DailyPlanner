package com.hirkanico.dailyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.hirkanico.dailyplanner.library.DBManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reminder extends AppCompatActivity {

    EditText editTaskName;
    EditText editTaskDate;
    EditText editTaskTime;
    EditText editTaskDuration;

    TextView txtShowDate;

    AppCompatButton btnAddTask;

    private RadioGroup radioGroup;

    DBManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder_layout);

        radioGroup = findViewById(R.id.priorityRadioGroup);
        radioGroup.clearCheck();

        database = new DBManager(Reminder.this);
        database.open();

        txtShowDate = findViewById(R.id.txtShowDate);

        txtShowDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        AppCompatButton btnSubmitNewReminder = findViewById(R.id.btnSubmitNewReminder);

        editTaskName = findViewById(R.id.editReminderTaskName);
        editTaskDate = findViewById(R.id.editReminderTaskDate);
        editTaskTime = findViewById(R.id.editReminderTaskTime);
        editTaskDuration = findViewById(R.id.editReminderTaskDuration);

        editTaskTime.setOnClickListener(v -> selectTime());

        editTaskDate.setOnClickListener(v -> selectDate());

        database.open();

        btnSubmitNewReminder.setOnClickListener(view1 -> {

            if (editTaskDate.getText().toString().equals("") || editTaskTime.getText().toString().equals("")) {
                Toast.makeText(Reminder.this, "Date and Time Must be determine", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(Reminder.this, "No priority has been selected", Toast.LENGTH_SHORT).show();
                return;
            }


            String priority = "";
            if(radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkReminderLow).getId())
                priority = "low";


            if(radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkReminderMedium).getId())
                priority = "medium";


            if (radioGroup.getCheckedRadioButtonId() == findViewById(R.id.chkReminderHigh).getId())
                priority = "high";


            database.insertNewPlane(editTaskName.getText().toString(), editTaskDate.getText().toString(), editTaskTime.getText().toString(), editTaskDuration.getText().toString(), "-", priority);
            editTaskName.setText("");
            editTaskDate.setText("");
            editTaskTime.setText("");
            editTaskDuration.setText("");
            Toast.makeText(Reminder.this, "Task added", Toast.LENGTH_SHORT).show();
        });
    }

    public void selectTime() {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting our hour, minute.
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(Reminder.this,
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
        DecimalFormat mFormat= new DecimalFormat("00");
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                Reminder.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        editTaskDate.setText(new StringBuilder().append(year).append("-").append(mFormat.format(monthOfYear + 1)).append("-").append(mFormat.format(dayOfMonth)).toString());

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }
}