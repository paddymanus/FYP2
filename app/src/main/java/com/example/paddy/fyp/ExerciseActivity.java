package com.example.paddy.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paddy.fyp.adapters.SetRecyclerAdapter;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.Set;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener

{

    private static final String TAG = "ExerciseActivity";
    private LinearLayout activityExerciseParent;
    private LinearLayout activityLogParent;

    // UI components
    private TextView mViewTitle, mSetNumber;
    private ListView mListSet, mListWeight, mListReps;
    private RelativeLayout mBackArrowContainer;
    private ImageButton mBackArrow, mCheck;
    private Button mAddButton;
    private RecyclerView mRecyclerView;
    private EditText setWeightET;
    private EditText setRepsET;

    // vars
    private boolean mIsNewExercise;
    private ExerciseSet exerciseSet, eSet;
    private LogItem mLogItem, mInitialLogItem;
    private ExerciseSet mFinalSet;
    private Exercise mInitialExercise;
    private ArrayList<Set> mSets = new ArrayList<>();
    List<ExerciseSet> exercises = new ArrayList<ExerciseSet>();
    private SetRecyclerAdapter mSetRecyclerAdapter;
    private ExerciseSetRepository mExerciseSetRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        activityExerciseParent = (LinearLayout) findViewById(R.id.activity_exercise_parent);
        activityLogParent = (LinearLayout) findViewById(R.id.parent);
        mViewTitle = findViewById(R.id.note_exercise_title);
        mSetNumber = findViewById(R.id.view_exercise_set_number);
        mBackArrow = findViewById(R.id.toolbar_back_arrow_exercise);
        mCheck = findViewById(R.id.toolbar_check);
        mAddButton = findViewById(R.id.add_field_button);
        setWeightET = findViewById(R.id.view_exercise_set_weight);
        setRepsET = findViewById(R.id.view_exercise_set_reps);
        mRecyclerView = findViewById(R.id.view_exercise_recycler_list);
        mExerciseSetRepository = new ExerciseSetRepository(this);


        if(getIntent().hasExtra("selected_item3")){
            LogItem logItem = getIntent().getParcelableExtra("selected_item3");
            Log.d(TAG, "onCreateThree: " + logItem.toString());
        }


        getIncomingIntentLog();

        if(getIncomingIntent()){
            setNewExerciseProperties();
        }
        else {
            setExerciseProperties();
        }
        setListeners();

        initRecyclerView();

//        ExerciseSet eSet = new ExerciseSet("Bench Press", 1, 17.5, 1);
//        Log.d(TAG, "onCreate: my set: " + eSet.toString());

    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSetRecyclerAdapter = new SetRecyclerAdapter(mSets);
        mRecyclerView.setAdapter(mSetRecyclerAdapter);
    }

    private void addExerciseSet() {
        Set newSet = new Set(getNextSetNumber(), 0, 0);
        mSetRecyclerAdapter.addExerciseSet(newSet);
        mSetRecyclerAdapter.notifyDataSetChanged();
    }



    private int getNextSetNumber(){
        return mSetRecyclerAdapter.getExerciseSets().size() + 1;
    }

    private void updateSets() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager)mRecyclerView.getLayoutManager());
        int fvPosition = layoutManager.findFirstVisibleItemPosition();
        int lvPosition = layoutManager.findLastVisibleItemPosition();
        for(int i = 0; i <= lvPosition - fvPosition; i++) {
            View item  = mRecyclerView.getChildAt(i);
            EditText setWeightET = (EditText) item.findViewById(R.id.view_exercise_set_weight);
            EditText setRepsET = (EditText) item.findViewById(R.id.view_exercise_set_reps);
            try {
                mSets.get(i).set_setWeight(Integer.valueOf(setWeightET.getText().toString()));
            } catch (Exception e) {
                mSets.get(i).set_setWeight(0);
            }
            try {
                mSets.get(i).set_setReps(Integer.valueOf(setRepsET.getText().toString()));
            } catch (Exception e) {
                mSets.get(i).set_setReps(0);
            }
            Log.d(TAG, "getIncomingIntent: " + mSets.toString());
            Toast toast = Toast.makeText(getApplicationContext(), "message" + mSets, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void updateExercise() {
        updateSets();

        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int fvPosition = layoutManager.findFirstVisibleItemPosition();
        int lvPosition = layoutManager.findLastVisibleItemPosition();

        String set = "";
        int volume = 0;
        double temp = 1.0;
        int max = 0;
        int maxTemp = 0;

        for (int i = 0; i <= lvPosition - fvPosition; i++) {

            exerciseSet = new ExerciseSet();
            exerciseSet.setName(mViewTitle.getText().toString());
            exerciseSet.setWorkoutID(mInitialLogItem.getId());

            View item = mRecyclerView.getChildAt(i);
            EditText setWeightET = (EditText) item.findViewById(R.id.view_exercise_set_weight);
            EditText setRepsET = (EditText) item.findViewById(R.id.view_exercise_set_reps);
            TextView setNumberET = (TextView) item.findViewById(R.id.view_exercise_set_number);

            exerciseSet.setWeight((Integer.valueOf(setWeightET.getText().toString())));
            exerciseSet.setReps(Integer.valueOf(setRepsET.getText().toString()));
            exercises.add(exerciseSet);

            if (set.length() != 0) {
                set = set.concat("\n");
            }
            set = set.concat(String.valueOf(exerciseSet.getWeight() + "kg x " + String.valueOf(exerciseSet.getReps())));
            Log.d(TAG, "getSet: " + set);

            temp = (temp * exerciseSet.getWeight() / (1.0278 - (0.0278 * exerciseSet.getReps())));
            max = (int) Math.round(temp);

            if(maxTemp < max){
                exerciseSet.setOnerepmax(max);
            } else {
                exerciseSet.setOnerepmax(maxTemp);
            }
            maxTemp = max;
            temp = 1.0;

            volume += exerciseSet.getWeight() * exerciseSet.getReps();

            exerciseSet.setVolume(volume);
         //   exerciseSet.setOnerepmax(max);
            exerciseSet.setParameters(set);
            Log.d(TAG, "updateExercise: " + exerciseSet.toString());

        }

    }

    private void saveNewExerciseSet()
    {
        mExerciseSetRepository.insertSetTask(exerciseSet);
    }


    public void onDelete(View v) {
        activityExerciseParent.removeView((View) v.getParent());
    }

    private void setListeners(){
        mBackArrow.setOnClickListener(this);
        mAddButton.setOnClickListener(this);
        mCheck.setOnClickListener(this);
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_exercise")){
            mInitialExercise = getIntent().getParcelableExtra("selected_exercise");
            mInitialLogItem = getIntent().getParcelableExtra("selected_item3");
            Log.d(TAG, "getIncomingIntent: " + mInitialExercise.toString() + mInitialLogItem.toString());

            mIsNewExercise = false;
            return false;
        }
        mIsNewExercise = true;
        return true;
    }

    private boolean getIncomingIntentLog(){
        if(getIntent().hasExtra("selected_item")){
            mLogItem = getIntent().getParcelableExtra("selected_item");
            Log.d(TAG, "getIncomingIntent: " + mLogItem.toString());

            mIsNewExercise = false;
            return false;
        }
        mIsNewExercise = true;
        return true;
    }

    private void setExerciseProperties(){
        mViewTitle.setText(mInitialExercise.getName());
    }

    private void setNewExerciseProperties(){
        mViewTitle.setText("Exercise Title");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise:{
                finish();
                break;
            }
            case R.id.add_field_button:{
                addExerciseSet();
                break;
            }
            case R.id.toolbar_check: {
                updateExercise();
                saveNewExerciseSet();
                Intent intent = new Intent(this, ExerciseLogListActivity.class);
                intent.putExtra("selected_item", mInitialLogItem);
                startActivity(intent);
                break;
            }
        }

    }





}
