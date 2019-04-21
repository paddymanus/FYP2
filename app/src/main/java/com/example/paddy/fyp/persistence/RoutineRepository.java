package com.example.paddy.fyp.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.paddy.fyp.async.DeleteSetAsyncTask;
import com.example.paddy.fyp.async.InsertRoutineAsyncTask;
import com.example.paddy.fyp.async.InsertSetAsyncTask;
import com.example.paddy.fyp.async.UpdateSetAsyncTask;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.RoutineHome;

import java.util.List;

public class RoutineRepository {

    private RoutineDatabase mRoutineDatabase;

    public RoutineRepository(Context context) {
        mRoutineDatabase = RoutineDatabase.getInstance(context);
    }

    public void insertRoutineTask(RoutineHome routineHome){
        new InsertRoutineAsyncTask(mRoutineDatabase.getRoutineDao()).execute(routineHome);
    }

    public void updateRoutine(RoutineHome routineHome){

    }

    public LiveData<List<RoutineHome>> retrieveRoutineTask(){

        return mRoutineDatabase.getRoutineDao().getRoutines();
    }

//    public LiveData<List<ExerciseSet>> retrieveSet1(int id){
//        return mExerciseSetDatabase.getExerciseSetDao().getWorkoutId1(id);
//    }
//
//    public LiveData<List<ExerciseSet>> retrieveSetByTitle(String name){
//        return mExerciseSetDatabase.getExerciseSetDao().getByTitle(name);
//    }



    public void deleteSet(RoutineHome routineHome){

    }
}
