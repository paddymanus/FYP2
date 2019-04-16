package com.example.paddy.fyp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.paddy.fyp.adapters.ExerciseRecyclerAdapter;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.ExerciseRepository;
import com.example.paddy.fyp.stats.StatsMusclesActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ExerciseRecyclerAdapter.OnExerciseListener,
        View.OnClickListener{

    private static final String TAG = "MainActivity";

    // UI components
    private RecyclerView mRecyclerView;
    private ImageButton mAddButton, mBackButton;

    // vars
    private ArrayList<Exercise> mExercise = new ArrayList<>();
    private ExerciseRecyclerAdapter mExerciseRecyclerAdapter;
    private ExerciseRepository mExerciseRepository;
    private boolean mIsNewLogItem;
    private LogItem mInitialLogItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        
        mRecyclerView = findViewById(R.id.rvAddExercise);
        mAddButton = findViewById(R.id.toolbar_add);
        mBackButton = findViewById(R.id.toolbar_back_arrow_select_exercise);

        mExerciseRepository = new ExerciseRepository(this);

        if(getIntent().hasExtra("selected_item1")){
            LogItem logItem1 = getIntent().getParcelableExtra("selected_item1");
            Log.d(TAG, "onCreateOne: " + logItem1.toString());
        }


        getIncomingIntent();

        initRecyclerView();
        retrieveExercises();
        setListeners();

    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_item1")){
            mInitialLogItem = getIntent().getParcelableExtra("selected_item1");
            Log.d(TAG, "getIncomingIntent: " + mInitialLogItem.toString());

            mIsNewLogItem = false;
            return false;
        }
        mIsNewLogItem = true;
        return true;
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


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseRecyclerAdapter = new ExerciseRecyclerAdapter(mExercise, this);
        mRecyclerView.setAdapter(mExerciseRecyclerAdapter);
    }

    private void setListeners(){
        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

    }


    @Override
    public void onExerciseClick(int position) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("selected_exercise", mExercise.get(position));
        intent.putExtra("selected_item3", mInitialLogItem);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_add:{
                Intent intent = new Intent(this, NewExerciseActivity.class);
                intent.putExtra("selected_item2", mInitialLogItem);
                startActivity(intent);
                break;
            }
            case R.id.toolbar_back_arrow_select_exercise:{
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
