package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.LogItem;

@Database(entities = {LogItem.class}, version = 1)
public abstract class LogItemDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "log_items_db";

    private static LogItemDatabase instance;

    static LogItemDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    LogItemDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract LogItemDao getLogItemsDao();
}
