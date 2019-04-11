package com.example.paddy.fyp.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.paddy.fyp.ExerciseActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.StatsHomeRecyclerAdapter;
import com.example.paddy.fyp.adapters.StatsOptionsRecyclerAdapter;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.models.StatsOptions;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class StatsOptionsActivity extends AppCompatActivity implements StatsOptionsRecyclerAdapter.OnStatsOptionsListener, View.OnClickListener {
    private static final String TAG = "StatsActivity";

    // Ui components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;

    // vars
    private ArrayList<StatsOptions> mStatsOptions = new ArrayList<>();
    private StatsOptionsRecyclerAdapter mStatsOptionsRecyclerAdapter;
    private boolean mIsNewLogItem;
    private StatsHome mIntialStatsHome;
    private Exercise mInitialExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_options_exercise);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.layout_stats_options);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        initRecyclerView();
        getIncomingIntent();

        if(getIntent().hasExtra("selected_stat")){
            mIntialStatsHome = getIntent().getParcelableExtra("selected_stat");
            Log.d(TAG, "onCreate: " + mIntialStatsHome.toString());
        }

        insertStats();
        setListeners();

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStatsOptionsRecyclerAdapter = new StatsOptionsRecyclerAdapter(mStatsOptions, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mStatsOptionsRecyclerAdapter);
    }

    private void insertStats(){
        StatsOptions statsOptions = new StatsOptions("History");
        StatsOptions statsOptions1 = new StatsOptions("Estimated 1 rep max");
        StatsOptions statsOptions2 = new StatsOptions("Total volume");
        mStatsOptions.add(statsOptions);
        mStatsOptions.add(statsOptions1);
        mStatsOptions.add(statsOptions2);
        mStatsOptionsRecyclerAdapter.notifyDataSetChanged();
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

    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }

    @Override
    public void onStatClicked(int position) {
        if(position == 0){
            Log.d(TAG, "onStatClicked: this is the history");
            Intent intent = new Intent(this, StatsHistoryActivity.class);
            intent.putExtra("selected_exercise", mInitialExercise);
            intent.putExtra("selected_stat", mIntialStatsHome);
            startActivity(intent);
        }
        else if(position == 1){
            Log.d(TAG, "onStatClicked: this is the est. 1RM");
        }
        else if(position == 2){
            Log.d(TAG, "onStatClicked: this is the total volume");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsExerciseActivity.class);
                intent.putExtra("selected_stat1", mIntialStatsHome);
                startActivity(intent);
                break;
            }
        }
    }
}
