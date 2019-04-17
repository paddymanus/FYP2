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

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.StatsHomeRecyclerAdapter;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity implements StatsHomeRecyclerAdapter.OnStatsHomeListener {
    private static final String TAG = "StatsActivity";

    // Ui components
    private RecyclerView mRecyclerView;

    // vars
    private Context mContext = StatsActivity.this;
    private ArrayList<StatsHome> mStatsHome = new ArrayList<>();
    private StatsHomeRecyclerAdapter mStatsHomeRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.rvStatsHome);

        setupBottomNavigationView();
        initRecyclerView();
        insertStats();

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
        StatsHome statsHome = new StatsHome("Muscles targeted");
        StatsHome statsHome1 = new StatsHome("Body measurements");
        StatsHome statsHome2 = new StatsHome("Bodyweight");
        StatsHome statsHome3 = new StatsHome("Abs");
        StatsHome statsHome4 = new StatsHome("Back");
        StatsHome statsHome5 = new StatsHome("Biceps");
        StatsHome statsHome6 = new StatsHome("Chest");
        StatsHome statsHome7 = new StatsHome("Legs");
        StatsHome statsHome8 = new StatsHome("Shoulders");
        StatsHome statsHome9 = new StatsHome("Triceps");
        mStatsHome.add(statsHome);
        mStatsHome.add(statsHome1);
        mStatsHome.add(statsHome2);
        mStatsHome.add(statsHome3);
        mStatsHome.add(statsHome4);
        mStatsHome.add(statsHome5);
        mStatsHome.add(statsHome6);
        mStatsHome.add(statsHome7);
        mStatsHome.add(statsHome8);
        mStatsHome.add(statsHome9);
        mStatsHomeRecyclerAdapter.notifyDataSetChanged();
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigationView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
    }

    @Override
    public void onStatClicked(int position) {
        if(position == 0){
            Log.d(TAG, "onStatClicked: this is zero mate");
            Intent intent = new Intent(this, StatsMusclesActivity.class);
            startActivity(intent);
        }
        else if(position == 1){
            Log.d(TAG, "onStatClicked: this is one now lad");
            Intent intent = new Intent(this, StatsMeasurementsActivity.class);
            startActivity(intent);
        }
        else if(position == 2){
            Log.d(TAG, "onStatClicked: this is big fat 2");
            Intent intent = new Intent(this, StatsBodyweightActivity.class);
            startActivity(intent);
        }
        else if(position >= 3){
            Log.d(TAG, "onStatClicked: these are the categories");
            Intent intent = new Intent(this, StatsExerciseActivity.class);
            intent.putExtra("selected_stat", mStatsHome.get(position));
            startActivity(intent);
        }
    }
}
