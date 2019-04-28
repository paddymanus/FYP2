package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    long[] insertExercises(Exercise... exercises);

    @Query("SELECT * FROM exercises ORDER BY name")
    LiveData<List<Exercise>> getExercises();

    @Query("SELECT * FROM exercises WHERE category LIKE :category ORDER BY name")
    LiveData<List<Exercise>> getExerciseStat(String category);


    @Delete
    int delete(Exercise... exercises);

    @Update
    int update(Exercise... exercises);

}
