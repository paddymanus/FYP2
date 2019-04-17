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

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.StatsHomeRecyclerAdapter;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class StatsBodyweightActivity extends AppCompatActivity implements StatsHomeRecyclerAdapter.OnStatsHomeListener, View.OnClickListener {
    private static final String TAG = "StatsActivity";

    // Ui components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;

    // vars
    private ArrayList<StatsHome> mStatsHome = new ArrayList<>();
    private StatsHomeRecyclerAdapter mStatsHomeRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_bodyweight);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.rvStatsHome);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        initRecyclerView();
        insertStats();
        setListeners();

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStatsHomeRecyclerAdapter = new StatsHomeRecyclerAdapter(mStatsHome, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mStatsHomeRecyclerAdapter);
    }

    private void insertStats(){
        StatsHome statsHome = new StatsHome("Bodyweight list");
        StatsHome statsHome1 = new StatsHome("Statistics");
        mStatsHome.add(statsHome);
        mStatsHome.add(statsHome1);
        mStatsHomeRecyclerAdapter.notifyDataSetChanged();
    }


    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }

    @Override
    public void onStatClicked(int position) {
        if(position == 0){
            Log.d(TAG, "onStatClicked: this is list");
            Intent intent = new Intent(this, StatsAddBodyweightActivity.class);
            startActivity(intent);
        }
        else if(position == 1){
            Log.d(TAG, "onStatClicked: this is stats");
            Intent intent = new Intent(this, StatsBodyweightStatActivity.class);
            startActivity(intent);
        }
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
