package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.Exercise;

@Database(entities = {Exercise.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "exercises_db";

    private static ExerciseDatabase instance;

    static ExerciseDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ExerciseDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract ExerciseDao getExerciseDao();
}
