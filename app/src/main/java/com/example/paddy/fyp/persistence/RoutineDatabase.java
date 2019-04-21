package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.RoutineHome;

@Database(entities = {RoutineHome.class}, version = 1)
public abstract class RoutineDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "routines_db";

    private static RoutineDatabase instance;

    static RoutineDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoutineDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract RoutineDao getRoutineDao();
}
