package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteBodyweightAsyncTask;
import com.example.paddy.fyp.async.InsertBodyweightAsyncTask;
import com.example.paddy.fyp.async.InsertLogItemAsyncTask;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.LogItem;

import java.util.List;

public class BodyweightRepository {

    private BodyweightDatabase mBodyweightDatabase;

    public BodyweightRepository(Context context) {
        mBodyweightDatabase = BodyweightDatabase.getInstance(context);
    }

    public void insertBodyweightTask(Bodyweight bodyweight){
        new InsertBodyweightAsyncTask(mBodyweightDatabase.getBodyweightsDao()).execute(bodyweight);
    }

    public void updateBodyweightTask(Bodyweight bodyweight){

    }

    public LiveData<List<Bodyweight>> retrieveBodyweightTask(){

        return mBodyweightDatabase.getBodyweightsDao().getBodyweight();
    }

    public LiveData<List<Bodyweight>> retrieveBodyweightTaskStat(){

        return mBodyweightDatabase.getBodyweightsDao().getBodyweightStat();
    }


    public void deleteBodyweight(Bodyweight bodyweight){
        new DeleteBodyweightAsyncTask(mBodyweightDatabase.getBodyweightsDao()).execute(bodyweight);
    }
}
