package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.persistence.ExerciseSetDao;

public class UpdateSetAsyncTask extends AsyncTask<ExerciseSet, Void, Void> {

    private ExerciseSetDao mExerciseSetDao;

    public UpdateSetAsyncTask(ExerciseSetDao dao) {
        mExerciseSetDao = dao;
    }

    @Override
    protected Void doInBackground(ExerciseSet... exerciseSets) {
        mExerciseSetDao.update(exerciseSets);
        return null;
    }
}
