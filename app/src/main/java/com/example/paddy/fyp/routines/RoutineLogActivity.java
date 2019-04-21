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
        View.OnClickListener, RoutineExerciseRecyclerAdapter.OnRoutineExerciseListener{

    private static final String TAG = "ExerciseLogListActivity";

    // UI Components
    private RecyclerView mRecyclerView;
    private EditText mEditTitle;
    private Button mButton;
    private ImageButton mBackPressed;
    private TextView mStart;

    // vars
    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private ArrayList<RoutineExercise> mRoutineExercise = new ArrayList<>();
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
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
        mEditTitle = findViewById(R.id.name_edit_text);
        mButton = findViewById(R.id.add_exercise_button);
        mBackPressed = findViewById(R.id.toolbar_back_arrow_exercise);
        mStart = findViewById(R.id.note_text_start);
        mExerciseSetRepository = new ExerciseSetRepository(this);

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
        retrieveSete1();
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
            mFinalLogItem = getIntent().getParcelableExtra("selected_item");
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



    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRoutineExerciseRecyclerAdapter = new RoutineExerciseRecyclerAdapter(mRoutineExercise, this);
        mRecyclerView.setAdapter(mRoutineExerciseRecyclerAdapter);
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
                intent.putExtra("selected_routine_home", mRoutineHome);
                startActivity(intent);
                Log.d(TAG, "onClick: " + mFinalLogItem);
                break;
            }
            case R.id.toolbar_back_arrow_exercise:{
                Intent intent = new Intent(this, RoutinesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.note_text_start:{
//                isLogNotNull();
//                saveChanges();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }



    private void deleteExercise(ExerciseSet exerciseSet){
        mSets.remove(exerciseSet);
        mExerciseSetRecyclerAdapter.notifyDataSetChanged();

        mExerciseSetRepository.deleteSet(exerciseSet);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteExercise(mSets.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public void onRoutineExerciseClick(int position) {

    }
}
