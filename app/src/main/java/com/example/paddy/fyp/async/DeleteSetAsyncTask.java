package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.persistence.ExerciseSetDao;

public class DeleteSetAsyncTask extends AsyncTask<ExerciseSet, Void, Void> {

    private ExerciseSetDao mExerciseSetDao;

    public DeleteSetAsyncTask(ExerciseSetDao dao) {
        mExerciseSetDao = dao;
    }

    @Override
    protected Void doInBackground(ExerciseSet... exerciseSets) {
        mExerciseSetDao.delete(exerciseSets);
        return null;
    }
}
