package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.ExerciseSet;

@Database(entities = {ExerciseSet.class}, version = 1)
public abstract class ExerciseSetDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "exercise_sets_db";

    private static ExerciseSetDatabase instance;

    static ExerciseSetDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ExerciseSetDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ExerciseSetDao getExerciseSetDao();
}
