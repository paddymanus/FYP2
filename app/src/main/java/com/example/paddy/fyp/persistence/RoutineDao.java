package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.RoutineHome;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert
        long[] insertRoutines(RoutineHome... routineHomes);

    @Query("SELECT * FROM RoutineHome")
    LiveData<List<RoutineHome>> getRoutines();

//    @Query("SELECT * FROM ExerciseSet WHERE workout_id LIKE :workoutid")
//    LiveData<List<ExerciseSet>> getWorkoutId1(int workoutid);
//
//    @Query("SELECT * FROM ExerciseSet WHERE exerciseName LIKE :name")
//    LiveData<List<ExerciseSet>> getByTitle(String name);

    @Delete
    int delete(RoutineHome... routineHomes);

    @Update
    int update(RoutineHome... routineHomes);


}
