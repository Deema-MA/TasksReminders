package com.app.alram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView tv_name_task , tv_level_details , tv_date_details , tv_time_details ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv_name_task = findViewById(R.id.tv_name_alarm);
        tv_level_details = findViewById(R.id.tv_level_details);
        tv_date_details = findViewById(R.id.tv_date_details);
        tv_time_details = findViewById(R.id.tv_time_details);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            String name_task = extras.getString("name_task");
            String level = extras.getString("level");
            String date = extras.getString("date");
            String time = extras.getString("time");

            tv_name_task.setText("Title Alarm : "+name_task);
            tv_level_details.setText("Level : "+level);
            tv_date_details.setText("Date : "+date);
            tv_time_details.setText("Time : "+time);


            Log.e("Title_Alarm", name_task + "===============================");
        }





    }
}
