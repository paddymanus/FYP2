package com.example.paddy.fyp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paddy.fyp.adapters.ExerciseSetRecyclerAdapter;
import com.example.paddy.fyp.adapters.SetRecyclerAdapter;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.Set;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.example.paddy.fyp.utils.UtilityDate;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener, SetRecyclerAdapter.OnSetListener,ExerciseSetRecyclerAdapter.onExerciseSetListener

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
    private ImageView mDeleteButton;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private EditText setWeightET;
    private EditText setRepsET;

    // vars
    private boolean mIsNewExercise;
    private ExerciseSet exerciseSet, eSet;
    private LogItem mLogItem, mInitialLogItem;
    private ExerciseSet mInitialSet;
    private Exercise mInitialExercise;
    private ArrayList<Set> mSets = new ArrayList<>();
    private ArrayList<ExerciseSet> mExerciseSets = new ArrayList<>();
    List<ExerciseSet> exercises = new ArrayList<ExerciseSet>();
    private SetRecyclerAdapter mSetRecyclerAdapter;
    private ExerciseSetRecyclerAdapter mExerciseSetRecyclerAdapter;
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
        mDeleteButton = findViewById(R.id.view_exercise_remove_set_button);
        setWeightET = findViewById(R.id.view_exercise_set_weight);
        setRepsET = findViewById(R.id.view_exercise_set_reps);
        mRecyclerView = findViewById(R.id.view_exercise_recycler_list);
        mRecyclerView2 = findViewById(R.id.view_exercise_recycler_list2);
        mExerciseSetRepository = new ExerciseSetRepository(this);


        if(getIntent().hasExtra("selected_item3")){
            LogItem logItem = getIntent().getParcelableExtra("selected_item3");
            Log.d(TAG, "onCreateThree: " + logItem.toString());
        }

        if(getIntent().hasExtra("selected_exercise_set")){
            mInitialSet = getIntent().getParcelableExtra("selected_exercise_set");
            Log.d(TAG, "onCreateExerciseSet: " + mInitialSet.toString());
        }

        getIncomingIntentLog();

        if(getIncomingIntent()){
            setNewExerciseProperties();
        }
        else {
            setExerciseProperties();
        }
        setListeners();

        retrieveExerciseSets();
        initRecyclerView();
        initRecyclerView2();

        autoThreeSets();

