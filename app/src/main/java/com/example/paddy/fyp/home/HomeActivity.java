package com.example.paddy.fyp.home;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.paddy.fyp.ExerciseLogListActivity;
import com.example.paddy.fyp.MainActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.LogItemRecyclerAdapter;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.LogItemRepository;
import com.example.paddy.fyp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements LogItemRecyclerAdapter.OnLogItemListener,
        View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private Context mContext = HomeActivity.this;

    // UI components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<LogItem> mLogItems = new ArrayList<>();
    private LogItemRecyclerAdapter mLogItemRecyclerAdapter;
    private LogItemRepository mLogItemRepository;
    private int lvPosition = 1;
    private String hello = "HELO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.rvLogItem);

        findViewById(R.id.fab).setOnClickListener(this);

        mLogItemRepository = new LogItemRepository(this);
        Log.d(TAG, "onCreate: starting");


        initRecyclerView();
     //   insertFakeItems();
        retrieveLogItems();
        setupBottomNavigationView();
    }

    private void retrieveLogItems(){
        mLogItemRepository.retrieveLogItemTask().observe(this, new Observer<List<LogItem>>() {
            @Override
            public void onChanged(@Nullable List<LogItem> logItems) {
                if(mLogItems.size() > 0){
                    mLogItems.clear();
                }
                if(logItems != null){
                    mLogItems.addAll(logItems);
                }
                mLogItemRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void insertFakeItems(){
        for (int i = 0; i < 1000; i++){
            LogItem logItem = new LogItem();
            logItem.setTitle("title # " + i);
            logItem.setContent("content # " + i);
            logItem.setTimestamp("Jan 2019");
            mLogItems.add(logItem);
        }
        mLogItemRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLogItemRecyclerAdapter = new LogItemRecyclerAdapter(mLogItems, this);
        mRecyclerView.setAdapter(mLogItemRecyclerAdapter);
    }

    private void setId(){

    }


    public void getLastPosition() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int lvPosition = layoutManager.findLastVisibleItemPosition() + 1;
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

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.putExtra("selected_item1", mLogItems.get(position));
    }

    @Override
    public void onClick(View v) {
        getLastPosition();
        Intent intent = new Intent(this, ExerciseLogListActivity.class);
        intent.putExtra("selected_log", mLogItems.size() + 1);
        startActivity(intent);
    }

}
