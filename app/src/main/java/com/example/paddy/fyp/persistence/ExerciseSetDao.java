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

    @Query("SELECT * FROM ExerciseSet " +
            "INNER JOIN LogItem ON ExerciseSet.workout_id LIKE LogItem.title " +
    "WHERE LogItem.title = :title")
    List<ExerciseSet> getMatchingExerciseSets(String title);

    @Query("SELECT * FROM ExerciseSet WHERE workout_id LIKE :workoutid")
    LiveData<List<ExerciseSet>> getWorkoutId1(String workoutid);

    @Query("SELECT * FROM ExerciseSet WHERE workout_id LIKE :name")
    LiveData<List<ExerciseSet>> getByTitle(String name);

    @Delete
    int delete(ExerciseSet... exerciseSets);

    @Update
    int update(ExerciseSet... exerciseSets);


}
