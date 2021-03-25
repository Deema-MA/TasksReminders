package com.app.alram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.NotificationManagerCompat;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.alram.Adapter.AlarmAdapter;
import com.app.alram.DB.DataBase;
import com.app.alram.Modle.Alarms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.app.alram.App.CHANNEL_1_ID;
import static com.app.alram.App.CHANNEL_2_ID;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_add;
    private RecyclerView rv_task;
    private EditText ed_title_alarm;
    private TextView tv_date_home, tv_time;
    private RadioButton rb_high, rb_low;
    private ImageButton btn_date , btn_time ;

    private List<Alarms> alarms;
    private List<Alarms> alarmsList;
    private DataBase database;
    private AlarmAdapter alarmAdapter;
    private String Level = "High";
    int hour, minute;

    private EditText editTextTitle;
    private EditText editTextMessage;
    private NotificationManagerCompat notificationManager;

    Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        setUpAdapter();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("H:m", Locale.getDefault()).format(new Date());

        editTextTitle = findViewById(R.id.ed_title_alarm);
        editTextMessage = findViewById(R.id.edit_text_message);

        alarmsList = database.getAlarmDao().getAllAlarmTime(currentDate, currentTime);
        notificationManager = NotificationManagerCompat.from(this);

    }


    private void findView() {

        database = DataBase.getInstance(this);

        alarms = new ArrayList<>();
        alarmsList = new ArrayList<>();


        btn_add = findViewById(R.id.btn_add);
        ed_title_alarm = findViewById(R.id.ed_title_alarm);
        tv_date_home = findViewById(R.id.tv_date_home);
        tv_time = findViewById(R.id.tv_time);
        rb_high = findViewById(R.id.rb_high);
        rb_low = findViewById(R.id.rb_low);
        rv_task = findViewById(R.id.rv_task);
        btn_date = findViewById(R.id.btn_date);
        btn_time = findViewById(R.id.btn_time);


        btn_add.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (validation()) {

                    String title_alarm = ed_title_alarm.getText().toString();
                    String date = tv_date_home.getText().toString();
                    String time = tv_time.getText().toString();


                    if (rb_high.isChecked()) {
                        Level = "High";


                    } else if (rb_low.isChecked()) {
                        Level = "Low";

                    }

                    long a = database.getAlarmDao().addNewAlarm(new Alarms(title_alarm, Level, date, time));

                    if (a > 0) {

                        ed_title_alarm.setText("");
                        rb_high.setChecked(true);
                        tv_date_home.setText("Choose A Date");
                        tv_time.setText("Choose The Time");
                        Toast.makeText(this, "The Alarm Was Added Successfully", Toast.LENGTH_SHORT).show();
                        setUpAdapter();
                    }

                }
                break;


            case R.id.btn_date:
                showCalendarDialog();
                break;


            case R.id.btn_time:
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, R.style.datepicker, (timePicker, i, i1) -> {
                    tv_time.setText(i + ":" + i1);
                }, hour, minute, true);
                timePickerDialog.show();
                break;
        }
    }


    private void setUpAdapter() {

        alarms = database.getAlarmDao().getAllAlarm();
        rv_task.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        alarmAdapter = new AlarmAdapter(MainActivity.this, alarms);
        rv_task.setAdapter(alarmAdapter);
        alarmAdapter.notifyDataSetChanged();


    }

    private void showCalendarDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(MainActivity.this), R.style.datepicker,
                (view, year, monthOfYear, dayOfMonth) -> {

                    c.setTimeInMillis(0);
                    c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                   Date chosenDate  = c.getTime();

                    tv_date_home.setText(formatToDisplayMessageFormat_2(chosenDate));


                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("");
        datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis() + 1 * 24 * 60 * 60 * 0000));
        datePickerDialog.show();
    }


    public static String formatToDisplayMessageFormat_2(Date date) {
        if (date == null) {
            return null;
        }
        String dateFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }



    private boolean validation() {

        if (!ValidationEmptyInput(ed_title_alarm)) {
            Toast.makeText(this, "Title Alarm is Empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (tv_date_home.getText().toString().equals("Choose A Date")) {
            Toast.makeText(this, "Please Choose A Date", Toast.LENGTH_LONG).show();

            return false;

        } else if (tv_time.getText().toString().equals("Choose The Time")) {
            Toast.makeText(this, "Please Choose The Time", Toast.LENGTH_LONG).show();

            return false;

        } else {
            return true;

        }
    }

    public static boolean ValidationEmptyInput(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return false;
        }
        return true;

    }
    public void sendOnChannel1(View v) {

        Intent intent = new Intent(this, DetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {

        Intent intent = new Intent(this, DetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        notificationManager.notify(2, notification);

    }






}
