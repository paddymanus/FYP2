package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteMeasurementAsyncTask;
import com.example.paddy.fyp.async.InsertBodyweightAsyncTask;
import com.example.paddy.fyp.async.InsertMeasurementAsyncTask;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;

import java.util.List;

public class MeasurementRepository {

    private MeasurementDatabase mMeasurementDatabase;

    public MeasurementRepository(Context context) {
        mMeasurementDatabase = MeasurementDatabase.getInstance(context);
    }

    public void insertMeasurementTask(Measurement measurement){
        new InsertMeasurementAsyncTask(mMeasurementDatabase.getMeasurementsDao()).execute(measurement);
    }

    public void updateMeasurementTask(Measurement measurement){

    }

    public LiveData<List<Measurement>> retrieveMeasurementTask(){

        return mMeasurementDatabase.getMeasurementsDao().getMeasurement();
    }

    public LiveData<List<Measurement>> retrieveMeasurementByBodypart(String bodypart){
        return mMeasurementDatabase.getMeasurementsDao().getByBodypart(bodypart);
    }


    public void deleteMeasurement(Measurement measurement){
        new DeleteMeasurementAsyncTask(mMeasurementDatabase.getMeasurementsDao()).execute(measurement);
    }
}
