package com.example.utilitiesappusingaws;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private TextView showTimeTV;
    private Button btnSetTime;
    private Button btnSetAlarm;
    private Button btnCancelAlarm;

    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        createNotificationChannel();

        btnSetTime = findViewById(R.id.setTimebutton);
        btnSetAlarm = findViewById(R.id.setAlarmbutton);
        btnCancelAlarm = findViewById(R.id.cancelAlarmbutton);
        showTimeTV = findViewById(R.id.showTimeTextView);
        showTimeTV.setText("Set your reminder");

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePicker();
                showTimeTV.setText("Set your reminder");
            }
        });

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setAlarm();
            showTimeTV.setText("Reminder On!");
        }
    });

        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                showTimeTV.setText("Set your reminder");
            }
        });
    }

    private void cancelAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        if(alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
    }

    private void setTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(),"notification_id");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour() > 12) {
                    btnSetTime.setText(String.format("%02d",(picker.getHour()-12)) + " : " + String.format("%02d", picker.getMinute()) + "PM");
                }
                else {
                    //btnSetTime.setText(picker.getHour()+ " : " + picker.getMinute() + "AM");
                    btnSetTime.setText(String.format("%02d",picker.getHour()) + " : " + String.format("%02d", picker.getMinute()) + "AM");
                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
            }
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderTaskNotificationChannel";
            String description = "Channel for Alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notification_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}