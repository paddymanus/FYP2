package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.ExerciseDao;
import com.example.paddy.fyp.persistence.LogItemDao;

public class InsertLogItemAsyncTask extends AsyncTask<LogItem, Void, Void> {

    private LogItemDao mLogItemDao;

    public InsertLogItemAsyncTask(LogItemDao dao) {
        mLogItemDao = dao;
    }

    @Override
    protected Void doInBackground(LogItem... logItems) {
        mLogItemDao.insertLogItems(logItems);
        return null;
    }
}
