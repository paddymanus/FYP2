package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.BodyweightRecyclerAdapter;
import com.example.paddy.fyp.adapters.StatsHomeRecyclerAdapter;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.persistence.BodyweightRepository;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;
import java.util.List;

public class StatsAddBodyweightActivity extends AppCompatActivity implements BodyweightRecyclerAdapter.OnBodyweightListener, View.OnClickListener {
    private static final String TAG = "StatsActivity";

    // Ui components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private EditText mEditWeight;
    private Button mButton;

    // vars
    private ArrayList<Bodyweight> mBodyweight = new ArrayList<>();
    private Bodyweight mWeight = new Bodyweight();
    private BodyweightRepository mBodyweightRepository;
    private BodyweightRecyclerAdapter mBodyweightRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_bodyweight_add);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.rvBodyweight);
        mEditWeight = findViewById(R.id.insert_weight_edit_text);
        mButton = findViewById(R.id.add_weight_button);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);
        mBodyweightRepository = new BodyweightRepository(this);

        initRecyclerView();
        retrieveBodyweight();
        setListeners();

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mBodyweightRecyclerAdapter = new BodyweightRecyclerAdapter(mBodyweight, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mBodyweightRecyclerAdapter);
    }

    private void retrieveBodyweight(){
        mBodyweightRepository.retrieveBodyweightTask().observe(this, new Observer<List<Bodyweight>>() {
            @Override
            public void onChanged(@Nullable List<Bodyweight> bodyweights) {
                if(mBodyweight.size() > 0){
                    mBodyweight.clear();
                }
                if(bodyweights != null){
                    mBodyweight.addAll(bodyweights);
                }
                mBodyweightRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void isWeightNotNull() {
        String temp = mEditWeight.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            String timestamp = UtilityDate.getCurrentTimeStamp();
            mWeight.setWeight(Float.valueOf(String.valueOf(mEditWeight.getText())));
            mWeight.setTimestamp(timestamp);
        }
        mBodyweight.add(mWeight);
        mBodyweightRecyclerAdapter.notifyItemInserted(mBodyweightRecyclerAdapter.getItemCount() + 1);
    }

    private void saveBodyweight(){
        mBodyweightRepository.insertBodyweightTask(mWeight);
        Log.d(TAG, "saveBodyweight: " + mWeight);
    }


    private void setListeners(){
        mBackButton.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsBodyweightActivity.class);
                startActivity(intent);
                break;
            }
        }
        switch (v.getId()){
            case R.id.add_weight_button:{
                isWeightNotNull();
                saveBodyweight();
                break;
            }
        }
    }

    private void deleteBodyweight(Bodyweight bodyweight){
        mBodyweight.remove(bodyweight);
        mBodyweightRecyclerAdapter.notifyDataSetChanged();

        mBodyweightRepository.deleteBodyweight(bodyweight);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteBodyweight(mBodyweight.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public void onBodyweightClicked(int position) {
        if(position == 0){
            Log.d(TAG, "onStatClicked: this is list");
//            Intent intent = new Intent(this, StatsMusclesActivity.class);
//            startActivity(intent);
        }
        else if(position == 1){
            Log.d(TAG, "onStatClicked: this is stats");
        }
    }
}
