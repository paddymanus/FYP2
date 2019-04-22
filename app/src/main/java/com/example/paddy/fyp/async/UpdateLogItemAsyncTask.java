package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.LogItemDao;

public class UpdateLogItemAsyncTask extends AsyncTask<LogItem, Void, Void> {

    private LogItemDao mLogItemDao;

    public UpdateLogItemAsyncTask(LogItemDao dao) {
        mLogItemDao = dao;
    }

    @Override
    protected Void doInBackground(LogItem... logItems) {
        mLogItemDao.update(logItems);
        return null;
    }
}
