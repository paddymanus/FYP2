package com.example.paddy.fyp.routines;

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

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.RoutinesHomeRecyclerAdapter;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class RoutinesActivity extends AppCompatActivity implements RoutinesHomeRecyclerAdapter.OnRoutineHomeListener, View.OnClickListener {
    private static final String TAG = "RoutinesActivity";

    // Ui components
    private RecyclerView mRecyclerView;

    // vars
    private Context mContext = RoutinesActivity.this;
    private ArrayList<RoutineHome> mRoutineHome = new ArrayList<>();
    private RoutinesHomeRecyclerAdapter mRoutinesHomeRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.rvRoutinesHome);

        setupBottomNavigationView();
        initRecyclerView();
        insertRoutines();
    }

    private void insertRoutines(){
        RoutineHome routineHome = new RoutineHome("Full body");
        RoutineHome routineHome1 = new RoutineHome("Push");
        RoutineHome routineHome2 = new RoutineHome("Pull");
        RoutineHome routineHome3 = new RoutineHome("Legs");
        mRoutineHome.add(routineHome);
        mRoutineHome.add(routineHome1);
        mRoutineHome.add(routineHome2);
        mRoutineHome.add(routineHome3);
        mRoutinesHomeRecyclerAdapter.notifyDataSetChanged();
    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRoutinesHomeRecyclerAdapter = new RoutinesHomeRecyclerAdapter(mRoutineHome, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mRoutinesHomeRecyclerAdapter);
    }


    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigationView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
    }

    @Override
    public void onRoutineClicked(int position) {
        Intent intent = new Intent(this, RoutineLogActivity.class);
        intent.putExtra("selected_routine_item", mRoutineHome.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RoutineLogActivity.class);
        intent.putExtra("selected_routine", mRoutineHome.size() + 1);
        startActivity(intent);
    }
}
