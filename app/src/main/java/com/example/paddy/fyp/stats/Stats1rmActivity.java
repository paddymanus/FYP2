package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Stats1rmActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Stats1rmActivity";

    //ui components
    private ImageButton mBackButton;
    private TextView mViewTitle;

    // vars
    private LineChart lineChart;
    private StatsHome mIntialStatsHome;
    private Exercise mInitialExercise;
    private ExerciseSetRepository mExerciseSetRepository;
    ArrayList<Entry> yValues = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_1rm);

        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);
        mViewTitle = findViewById(R.id.stats_exercise_title);

        lineChart = (LineChart) findViewById(R.id.LineChart1rm);

        mExerciseSetRepository = new ExerciseSetRepository(this);

        if(getIntent().hasExtra("selected_stat")){
            mIntialStatsHome = getIntent().getParcelableExtra("selected_stat");
            Log.d(TAG, "onCreateOne: " + mIntialStatsHome.toString());
        }

        if(getIntent().hasExtra("selected_exercise")){
            mInitialExercise = getIntent().getParcelableExtra("selected_exercise");
            setExerciseProperties();
            Log.d(TAG, "onCreateTwo: " + mInitialExercise.toString());
        }

//        mChart.setOnChartGestureListener(Stats1rmActivity.this);
//        mChart.setOnChartValueSelectedListener(Stats1rmActivity.this);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setVisibleXRangeMaximum(10);


        retrieveExercise();
        setListeners();

    }

    private void retrieveExercise(){
        mExerciseSetRepository.retrieveSetByTitle(mInitialExercise.getName()).observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exerciseSets) {
                if(exerciseSets != null){
                    for(int i = 0; i < exerciseSets.size(); i++){
                        int onerepmax = exerciseSets.get(i).getOnerepmax();
                        yValues.add(new Entry(i, onerepmax));
                        Log.d(TAG, "onChanged: " + onerepmax);
                        Log.d(TAG, "onChanged: " + yValues);
                    }
                }
                LineDataSet set1 = new LineDataSet(yValues, "Weight KG");

                set1.setFillAlpha(150);
                set1.setColor(Color.RED);
                set1.setLineWidth(3f);
                //set1.setDrawFilled(true);
                //set1.setFillColor(Color.CYAN);
                set1.setValueTextSize(15f);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                LineData data = new LineData(dataSets);

                lineChart.setData(data);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }
        });
    }

    private void setExerciseProperties(){
        mViewTitle.setText(mInitialExercise.getName());
    }

    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsOptionsActivity.class);
                intent.putExtra("selected_stat", mIntialStatsHome);
                intent.putExtra("selected_exercise", mInitialExercise);
                startActivity(intent);
                break;
            }
        }

    }
}
