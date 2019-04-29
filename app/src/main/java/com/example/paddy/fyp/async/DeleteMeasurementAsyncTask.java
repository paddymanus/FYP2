package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.persistence.ExerciseDao;
import com.example.paddy.fyp.persistence.MeasurementDao;

public class DeleteMeasurementAsyncTask extends AsyncTask<Measurement, Void, Void> {

    private MeasurementDao mMeasurementDao;

    public DeleteMeasurementAsyncTask(MeasurementDao dao) {
        mMeasurementDao = dao;
    }

    @Override
    protected Void doInBackground(Measurement... measurements) {
        mMeasurementDao.delete(measurements);
        return null;
    }
}
