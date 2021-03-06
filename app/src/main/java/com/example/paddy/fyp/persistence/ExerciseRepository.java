package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteExerciseAsyncTask;
import com.example.paddy.fyp.async.InsertExerciseAsyncTask;
import com.example.paddy.fyp.models.Exercise;

import java.util.List;

public class ExerciseRepository {

    private ExerciseDatabase mExerciseDatabase;

    public ExerciseRepository(Context context) {
        mExerciseDatabase = ExerciseDatabase.getInstance(context);
    }

    public void insertExerciseTask(Exercise exercise){
        new InsertExerciseAsyncTask(mExerciseDatabase.getExerciseDao()).execute(exercise);
    }

    public void updateExerciseTask(Exercise exercise){

    }

    public LiveData<List<Exercise>> retrieveExerciseTask(){

        return mExerciseDatabase.getExerciseDao().getExercises();
    }

    public LiveData<List<Exercise>> retrieveExerciseTaskByBodypart(String bodypart){

        return mExerciseDatabase.getExerciseDao().getExerciseStat(bodypart);
    }

    public LiveData<List<Exercise>> retrieveExerciseStat(String category){
        return mExerciseDatabase.getExerciseDao().getExerciseStat(category);
    }

    public void deleteExercise(Exercise exercise){
        new DeleteExerciseAsyncTask(mExerciseDatabase.getExerciseDao()).execute(exercise);
    }
}
