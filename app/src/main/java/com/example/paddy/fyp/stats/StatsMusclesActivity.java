package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.persistence.ExerciseRepository;
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

public class StatsMusclesActivity extends AppCompatActivity {

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

    private ExerciseRepository mExerciseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_muscles);
        Log.d(TAG, "onCreate: starting to create chart");


        pieChart = (PieChart) findViewById(R.id.PieChart);
        mExerciseRepository = new ExerciseRepository(this);

        Description description = new Description();
        description.setText("Exercise performed from muscle groups ");
        description.setTextSize(34);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Muscles Trained");
        pieChart.setCenterTextSize(10);


        addDataSet();
        retrieveExerciseStats();


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
                String employee = xData[pos1];
                Toast.makeText(StatsMusclesActivity.this, "" + employee + "\n" + "Trained " + sales + " times", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

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
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.GRAY);

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
        mExerciseRepository.retrieveExerciseStat("Legs").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    legSize = exercises.size();
                    yEntrys.add(new PieEntry(legSize));
                    yData[0] = legSize;
                    Log.d(TAG, "onChangedLegs: " + yEntrys);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Chest").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    chestSize = exercises.size();
                    yEntrys.add(new PieEntry(chestSize));
                    yData[1] = chestSize;
                    Log.d(TAG, "onChangedChest: " + chestSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Shoulders").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    shouldersSize = exercises.size();
                    yEntrys.add(new PieEntry(shouldersSize));
                    yData[2] = shouldersSize;
                    Log.d(TAG, "onChangedShoulders: " + shouldersSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Biceps").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    bicepsSize = exercises.size();
                    yEntrys.add(new PieEntry(bicepsSize));
                    yData[3] = bicepsSize;
                    Log.d(TAG, "onChangedBiceps: " + bicepsSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Triceps").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    tricepsSize = exercises.size();
                    yEntrys.add(new PieEntry(tricepsSize));
                    yData[4] = tricepsSize;
                    Log.d(TAG, "onChangedTriceps: " + tricepsSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Back").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    backSize = exercises.size();
                    yEntrys.add(new PieEntry(backSize));
                    yData[5] = backSize;
                    Log.d(TAG, "onChangedBack: " + backSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });
        mExerciseRepository.retrieveExerciseStat("Abs").observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises != null){
                    absSize = exercises.size();
                    yEntrys.add(new PieEntry(absSize));
                    yData[6] = absSize;
                    Log.d(TAG, "onChangedAbs: " + absSize);
                }
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });


    }
}
