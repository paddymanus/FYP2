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
import com.example.paddy.fyp.persistence.BodyweightRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
    ArrayList<String> xValues = new ArrayList<String>();
    ArrayList<String> xStringValues = new ArrayList<>();
    XAxis xAxis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_bodyweight_stats);

        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);

        lineChart = (LineChart) findViewById(R.id.LineChartBodyweight);

        mBodyweightRepository = new BodyweightRepository(this);



        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        YAxis rightAxis = this.lineChart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextSize(0);

        Description description = new Description();
        description.setText("Bodyweight over time");
        description.setTextSize(15);
        lineChart.setDescription(description);


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
                        Log.d(TAG, "onChanged: " + timestamp);
                        xValues.add(i,timestamp);
                        yValues.add(new Entry(i, weight));

                        xAxis = lineChart.getXAxis();
                        xAxis.setTextColor(Color.BLACK);
                        xAxis.setTextSize(15);
                        xAxis.setDrawGridLines(false);
                        xAxis.isCenterAxisLabelsEnabled();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues) {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return xValues.get((int) value);
                            }
                        });
                    }
                }
                LineDataSet set1 = new LineDataSet(yValues, "Weight KG");

                set1.setFillAlpha(150);
                set1.setColor(Color.RED);
                set1.setLineWidth(2f);
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