//        ExerciseSet eSet = new ExerciseSet("Bench Press", 1, 17.5, 1);
//        Log.d(TAG, "onCreate: my set: " + eSet.toString());

    }

    private void retrieveExerciseSets(){
        mExerciseSetRepository.retrieveSetByTitle(mInitialExercise.getName()).observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exerciseSets) {
                if(mExerciseSets.size() > 0){
                    mExerciseSets.clear();
                }
                if(exerciseSets != null){
                    mExerciseSets.addAll(exerciseSets);
                }
                mExerciseSetRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void autoThreeSets(){
        for(int i = 0; i < 3; i++){
            addExerciseSet();
        }
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSetRecyclerAdapter = new SetRecyclerAdapter(mSets, this);
        mRecyclerView.setAdapter(mSetRecyclerAdapter);
    }

    private void initRecyclerView2(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView2.setLayoutManager(linearLayoutManager);
        mExerciseSetRecyclerAdapter = new ExerciseSetRecyclerAdapter(mExerciseSets, this);
        mRecyclerView2.setAdapter(mExerciseSetRecyclerAdapter);
    }

    private void addExerciseSet() {
        Set newSet = new Set(getNextSetNumber(), 0, 0);
        mSetRecyclerAdapter.addExerciseSet(newSet);
        mSetRecyclerAdapter.notifyItemInserted(mSetRecyclerAdapter.getExerciseSets().size() + 1);
    }

    private void removeExerciseSet(int position) {
        mSets.remove(position);
        mSetRecyclerAdapter.notifyItemRemoved(position);
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

        }
    }

    public int updateExercise() {
        updateSets();
        boolean isCalled = false;
        int alert = 0;

        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int fvPosition = layoutManager.findFirstVisibleItemPosition();
        int lvPosition = layoutManager.findLastVisibleItemPosition();

        String set = "";
        int volume = 0;
        double temp = 1.0;
        int max = 0;
        int maxTemp = 0;
        int weightMax = 0;
        int weightNull = 0;
        int repMax = 0;
        int weightMaxTemp = 0;
        int weightNullTemp = 0;
        int repMaxTemp = 0;

        for (int i = 0; i <= lvPosition - fvPosition; i++) {

            exerciseSet = new ExerciseSet();
            exerciseSet.setName(mViewTitle.getText().toString());
            exerciseSet.setWorkoutID(mInitialLogItem.getId());
            exerciseSet.setCategory(mInitialExercise.getCategory());
            String timestamp = UtilityDate.getCurrentTimeStamp();
            exerciseSet.setTimestamp(timestamp);

            View item = mRecyclerView.getChildAt(i);
            EditText setWeightET = (EditText) item.findViewById(R.id.view_exercise_set_weight);
            EditText setRepsET = (EditText) item.findViewById(R.id.view_exercise_set_reps);
            TextView setNumberET = (TextView) item.findViewById(R.id.view_exercise_set_number);

            weightMax = Integer.valueOf(String.valueOf(setWeightET.getText()));
            repMax = Integer.valueOf(String.valueOf(setRepsET.getText()));
            weightNull = Integer.valueOf(String.valueOf(setWeightET.getText()));
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
            exerciseSet.setParameters(set);
            Log.d(TAG, "updateExercise: " + exerciseSet.toString());

            if(weightMaxTemp > weightMax){
                weightMax = weightMaxTemp;
            }

            if(repMaxTemp > repMax){
                repMax = repMaxTemp;
            }

            if(weightNullTemp > weightNull){
                weightNull = weightNullTemp;
            }

            if (weightMax > 150){
                alert = 1;
            } else if (weightNull == 0){
                alert = 2;
            } else if (repMax == 0){
                alert = 3;
            }
            weightMaxTemp = weightMax;
        }
        return alert;
//        saveExercise();
    }

    private void alertPopup(){
        boolean didPopUp = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseActivity.this);


        builder.setCancelable(true);
        builder.setTitle("WARNING, RECOMMENDED WEIGHT LIMIT EXCEEDED");
        builder.setMessage("Are you sure you want to log this weight?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveExercise();
             //   dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void alertPopupWeight(){
        boolean didPopUp = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseActivity.this);


        builder.setCancelable(true);
        builder.setTitle("You haven't recorded any weight here");
        builder.setMessage("Please record weight for all sets performed");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();
    }

    private void alertPopupReps(){
        boolean didPopUp = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseActivity.this);


        builder.setCancelable(true);
        builder.setTitle("You haven't recorded any reps here");
        builder.setMessage("Please record reps for all sets performed");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();
    }

    private void alertPopupNull(){
        boolean didPopUp = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseActivity.this);


        builder.setCancelable(true);
        builder.setTitle("You haven't recorded anything");
        builder.setMessage("Please record weight and reps for all sets performed");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();
    }

    private void saveExercise(){
        saveNewExerciseSet();
        Intent intent = new Intent(this, ExerciseLogListActivity.class);
        intent.putExtra("selected_item", mInitialLogItem);
        startActivity(intent);
    }

    private void updateExerciseSet(){
        mExerciseSetRepository.updateSet(mInitialSet);
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
        mViewTitle.setText(mInitialSet.getName());
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
            case R.id.view_exercise_remove_set_button: {
                int position = mRecyclerView.getChildAdapterPosition(v);
                removeExerciseSet(position);
            }
            case R.id.toolbar_check: {
                   if (updateExercise() == 1){
                       alertPopup();
                   } else if (updateExercise() == 2){
                       alertPopupWeight();
                   } else if (updateExercise() == 3){
                       alertPopupReps();
                   } else {
                       saveExercise();
                   }
//                    saveExercise();
//                    saveNewExerciseSet();
//                    Intent intent = new Intent(this, ExerciseLogListActivity.class);
//                    intent.putExtra("selected_item", mInitialLogItem);
//                    startActivity(intent);
                break;
            }
        }

    }


    @Override
    public void onSetClick(int position) {
        Log.d(TAG, "onSetClick: clicked" + position);
    }

    @Override
    public void onDeleteClick(int position) {
        removeExerciseSet(position);
    }

    @Override
    public void onExerciseSetClick(int position) {

    }
}
