package com.example.paddy.fyp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paddy.fyp.adapters.ExerciseSetRecyclerAdapter;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.example.paddy.fyp.persistence.LogItemRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseLogListActivity extends AppCompatActivity implements
        View.OnClickListener{

    private static final String TAG = "ExerciseLogListActivity";

    // UI Components
    private RecyclerView mRecyclerView;
    private EditText mEditTitle;
    private Button mButton;
    private ImageButton mBackPressed;
    private TextView mFinish;

    // vars
    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private HashMap<String, String> mSetsHashMap = new HashMap();
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
    private ExerciseSetRepository mExerciseSetRepository;
    private boolean mIsNewLogItem;
    private LogItem mInitialLogItem;
    private LogItem mFinalLogItem;
    private LogItemRepository mLogItemRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log_list);
        mRecyclerView = findViewById(R.id.rvExerciseLog);
        mEditTitle = findViewById(R.id.name_edit_text);
        mButton = findViewById(R.id.add_exercise_button);
        mBackPressed = findViewById(R.id.toolbar_back_arrow_exercise);
        mFinish = findViewById(R.id.note_text_finish);
        mExerciseSetRepository = new ExerciseSetRepository(this);
        mLogItemRepository = new LogItemRepository(this);

        if(getIntent().hasExtra("selected_item")){
            LogItem logItem = getIntent().getParcelableExtra("selected_item");
            Log.d(TAG, "onCreate: " + logItem.toString());
        }

        if(getIncomingIntent()){
            // this is a new log item
            setNewLogItemProperties();
        }
        else{
            // this is NOT a new log item
            setLogItemProperties();
        }

        initRecyclerView();
        retrieveExerciseSets();
        setListeners();
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_item")){
            mInitialLogItem = getIntent().getParcelableExtra("selected_item");
            mFinalLogItem = getIntent().getParcelableExtra("selected_item");
            Log.d(TAG, "getIncomingIntent: " + mInitialLogItem.toString());

            mIsNewLogItem = false;
            return false;
        }
        mIsNewLogItem = true;
        return true;
    }

    private void saveChanges(){
        if(mIsNewLogItem){
            saveNewLogItem();
        }
        else{

        }
    }

    private void saveNewLogItem(){
        mLogItemRepository.insertLogItemTask(mFinalLogItem);
    }

    private void setLogItemProperties(){
        mEditTitle.setText(mInitialLogItem.getTitle());
    }

    private void getExercises(){

    }

    private void setNewLogItemProperties(){
        mEditTitle.setText("");
    }


    private void retrieveExerciseSets(){
        mExerciseSetRepository.retrieveSetTask().observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exerciseSets) {
                if(mSets.size() > 0){
                    mSets.clear();
                }
                if(exerciseSets != null){
                    mSets.addAll(exerciseSets);
                    for(int i = 0; i < mSets.size(); i++){
                        int workoutId = mSets.get(i).getWorkoutID();
                    }
                }
                mExerciseSetRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void retrieveExerciseSetsByID(int id){
        mExerciseSetRepository.retrieveSetTask().observe(this, new Observer<List<ExerciseSet>>() {
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
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseSetRecyclerAdapter = new ExerciseSetRecyclerAdapter(mSets);
        mRecyclerView.setAdapter(mExerciseSetRecyclerAdapter);
    }

    private void setListeners(){
        mButton.setOnClickListener(this);
        mBackPressed.setOnClickListener(this);
        mFinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_exercise_button:{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.toolbar_back_arrow_exercise:{
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.note_text_finish:{
                saveChanges();
                finish();
            }
        }
    }
}
