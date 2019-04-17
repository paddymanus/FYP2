package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.persistence.BodyweightDao;
import com.example.paddy.fyp.persistence.MeasurementDao;

public class InsertMeasurementAsyncTask extends AsyncTask<Measurement, Void, Void> {

    private MeasurementDao mMeasurementDao;

    public InsertMeasurementAsyncTask(MeasurementDao dao) {
        mMeasurementDao = dao;
    }

    @Override
    protected Void doInBackground(Measurement... measurements) {
        mMeasurementDao.insertMeasurement(measurements);
        return null;
    }
}
