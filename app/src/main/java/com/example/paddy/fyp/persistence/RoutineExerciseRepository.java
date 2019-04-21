package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteExerciseAsyncTask;
import com.example.paddy.fyp.async.InsertExerciseAsyncTask;
import com.example.paddy.fyp.async.InsertRoutineAsyncTask;
import com.example.paddy.fyp.async.InsertRoutineExerciseAsyncTask;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.RoutineExercise;

import java.util.List;

public class RoutineExerciseRepository {

    private RoutineExerciseDatabase mRoutineExerciseDatabase;

    public RoutineExerciseRepository(Context context) {
        mRoutineExerciseDatabase = RoutineExerciseDatabase.getInstance(context);
    }

    public void insertExerciseTask(RoutineExercise routineExercise){
        new InsertRoutineExerciseAsyncTask(mRoutineExerciseDatabase.getRoutineExerciseDao()).execute(routineExercise);
    }

    public void updateExerciseTask(Exercise exercise){

    }

    public LiveData<List<RoutineExercise>> retrieveExerciseTask(){

        return mRoutineExerciseDatabase.getRoutineExerciseDao().getExercises();
    }

    public LiveData<List<RoutineExercise>> retrieveMatchingExerciseTask(int id){
        return  mRoutineExerciseDatabase.getRoutineExerciseDao().getMatchingExercise(id);
    }
//
//    public LiveData<List<Exercise>> retrieveExerciseStat(String category){
//        return mExerciseDatabase.getExerciseDao().getExerciseStat(category);
//    }
//
//    public void deleteExercise(Exercise exercise){
//        new DeleteExerciseAsyncTask(mExerciseDatabase.getExerciseDao()).execute(exercise);
//    }
}
