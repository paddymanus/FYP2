package com.example.paddy.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;

@Database(entities = {Measurement.class}, version = 1)
public abstract class MeasurementDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "measurement_db";

    private static MeasurementDatabase instance;

    static MeasurementDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MeasurementDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract MeasurementDao getMeasurementsDao();
}
