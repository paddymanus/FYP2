package com.example.paddy.fyp.async;

import android.os.AsyncTask;

import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.RoutineExercise;
import com.example.paddy.fyp.persistence.ExerciseSetDao;
import com.example.paddy.fyp.persistence.RoutineExerciseDao;

public class DeleteRoutineExerciseAsyncTask extends AsyncTask<RoutineExercise, Void, Void> {

    private RoutineExerciseDao mRoutineExerciseDao;

    public DeleteRoutineExerciseAsyncTask(RoutineExerciseDao dao) {
        mRoutineExerciseDao = dao;
    }

    @Override
    protected Void doInBackground(RoutineExercise... routineExercises) {
        mRoutineExerciseDao.delete(routineExercises);
        return null;
    }
}
