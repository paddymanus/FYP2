package com.example.paddy.fyp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

import com.example.paddy.fyp.adapters.ExerciseSetRecyclerAdapter;
import com.example.paddy.fyp.adapters.LogItemRecyclerAdapter;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.example.paddy.fyp.persistence.LogItemRepository;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseLogListActivity extends AppCompatActivity implements
        View.OnClickListener, ExerciseSetRecyclerAdapter.onExerciseSetListener{

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
    private LogItemRecyclerAdapter mLogItemRecyclerAdapter;
    private ExerciseSetRepository mExerciseSetRepository;
    private boolean mIsNewLogItem;
    private LogItem mInitialLogItem;
    private LogItem mFinalLogItem;
    private ExerciseSet mExerciseSet;
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
       // retrieveExerciseSets();
        retrieveSete1();
      //  retrieveSetByName();
        setListeners();
        if(getIntent().hasExtra("selected_log")) {
            int intValueHere = getIntent().getExtras().getInt("selected_log");
            Log.d(TAG, "onCreate: " + intValueHere);
        }
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
        if(getIncomingIntent()){
            isLogNotNull();
        }
        else {
            mFinalLogItem = getIntent().getParcelableExtra("selected_item");
        }
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

    private void isLogNotNull() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int fvPosition = layoutManager.findFirstVisibleItemPosition();
        int lvPosition = layoutManager.findLastVisibleItemPosition();

        String temp = mEditTitle.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            int intValue = getIntent().getExtras().getInt("selected_log");
            Log.d(TAG, "isLogNotNull: " + intValue);
            mFinalLogItem.setId(intValue);
            mFinalLogItem.setTitle(mEditTitle.getText().toString());
            String content = "Bench Press, Shoulder Press, Dumbell Flies, Laterial Raises";
            String timestamp = UtilityDate.getCurrentTimeStamp();
            mFinalLogItem.setContent(content);
            mFinalLogItem.setTimestamp(timestamp);
            Log.d(TAG, "isLogNotNull: " + mFinalLogItem);

        }
    }

    private void setNewLogItemProperties(){
        mEditTitle.setText("");

        mInitialLogItem = new LogItem();
        mFinalLogItem = new LogItem();
        mInitialLogItem.setTitle("");
        mFinalLogItem.setTitle("");
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
        mExerciseSetRepository.retrieveSet1(mInitialLogItem.getId()).observe(this, new Observer<List<ExerciseSet>>() {
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
        mButton.setOnClickListener(this);
        mBackPressed.setOnClickListener(this);
        mFinish.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_exercise_button:{
                checkIfNew();
                saveChanges();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("selected_item1", mFinalLogItem);
                startActivity(intent);
                Log.d(TAG, "onClick: " + mFinalLogItem);
                break;
            }
            case R.id.toolbar_back_arrow_exercise:{
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            }
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
        Log.d(TAG, "onExerciseSetClick: " + position);
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
