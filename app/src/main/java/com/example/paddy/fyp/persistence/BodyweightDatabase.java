package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.LogItem;

@Database(entities = {Bodyweight.class}, version = 1)
public abstract class BodyweightDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "bodyweight_db";

    private static BodyweightDatabase instance;

    static BodyweightDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    BodyweightDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract BodyweightDao getBodyweightsDao();
}
