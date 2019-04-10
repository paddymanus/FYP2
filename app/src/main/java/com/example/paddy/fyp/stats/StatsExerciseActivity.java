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

import com.example.paddy.fyp.ExerciseActivity;
import com.example.paddy.fyp.NewExerciseActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.ExerciseRecyclerAdapter;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.persistence.ExerciseRepository;

import java.util.ArrayList;
import java.util.List;

public class StatsExerciseActivity extends AppCompatActivity implements ExerciseRecyclerAdapter.OnExerciseListener, View.OnClickListener {

    private static final String TAG = "StatsExerciseActivity";

    // UI components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private TextView mViewTitle;

    // vars
    private ArrayList<Exercise> mExercise = new ArrayList<>();
    private ExerciseRecyclerAdapter mExerciseRecyclerAdapter;
    private ExerciseRepository mExerciseRepository;
    private boolean mIsNewLogItem;
    private StatsHome mIntialStatsHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_exercise);
        Log.d(TAG, "onCreate: started");

        mRecyclerView = findViewById(R.id.view_exercise_recycler_list);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);
        mViewTitle = findViewById(R.id.stats_exercise_title);

        mExerciseRepository = new ExerciseRepository(this);

        if(getIntent().hasExtra("selected_stat")){
            StatsHome statsHome = getIntent().getParcelableExtra("selected_stat");
            Log.d(TAG, "onCreateOne: " + statsHome.toString());
        }

        if(getIncomingIntent()){

        }
        else {
            setExerciseProperties();
        }

        initRecyclerView();
        retrieveExerciseStats();
        setListeners();

    }

    private boolean getIncomingIntent(){
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
        mViewTitle.setText(mIntialStatsHome.getTitle());
    }


    private void retrieveExercises(){
        mExerciseRepository.retrieveExerciseTask().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(mExercise.size() > 0){
                    mExercise.clear();
                }
                if(exercises != null){
                    mExercise.addAll(exercises);
                }
                mExerciseRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void retrieveExerciseStats(){
        mExerciseRepository.retrieveExerciseStat(mIntialStatsHome.getTitle()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(mExercise.size() > 0){
                    mExercise.clear();
                }
                if(exercises != null){
                    mExercise.addAll(exercises);
                }
                mExerciseRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseRecyclerAdapter = new ExerciseRecyclerAdapter(mExercise, this);
        mRecyclerView.setAdapter(mExerciseRecyclerAdapter);
    }

    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }


    @Override
    public void onExerciseClick(int position) {

        Intent intent = new Intent(this, StatsOptionsActivity.class);
        intent.putExtra("selected_stat", mIntialStatsHome);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
