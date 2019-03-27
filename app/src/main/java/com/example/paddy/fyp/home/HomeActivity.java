package com.example.paddy.fyp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.paddy.fyp.ExerciseLogListActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.LogItemRecyclerAdapter;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements LogItemRecyclerAdapter.OnLogItemListener,
        View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private Context mContext = HomeActivity.this;

    // UI components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<LogItem> mLogItems = new ArrayList<>();
    private LogItemRecyclerAdapter mLogItemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.rvLogItem);

        findViewById(R.id.fab).setOnClickListener(this);
        Log.d(TAG, "onCreate: starting");

        initRecyclerView();
      // insertFakeItems();
        setupBottomNavigationView();
    }

//    private void insertFakeItems(){
//        for (int i = 0; i < 1000; i++){
//            LogItem logItem = new LogItem();
//            logItem.setTitle("title # " + i);
//            logItem.setContent("content # " + i);
//            logItem.setTimestamp("Jan 2019");
//            mLogItems.add(logItem);
//        }
//        mLogItemRecyclerAdapter.notifyDataSetChanged();
//    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLogItemRecyclerAdapter = new LogItemRecyclerAdapter(mLogItems, this);
        mRecyclerView.setAdapter(mLogItemRecyclerAdapter);
    }





    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        /**
         * HERE IS WHERE I WILL NEED TO ADD ADDITIONAL FRAGMENTS - CODING WITH MITCH INSTAGRAM CLONE EPISODE 6 @ 8 MINUTES
         */
       // adapter.addFragment(new );
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigationView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
    }

    @Override
    public void onLogItemClick(int position) {
        Log.d(TAG, "onLogItemClick: clicked" + position);

        Intent intent = new Intent(this, ExerciseLogListActivity.class);
        intent.putExtra("selected_item", mLogItems.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ExerciseLogListActivity.class);
        startActivity(intent);
    }
}
