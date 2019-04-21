package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.RoutineExercise;
import com.example.paddy.fyp.persistence.ExerciseDao;
import com.example.paddy.fyp.persistence.RoutineExerciseDao;

public class InsertRoutineExerciseAsyncTask extends AsyncTask<RoutineExercise, Void, Void> {

    private RoutineExerciseDao mRoutineExerciseDao;

    public InsertRoutineExerciseAsyncTask(RoutineExerciseDao dao) {
        mRoutineExerciseDao = dao;
    }

    @Override
    protected Void doInBackground(RoutineExercise... routineExercises) {
        mRoutineExerciseDao.insertRoutineExercises(routineExercises);
        return null;
    }


}
