package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.ExerciseRecyclerAdapter;
import com.example.paddy.fyp.adapters.ExerciseSetRecyclerAdapter;
import com.example.paddy.fyp.adapters.LogItemRecyclerAdapter;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.persistence.ExerciseRepository;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;

import java.util.ArrayList;
import java.util.List;

public class StatsHistoryActivity extends AppCompatActivity implements ExerciseSetRecyclerAdapter.onExerciseSetListener, View.OnClickListener {

    private static final String TAG = "StatsHistoryActivity";

    // UI components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private TextView mViewTitle;

    // vars
    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
    private ExerciseSetRepository mExerciseSetRepository;
    private Exercise mInitialExercise;
    private boolean mIsNewLogItem;
    private StatsHome mIntialStatsHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_history);
        Log.d(TAG, "onCreate: started");

        mRecyclerView = findViewById(R.id.view_exercise_recycler_list);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);
        mViewTitle = findViewById(R.id.stats_exercise_title);

        mExerciseSetRepository = new ExerciseSetRepository(this);

        if(getIntent().hasExtra("selected_stat")){
            mIntialStatsHome = getIntent().getParcelableExtra("selected_stat");
            Log.d(TAG, "onCreateOne: " + mIntialStatsHome.toString());
        }


        if(getIncomingIntent()){

        }
        else {
            setExerciseProperties();
        }

        getIncomingIntent1();

        initRecyclerView();
        retrieveExerciseSets();
        setListeners();

    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_exercise")){
            mInitialExercise = getIntent().getParcelableExtra("selected_exercise");
            Log.d(TAG, "getIncomingIntent: " + mInitialExercise.toString());

            mIsNewLogItem = false;
            return false;
        }
        mIsNewLogItem = true;
        return true;
    }

    private boolean getIncomingIntent1(){
        if(getIntent().hasExtra("selected_stat")){
            mIntialStatsHome = getIntent().getParcelableExtra("selected_stat");
            Log.d(TAG, "getIncomingIntent: " + mIntialStatsHome.toString());

            mIsNewLogItem = false;
            return false;
        }
        mIsNewLogItem = true;
        return true;
    }

    private void setExerciseProperties(){
        mViewTitle.setText(mInitialExercise.getName());
    }

    private void retrieveExerciseSets(){
        mExerciseSetRepository.retrieveSetByTitle(mInitialExercise.getName()).observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exerciseSets) {
                if(mSets.size() > 0){
                    mSets.clear();
                }
                if(exerciseSets != null){
                    mSets.addAll(exerciseSets);
                }
                mExerciseSetRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseSetRecyclerAdapter = new ExerciseSetRecyclerAdapter(mSets, this);
        mRecyclerView.setAdapter(mExerciseSetRecyclerAdapter);
    }

    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsOptionsActivity.class);
                intent.putExtra("selected_exercise", mInitialExercise);
                intent.putExtra("selected_stat", mIntialStatsHome);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onExerciseSetClick(int position) {

    }
}
