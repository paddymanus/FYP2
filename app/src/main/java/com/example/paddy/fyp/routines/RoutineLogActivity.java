package com.example.paddy.fyp.routines;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paddy.fyp.MainActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.ExerciseSetRecyclerAdapter;
import com.example.paddy.fyp.adapters.LogItemRecyclerAdapter;
import com.example.paddy.fyp.adapters.RoutineExerciseRecyclerAdapter;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.RoutineExercise;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.example.paddy.fyp.persistence.LogItemRepository;
import com.example.paddy.fyp.persistence.RoutineExerciseRepository;
import com.example.paddy.fyp.persistence.RoutineRepository;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoutineLogActivity extends AppCompatActivity implements
        View.OnClickListener, RoutineExerciseRecyclerAdapter.OnRoutineExerciseListener, LogItemRecyclerAdapter.OnLogItemListener{

    private static final String TAG = "RoutineLogActivity";

    // UI Components
    private RecyclerView mRecyclerView, mRecyclerView2;
    private EditText mEditTitle;
    private Button mButton;
    private ImageButton mBackPressed;
    private TextView mStart;

    // vars
    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private ArrayList<LogItem> mLogItems = new ArrayList<>();
    private ArrayList<RoutineExercise> mRoutineExercise = new ArrayList<>();
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
    private LogItemRecyclerAdapter mLogItemRecyclerAdapter;
    private RoutineExerciseRecyclerAdapter mRoutineExerciseRecyclerAdapter;
    private ExerciseSetRepository mExerciseSetRepository;
    private Exercise mExercise;
    private boolean mIsNewRoutineItem;
    private LogItem mInitialLogItem;
    private LogItem mFinalLogItem;
    private ExerciseSet mExerciseSet;
    private RoutineHome mRoutineHome;
    private RoutineHome mFinalRoutine;
    private LogItemRepository mLogItemRepository;
    private RoutineRepository mRoutineRepository;
    private RoutineExerciseRepository mRoutineExerciseRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_workout);
        mRecyclerView = findViewById(R.id.rvRoutineLog);
        mRecyclerView2 = findViewById(R.id.rvLogItem);
        mEditTitle = findViewById(R.id.name_edit_text);
        mButton = findViewById(R.id.add_exercise_button);
        mBackPressed = findViewById(R.id.toolbar_back_arrow_exercise);
        mStart = findViewById(R.id.note_text_start);
        mExerciseSetRepository = new ExerciseSetRepository(this);
        mLogItemRepository = new LogItemRepository(this);

        mRoutineRepository = new RoutineRepository(this);
        mRoutineExerciseRepository = new RoutineExerciseRepository(this);

        if(getIntent().hasExtra("selected_routine_item")){
            mRoutineHome = getIntent().getParcelableExtra("selected_routine_item");
            Log.d(TAG, "onCreate: " + mRoutineHome);
        }

        if(getIntent().hasExtra("selected_exercise")){
            mExercise = getIntent().getParcelableExtra("selected_exercise");
            Log.d(TAG, "onCreate: " + mExercise);
        }

        if(getIntent().hasExtra("selected_routine")) {
            int intValue = getIntent().getExtras().getInt("selected_routine");
            Log.d(TAG, "onCreate: " + intValue);
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
        initRecyclerView2();
        retrieveSete1();
        retrieveLogItems();
        setListeners();
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_routine_item")){
            mRoutineHome = getIntent().getParcelableExtra("selected_routine_item");
            Log.d(TAG, "getIncomingIntent: " + mRoutineHome.toString());

            mIsNewRoutineItem = false;
            return false;
        }
        mIsNewRoutineItem = true;
        return true;
    }
//
    private void checkIfNew(){
        if(getIncomingIntent()){
            isLogNotNull();
        }
        else {
            mFinalRoutine = getIntent().getParcelableExtra("selected_routine_item");
        }
    }


    private void saveChanges(){
        if(mIsNewRoutineItem){
            saveNewRoutineItem();
        }
        else{

        }
    }
//
    private void saveNewRoutineItem(){
        mRoutineRepository.insertRoutineTask(mFinalRoutine);
    }
//
    private void setLogItemProperties(){
        mEditTitle.setText(mRoutineHome.getTitle());
    }


    private void isLogNotNull() {
        mFinalRoutine = new RoutineHome();
        String temp = mEditTitle.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            int intValue = getIntent().getExtras().getInt("selected_routine");
            mFinalRoutine.setId(intValue);
            mFinalRoutine.setTitle(mEditTitle.getText().toString());
        }
    }
//
    private void setNewLogItemProperties(){
        mEditTitle.setText("");

        mRoutineHome = new RoutineHome();
        mFinalRoutine = new RoutineHome();
        mFinalRoutine.setTitle("");
        mRoutineHome.setTitle("");
    }


    private void retrieveSete1(){
        if(getIntent().hasExtra("selected_routine_item")) {
            mRoutineHome = getIntent().getParcelableExtra("selected_routine_item");
        }
        mRoutineExerciseRepository.retrieveMatchingExerciseTask(mRoutineHome.getId()).observe(this, new Observer<List<RoutineExercise>>() {
            @Override
            public void onChanged(@Nullable List<RoutineExercise> routineExercises) {
                if(mRoutineExercise.size() > 0){
                    mRoutineExercise.clear();
                }
                if(routineExercises != null){
                    mRoutineExercise.addAll(routineExercises);
                }
                mRoutineExerciseRecyclerAdapter.notifyDataSetChanged();
            }
        });
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



    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRoutineExerciseRecyclerAdapter = new RoutineExerciseRecyclerAdapter(mRoutineExercise, this);
        mRecyclerView.setAdapter(mRoutineExerciseRecyclerAdapter);
    }

    private void initRecyclerView2(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView2.setLayoutManager(linearLayoutManager);
        mLogItemRecyclerAdapter = new LogItemRecyclerAdapter(mLogItems, this);
        mRecyclerView2.setAdapter(mLogItemRecyclerAdapter);
        mRecyclerView2.setVisibility(View.GONE);
        Log.d(TAG, "initRecyclerView: " + mLogItems.size());
    }


    private void setListeners(){
        mButton.setOnClickListener(this);
        mBackPressed.setOnClickListener(this);
        mStart.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_exercise_button:{
                checkIfNew();
                saveChanges();
                Intent intent = new Intent(this, RoutinesAddExerciseActivity.class);
                intent.putExtra("selected_routine_home", mFinalRoutine);
                startActivity(intent);
                Log.d(TAG, "onClick: " + mFinalRoutine);
                break;
            }
            case R.id.toolbar_back_arrow_exercise:{
                Intent intent = new Intent(this, RoutinesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.note_text_start:{
                isLogNotNull();
                saveChanges();
                Intent intent = new Intent(this, RoutineExerciseLogListActivity.class);
                intent.putExtra("selected_log", mLogItems.size() + 1);
                intent.putExtra("selected_routine_home", mFinalRoutine);
                intent.putParcelableArrayListExtra("selected_routine_log", mRoutineExercise);
                startActivity(intent);
            }
        }
    }



    private void deleteRoutineExercise(RoutineExercise routineExercise){
        mRoutineExercise.remove(routineExercise);
        mRoutineExerciseRecyclerAdapter.notifyDataSetChanged();

        mRoutineExerciseRepository.deleteRoutineExercise(routineExercise);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteRoutineExercise(mRoutineExercise.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public void onRoutineExerciseClick(int position) {

    }

    @Override
    public void onLogItemClick(int position) {

    }
}
