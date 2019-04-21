package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.persistence.ExerciseSetDao;
import com.example.paddy.fyp.persistence.RoutineDao;

public class InsertRoutineAsyncTask extends AsyncTask<RoutineHome, Void, Void> {

    private RoutineDao mRoutineDao;

    public InsertRoutineAsyncTask(RoutineDao dao) {
        mRoutineDao = dao;
    }


    @Override
    protected Void doInBackground(RoutineHome... routineHomes) {
        mRoutineDao.insertRoutines(routineHomes);
        return null;
    }
}
