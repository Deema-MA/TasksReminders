package com.app.alram.DBDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.alram.Modle.Alarms;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert(entity = Alarms.class, onConflict = OnConflictStrategy.REPLACE)
    long addNewAlarm(Alarms alarms);

    @Query("SELECT DISTINCT * FROM alarms")
    List<Alarms> getAllAlarm();


    @Query("SELECT DISTINCT * FROM alarms WHERE date = :date AND time =:time")
    List<Alarms> getAllAlarmTime(String date, String time);

}
