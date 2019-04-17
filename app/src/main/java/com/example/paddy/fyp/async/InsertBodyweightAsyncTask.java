package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.BodyweightDao;
import com.example.paddy.fyp.persistence.LogItemDao;

public class InsertBodyweightAsyncTask extends AsyncTask<Bodyweight, Void, Void> {

    private BodyweightDao mBodyweightDao;

    public InsertBodyweightAsyncTask(BodyweightDao dao) {
        mBodyweightDao = dao;
    }

    @Override
    protected Void doInBackground(Bodyweight... bodyweights) {
        mBodyweightDao.insertBodyweight(bodyweights);
        return null;
    }
}
