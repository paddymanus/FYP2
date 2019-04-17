package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.LogItem;

import java.util.List;

@Dao
public interface BodyweightDao {

        @Insert
        long[] insertBodyweight(Bodyweight... bodyweights);

        @Query("SELECT * FROM bodyweight")
        LiveData<List<Bodyweight>> getBodyweight();

//        @Query("SELECT * FROM logItem WHERE id LIKE :id")
//        List<LogItem> findByWorkoutId(int id);

        @Delete
        int delete(Bodyweight... bodyweights);

        @Update
        int update(Bodyweight... bodyweights);


}
