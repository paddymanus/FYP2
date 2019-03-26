package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.ExerciseSet;

import java.util.List;

@Dao
public interface ExerciseSetDao {

    @Insert
    long[] insertExerciseSets(ExerciseSet... exerciseSets);

    @Query("SELECT * FROM exerciseSets")
    LiveData<List<ExerciseSet>> getExerciseSets();

    @Delete
    int delete(ExerciseSet... exerciseSets);

    @Update
    int update(ExerciseSet... exerciseSets);


}
