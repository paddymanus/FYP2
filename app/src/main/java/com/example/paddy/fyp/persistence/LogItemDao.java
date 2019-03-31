package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.LogItem;

import java.util.List;

@Dao
public interface LogItemDao {

        @Insert
        long[] insertLogItems(LogItem... logItems);

        @Query("SELECT * FROM logItem")
        LiveData<List<LogItem>> getLogItems();

//        @Query("SELECT * FROM logItem WHERE id LIKE :id")
//        List<LogItem> findByWorkoutId(int id);

        @Delete
        int delete(LogItem... logItems);

        @Update
        int update(LogItem... logItems);


}
