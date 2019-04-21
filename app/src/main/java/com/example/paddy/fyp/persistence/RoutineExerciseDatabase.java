package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.RoutineExercise;

@Database(entities = {RoutineExercise.class}, version = 1)
public abstract class RoutineExerciseDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "routine_exercises_db";

    private static RoutineExerciseDatabase instance;

    static RoutineExerciseDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoutineExerciseDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract RoutineExerciseDao getRoutineExerciseDao();
}
