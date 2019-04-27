package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteSetAsyncTask;
import com.example.paddy.fyp.async.InsertSetAsyncTask;
import com.example.paddy.fyp.async.UpdateSetAsyncTask;
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
        new UpdateSetAsyncTask(mExerciseSetDatabase.getExerciseSetDao()).execute(exerciseSet);
    }

    public LiveData<List<ExerciseSet>> retrieveSetTask(){

        return mExerciseSetDatabase.getExerciseSetDao().getExerciseSets();
    }

    public LiveData<List<ExerciseSet>> retrieveSet1(int id){
        return mExerciseSetDatabase.getExerciseSetDao().getWorkoutId1(id);
    }

    public LiveData<List<ExerciseSet>> retrieveSetByTitle(String name){
        return mExerciseSetDatabase.getExerciseSetDao().getByTitle(name);
    }

    public LiveData<List<ExerciseSet>> retrieveExerciseSetStat(String category){
        return mExerciseSetDatabase.getExerciseSetDao().getExerciseSetStat(category);
    }



    public void deleteSet(ExerciseSet exerciseSet){
        new DeleteSetAsyncTask(mExerciseSetDatabase.getExerciseSetDao()).execute(exerciseSet);
    }
}
