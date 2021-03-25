package com.app.alram.Modle;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarms")
public class Alarms {

    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String title_alarm;
    private String level ;
    private String date ;
    private String time ;

    public Alarms() {
    }

    public Alarms(String title_alarm, String level, String date, String time) {
        this.title_alarm = title_alarm;
        this.level = level;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_alarm() {
        return title_alarm;
    }

    public void setTitle_alarm(String title_alarm) {
        this.title_alarm = title_alarm;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
