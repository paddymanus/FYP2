package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.InsertSetAsyncTask;
import com.example.paddy.fyp.models.ExerciseSet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSetRepository {

    private ExerciseSetDatabase mExerciseSetDatabase;

    public ExerciseSetRepository(Context context) {
        mExerciseSetDatabase = ExerciseSetDatabase.getInstance(context);
    }

    public void insertSetTask(ExerciseSet exerciseSet){
        new InsertSetAsyncTask(mExerciseSetDatabase.getExerciseSetDao()).execute(exerciseSet);
    }

    public void updateSet(ExerciseSet exerciseSet){

    }

    public LiveData<List<ExerciseSet>> retrieveSetTask(){

        return mExerciseSetDatabase.getExerciseSetDao().getExerciseSets();
    }

    public LiveData<List<ExerciseSet>> retrieveMatchingSetsTask(){
        return mExerciseSetDatabase.getExerciseSetDao().getMatchingExerciseSets();
    }


    public void deleteSet(ExerciseSet exerciseSet){

    }
}
