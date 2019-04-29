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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.adapters.BodyweightRecyclerAdapter;
import com.example.paddy.fyp.adapters.MeasurementRecyclerAdapter;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.persistence.BodyweightRepository;
import com.example.paddy.fyp.persistence.MeasurementRepository;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;
import java.util.List;

public class StatsAddMeasurementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MeasurementRecyclerAdapter.OnMeasurementListener, View.OnClickListener {
    private static final String TAG = "StatsActivity";

    // Ui components
    private RecyclerView mRecyclerView;
    private ImageButton mBackButton;
    private EditText mEditWeight;
    private Button mButton;
    private Spinner mEditBodypart;

    // vars
    private ArrayList<Measurement> mMeasurement = new ArrayList<>();
    private Measurement mMeasure = new Measurement();
    private MeasurementRepository mMeasurementRepository;
    private MeasurementRecyclerAdapter mMeasurementRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_measurements_add);
        Log.d(TAG, "onCreate: started");
        mRecyclerView = findViewById(R.id.rvBodyweight);
        mEditWeight = findViewById(R.id.insert_measurement_edit_text);
        mButton = findViewById(R.id.add_measurement_button);
        mEditBodypart = findViewById(R.id.spinnerBodypart);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        mMeasurementRepository = new MeasurementRepository(this);

        Spinner spinner = findViewById(R.id.spinnerBodypart);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.bodyparts, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);


        initRecyclerView();
        retrieveMeasurement();
        setListeners();

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mMeasurementRecyclerAdapter = new MeasurementRecyclerAdapter(mMeasurement, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mMeasurementRecyclerAdapter);
    }

    private void retrieveMeasurement(){
        mMeasurementRepository.retrieveMeasurementTask().observe(this, new Observer<List<Measurement>>() {
            @Override
            public void onChanged(@Nullable List<Measurement> measurements) {
                if(mMeasurement.size() > 0){
                    mMeasurement.clear();
                }
                if(measurements != null){
                    mMeasurement.addAll(measurements);
                }
                mMeasurementRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void isMeasurementNotNull() {
        String temp = mEditWeight.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            String timestamp = UtilityDate.getCurrentTimeStamp();
            mMeasure.setMeasurement(Float.valueOf(String.valueOf(mEditWeight.getText())));
            mMeasure.setTimestamp(timestamp);
            String bodypart = mEditBodypart.getSelectedItem().toString();
            mMeasure.setBodypart(bodypart);
        }
        mMeasurement.add(mMeasure);
        mMeasurementRecyclerAdapter.notifyItemInserted(mMeasurementRecyclerAdapter.getItemCount() + 1);
    }

    private void saveMeasurement(){
        mMeasurementRepository.insertMeasurementTask(mMeasure);
        Log.d(TAG, "saveBodyweight: " + mMeasure);
    }


    private void setListeners(){
        mBackButton.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsMeasurementsActivity.class);
                startActivity(intent);
                break;
            }
        }
        switch (v.getId()){
            case R.id.add_measurement_button:{
                isMeasurementNotNull();
                saveMeasurement();
                break;
            }
        }
    }

    @Override
    public void onMeasurementClicked(int position) {
        if(position == 0){
            Log.d(TAG, "onStatClicked: this is list");
//            Intent intent = new Intent(this, StatsMusclesActivity.class);
//            startActivity(intent);
        }
        else if(position == 1){
            Log.d(TAG, "onStatClicked: this is stats");
        }
    }

    private void deleteMeasurement(Measurement measurement){
        mMeasurement.remove(measurement);
        mMeasurementRecyclerAdapter.notifyDataSetChanged();

        mMeasurementRepository.deleteMeasurement(measurement);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteMeasurement(mMeasurement.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bodypart = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
