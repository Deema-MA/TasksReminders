package com.app.alram.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.app.alram.DBDao.AlarmDao;
import com.app.alram.Modle.Alarms;

@Database(entities = {Alarms.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "alarm_db";
    private static DataBase instance;

    public static DataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBase.class,
                    DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }


    public abstract AlarmDao getAlarmDao();


}
