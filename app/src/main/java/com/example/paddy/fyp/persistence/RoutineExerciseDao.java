package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.RoutineExercise;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

@Dao
public interface RoutineExerciseDao {

    @Insert
    long[] insertRoutineExercises(RoutineExercise... routineExercises);

    @Query("SELECT * FROM routineexercise ORDER BY name")
    LiveData<List<RoutineExercise>> getExercises();

    @Query("SELECT * FROM routineexercise WHERE log_id LIKE :logid")
    LiveData<List<RoutineExercise>> getMatchingExercise(int logid);

    @Query("SELECT * FROM routineexercise WHERE category LIKE :category")
    LiveData<List<RoutineExercise>> getExerciseStat(String category);


    @Delete
    int delete(RoutineExercise... routineExercises);

    @Update
    int update(RoutineExercise... routineExercises);

}
