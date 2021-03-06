package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.persistence.ExerciseRepository;
import com.example.paddy.fyp.persistence.ExerciseSetRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsMusclesActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "StatsMusclesActivity";

    private int legSize;
    private int chestSize;
    private int shouldersSize;
    private int bicepsSize;
    private int tricepsSize;
    private int backSize;
    private int absSize;
    DataSet dataSet;
    ArrayList<PieEntry> yEntrys = new ArrayList<>();
    ArrayList<String> xEntrys = new ArrayList<>();
    private int[] yData = {legSize, chestSize, shouldersSize, bicepsSize, tricepsSize, backSize, absSize};
    private String[] xData = {"Legs", "Chest", "Shoulders", "Biceps", "Triceps", "Back", "Abs"};
    PieChart pieChart;
    private ImageButton mBackButton;

    private ExerciseRepository mExerciseRepository;
    private ExerciseSetRepository mExerciseSetRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_muscles);
        Log.d(TAG, "onCreate: starting to create chart");


        pieChart = (PieChart) findViewById(R.id.PieChart);
        mExerciseRepository = new ExerciseRepository(this);
        mExerciseSetRepository = new ExerciseSetRepository(this);
        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        Description description = new Description();
      //  description.setText("Exercise performed from muscle groups ");
        description.setTextSize(15);
        description.setPosition(2,6);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Muscles Trained");
        pieChart.setCenterTextSize(10);


        addDataSet();
        retrieveExerciseStats();
        setListeners();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value selected from chart");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String sales = e.toString().substring(pos1 + 3);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                String muscle = xData[pos1];
                Toast.makeText(StatsMusclesActivity.this, "" + muscle + "\n" + "Trained " + sales + " times", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void setListeners(){
        mBackButton.setOnClickListener(this);

    }



    private void addDataSet() {
        Log.d(TAG, "addDataSet: started");


//        for(int i = 0; i < yData.length; i++){
//            yEntrys.add(new PieEntry(yData[i] , i));
//        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Muscles Trained");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#003f5c"));
        colors.add(Color.parseColor("#374c80"));
        colors.add(Color.parseColor("#7a5195"));
        colors.add(Color.parseColor("#bc5090"));
        colors.add(Color.parseColor("#ef5675"));
        colors.add(Color.parseColor("#ff764a"));
        colors.add(Color.parseColor("#ffa600"));

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        Log.d(TAG, "addDataSet: " + yEntrys);
        pieChart.invalidate();
    }

    public void retrieveExerciseStats(){
        mExerciseSetRepository.retrieveExerciseSetStat("Legs").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    legSize = exercises.size();
                    yEntrys.add(new PieEntry(legSize, "LEGS"));
                    yData[0] = legSize;
                    Log.d(TAG, "onChangedLegs: " + yEntrys);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Chest").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    chestSize = exercises.size();
                    yEntrys.add(new PieEntry(chestSize, "CHEST"));
                    yData[1] = chestSize;
                    Log.d(TAG, "onChangedChest: " + chestSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Shoulders").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    shouldersSize = exercises.size();
                    yEntrys.add(new PieEntry(shouldersSize, "SHOULDERS"));
                    yData[2] = shouldersSize;
                    Log.d(TAG, "onChangedShoulders: " + shouldersSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Biceps").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    bicepsSize = exercises.size();
                    yEntrys.add(new PieEntry(bicepsSize, "BICEPS"));
                    yData[3] = bicepsSize;
                    Log.d(TAG, "onChangedBiceps: " + bicepsSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Triceps").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    tricepsSize = exercises.size();
                    yEntrys.add(new PieEntry(tricepsSize, "TRICEPS"));
                    yData[4] = tricepsSize;
                    Log.d(TAG, "onChangedTriceps: " + tricepsSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Back").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    backSize = exercises.size();
                    yEntrys.add(new PieEntry(backSize, "BACK"));
                    yData[5] = backSize;
                    Log.d(TAG, "onChangedBack: " + backSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseSetRepository.retrieveExerciseSetStat("Abs").observe(this, new Observer<List<ExerciseSet>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseSet> exercises) {
                if(exercises != null){
                    absSize = exercises.size();
                    yEntrys.add(new PieEntry(absSize, "ABS"));
                    yData[6] = absSize;
                    Log.d(TAG, "onChangedAbs: " + absSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back_arrow_exercise_stats:{
                Intent intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
