package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.InsertSetAsyncTask;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;

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

    public List<ExerciseSet> retrieveMatchingSetsTask(String title){
        return mExerciseSetDatabase.getExerciseSetDao().getMatchingExerciseSets(title);
    }

    public LiveData<List<ExerciseSet>> retrieveSet1(String title){
        return mExerciseSetDatabase.getExerciseSetDao().getWorkoutId1(title);
    }

    public LiveData<List<ExerciseSet>> retrieveSetByTitle(){
        return mExerciseSetDatabase.getExerciseSetDao().getByTitle("");
    }



    public void deleteSet(ExerciseSet exerciseSet){

    }
}
