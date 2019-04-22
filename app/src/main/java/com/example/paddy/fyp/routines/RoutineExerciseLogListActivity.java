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
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.RoutineExercise;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.example.paddy.fyp.persistence.LogItemRepository;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoutineExerciseLogListActivity extends AppCompatActivity implements
        View.OnClickListener, ExerciseSetRecyclerAdapter.onExerciseSetListener{

    private static final String TAG = "RoutineExerciseLogListActivity";

    // UI Components
    private RecyclerView mRecyclerView;
    private EditText mEditTitle;
    private TextView mFinish;

    // vars
    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private ArrayList<RoutineExercise> mRoutineExercise = new ArrayList<>();
    private HashMap<String, String> mSetsHashMap = new HashMap();
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
    private ExerciseSet exerciseSet;
    private ExerciseSetRepository mExerciseSetRepository;
    private boolean mIsNewLogItem;
    private LogItem mInitialLogItem;
    private LogItem mFinalLogItem;
    private RoutineHome mRoutineHome;
    private ExerciseSet mExerciseSet;
    private LogItemRepository mLogItemRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_exercise_log_list);
        mRecyclerView = findViewById(R.id.rvExerciseLog);
        mEditTitle = findViewById(R.id.name_edit_text);
        mFinish = findViewById(R.id.note_text_finish);
        mExerciseSetRepository = new ExerciseSetRepository(this);
        mLogItemRepository = new LogItemRepository(this);

        if(getIntent().hasExtra("selected_item")){
            LogItem logItem = getIntent().getParcelableExtra("selected_item");
            Log.d(TAG, "onCreate: " + logItem.toString());
        }

        if(getIntent().hasExtra("selected_routine_log")){
            mRoutineExercise = getIntent().getParcelableArrayListExtra("selected_routine_log");
            Log.d(TAG, "onCreate: " + mRoutineExercise);
        }

        if(getIntent().hasExtra("selected_routine_home")){
            mRoutineHome = getIntent().getParcelableExtra("selected_routine_home");
            mEditTitle.setText(mRoutineHome.getTitle());
            Log.d(TAG, "onCreateExerciseSet: " + mRoutineHome.toString());
        }

//        if(getIncomingIntent()){
//            // this is a new log item
//            setNewLogItemProperties();
//        }
//        else{
//            // this is NOT a new log item
//            setLogItemProperties();
//        }


        //setLogItemProperties();

        initRecyclerView();
       // isLogNotNull();

       // retrieveExerciseSets();
        retrieveSete1();
      //  retrieveSetByName();
        setListeners();
        sortExercise();

//        int intValueHere = getIntent().getExtras().getInt("selected_log");
//        Log.d(TAG, "onCreate: " + intValueHere);


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

    private void checkIfNew(){
        isLogNotNull();
    }


    private void saveChanges(){
        if(mIsNewLogItem){
            saveNewLogItem();
        }
        else{
            updateLogItem();
        }
    }


    private void saveNewLogItem(){
        mLogItemRepository.insertLogItemTask(mFinalLogItem);
    }

    private void updateLogItem(){
        mLogItemRepository.updateLogItemTask(mFinalLogItem);
    }

    private void setLogItemProperties(){
        mEditTitle.setText(mRoutineHome.getTitle());
    }

    private void getExercises(){

    }

    private void isLogNotNull() {
        if(getIntent().hasExtra("selected_routine_home")){
            mRoutineHome = getIntent().getParcelableExtra("selected_routine_home");
            Log.d(TAG, "onCreateExerciseSet: " + mRoutineHome.toString());
        }
        mFinalLogItem = new LogItem();
        mEditTitle.setText(mRoutineHome.getTitle());
        String temp = mEditTitle.getText().toString();
        Log.d(TAG, "isLogNotNull: " + temp);
        Log.d(TAG, "isLogNotNull: " + mEditTitle.toString());
        int intVal = getIntent().getExtras().getInt("selected_log");
//        temp = temp.replace("\n", "");
//        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            mFinalLogItem.setId(intVal);
            mFinalLogItem.setTitle(mRoutineHome.getTitle());
            String content = "Bench Press, Shoulder Press, Dumbell Flies, Laterial Raises";
            String timestamp = UtilityDate.getCurrentTimeStamp();
            mFinalLogItem.setContent(content);
            mFinalLogItem.setTimestamp(timestamp);
            Log.d(TAG, "isLogNotNull: " + mFinalLogItem);
            mLogItemRepository.insertLogItemTask(mFinalLogItem);

        }
    }

