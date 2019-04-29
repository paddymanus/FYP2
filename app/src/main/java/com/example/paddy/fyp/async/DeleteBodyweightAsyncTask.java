package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.persistence.BodyweightDao;
import com.example.paddy.fyp.persistence.MeasurementDao;

public class DeleteBodyweightAsyncTask extends AsyncTask<Bodyweight, Void, Void> {

    private BodyweightDao mBodyweightDao;

    public DeleteBodyweightAsyncTask(BodyweightDao dao) {
        mBodyweightDao = dao;
    }

    @Override
    protected Void doInBackground(Bodyweight... bodyweights) {
        mBodyweightDao.delete(bodyweights);
        return null;
    }
}
