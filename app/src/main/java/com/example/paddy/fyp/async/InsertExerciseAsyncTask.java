package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.persistence.ExerciseDao;

public class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {

    private ExerciseDao mExerciseDao;

    public InsertExerciseAsyncTask(ExerciseDao dao) {
        mExerciseDao = dao;
    }

    @Override
    protected Void doInBackground(Exercise... exercises) {
        mExerciseDao.insertExercises(exercises);
        return null;
    }
}
