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

    @Query("SELECT * FROM ExerciseSet")
    LiveData<List<ExerciseSet>> getExerciseSets();

    @Query("SELECT * FROM ExerciseSet WHERE workout_id LIKE :workoutid")
    LiveData<List<ExerciseSet>> getWorkoutId1(int workoutid);

    @Query("SELECT * FROM ExerciseSet WHERE exerciseName LIKE :name")
    LiveData<List<ExerciseSet>> getByTitle(String name);

    @Query("SELECT * FROM ExerciseSet WHERE exerciseName LIKE :name ORDER BY _id  DESC LIMIT 3")
    LiveData<List<ExerciseSet>> getByTitleExercise(String name);


    @Query("SELECT * FROM ExerciseSet WHERE category LIKE :category")
    LiveData<List<ExerciseSet>> getExerciseSetStat(String category);

    @Delete
    int delete(ExerciseSet... exerciseSets);

    @Update
    int update(ExerciseSet... exerciseSets);


}
