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
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.persistence.BodyweightRepository;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class StatsBodyweightStatActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Stats1rmActivity";

    //ui components
    private ImageButton mBackButton;
    private TextView mViewTitle;

    // vars
    private LineChart lineChart;
    private BodyweightRepository mBodyweightRepository;
    ArrayList<Entry> yValues = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_bodyweight_stats);

        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        lineChart = (LineChart) findViewById(R.id.LineChartBodyweight);

        mBodyweightRepository = new BodyweightRepository(this);


        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);


        retrieveBodyweight();
        setListeners();

    }

    private void retrieveBodyweight(){
        mBodyweightRepository.retrieveBodyweightTask().observe(this, new Observer<List<Bodyweight>>() {
            @Override
            public void onChanged(@Nullable List<Bodyweight> bodyweights) {
                if(bodyweights != null){
                    for(int i = 0; i < bodyweights.size(); i++){
                        int weight = bodyweights.get(i).getWeight();
                        String timestamp = bodyweights.get(i).getTimestamp();
                        yValues.add(new Entry(i, weight));
                        Log.d(TAG, "onChanged: " + weight);
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


    private void setListeners(){
        mBackButton.setOnClickListener(this);

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

    }
}