//    private void setNewLogItemProperties(){
//        mEditTitle.setText("");
//
//        mInitialLogItem = new LogItem();
//        mFinalLogItem = new LogItem();
//        mInitialLogItem.setTitle("");
//        mFinalLogItem.setTitle("");
//    }

    private void sortExercise(){


        int intValue = getIntent().getExtras().getInt("selected_log");
        Log.d(TAG, "onCreate: " + intValue);
        if(getIntent().hasExtra("selected_routine_log")){
            mRoutineExercise = getIntent().getParcelableArrayListExtra("selected_routine_log");
            Log.d(TAG, "onCreate: " + mRoutineExercise);
        }

        for(int i = 0; i < mRoutineExercise.size(); i++){
            exerciseSet = new ExerciseSet();
            exerciseSet.setName(mRoutineExercise.get(i).getName());
            exerciseSet.setWorkoutID(intValue);
            exerciseSet.setVolume(0);
            exerciseSet.setOnerepmax(0);
            exerciseSet.setParameters("...");
            exerciseSet.setWeight(0);
            exerciseSet.setReps(0);
            String timestamp = UtilityDate.getCurrentTimeStamp();
            exerciseSet.setTimestamp(timestamp);
            String name = mRoutineExercise.get(i).getName();
            mExerciseSetRepository.insertSetTask(exerciseSet);
            Log.d(TAG, "sortExercise: " + name);
        }
    }

    private void saveNewExerciseSet()
    {
        mExerciseSetRepository.insertSetTask(exerciseSet);
    }

    private void updateSet(int position){
        mExerciseSetRepository.updateSet(exerciseSet);
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
                }
                mExerciseSetRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void retrieveSete1(){
        int intValue = getIntent().getExtras().getInt("selected_log");
        Log.d(TAG, "onCreate: " + intValue);
        mExerciseSetRepository.retrieveSet1(intValue).observe(this, new Observer<List<ExerciseSet>>() {
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mExerciseSetRecyclerAdapter = new ExerciseSetRecyclerAdapter(mSets, this);
        mRecyclerView.setAdapter(mExerciseSetRecyclerAdapter);
    }




    private void setListeners(){
////        mButton.setOnClickListener(this);
//        mBackPressed.setOnClickListener(this);
        mFinish.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.add_exercise_button:{
//                checkIfNew();
//                saveChanges();
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("selected_item1", mFinalLogItem);
//                startActivity(intent);
//                Log.d(TAG, "onClick: " + mFinalLogItem);
//                break;
//            }
//            case R.id.toolbar_back_arrow_exercise:{
//                Intent intent = new Intent(this, RoutineLogActivity.class);
//                intent.putExtra("selected_routine_item", mRoutineHome);
//                startActivity(intent);
//                break;
//            }
            case R.id.note_text_finish:{
                isLogNotNull();
                saveChanges();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onExerciseSetClick(int position) {
        int intValue = getIntent().getExtras().getInt("selected_log");
        Log.d(TAG, "onExerciseSetClick: " + position);
        Intent intent = new Intent(this, RoutineExerciseActivity.class);
        intent.putExtra("selected_item", mSets.get(position));
        intent.putExtra("selected_log", intValue);
        intent.putExtra("selected_routine_home", mRoutineHome);
        startActivity(intent);
//        Intent intent = new Intent(this, ExerciseActivity.class);
//        intent.putExtra("selected_exercise_set", mSets.get(position));
//        startActivity(intent);
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

}
