package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.InsertLogItemAsyncTask;
import com.example.paddy.fyp.models.LogItem;

import java.util.List;

public class LogItemRepository {

    private LogItemDatabase mLogItemDatabase;

    public LogItemRepository(Context context) {
        mLogItemDatabase = LogItemDatabase.getInstance(context);
    }

    public void insertLogItemTask(LogItem logItem){
        new InsertLogItemAsyncTask(mLogItemDatabase.getLogItemsDao()).execute(logItem);
    }

    public void updateLogItemTask(LogItem logItem){

    }

    public LiveData<List<LogItem>> retrieveLogItemTask(){

        return mLogItemDatabase.getLogItemsDao().getLogItems();
    }

    public List<LogItem> findByWorkoutIdTask(int id){

        return mLogItemDatabase.getLogItemsDao().findByWorkoutId(id);
    }

    public void deleteExercise(LogItem logItem){

    }
}
