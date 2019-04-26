package com.example.paddy.fyp.stats;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.persistence.BodyweightRepository;
import com.example.paddy.fyp.persistence.MeasurementRepository;
import com.example.paddy.fyp.utils.UtilityDate;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class StatsMeasurementsStatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static final String TAG = "Stats1rmActivity";

    //ui components
    private ImageButton mBackButton;
    private TextView mViewTitle;
    private Spinner mEditBodypart;


    // vars
    private LineChart lineChart;
    private MeasurementRepository mMeasurementRepository;
    ArrayList<Entry> yValues = new ArrayList<>();
    ArrayList<String> xValues = new ArrayList<String>();
    XAxis xAxis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_measurements_stats);

        mBackButton = findViewById(R.id.toolbar_back_arrow_exercise_stats);
        mEditBodypart = findViewById(R.id.spinnerBodypart);

        lineChart = (LineChart) findViewById(R.id.LineChartBodyweight);

        mMeasurementRepository = new MeasurementRepository(this);

        Spinner spinner = findViewById(R.id.spinnerBodypart);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.bodyparts, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);


        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        Description description = new Description();
        description.setText(" ");
        description.setTextSize(15);
        lineChart.setDescription(description);


        //retrieveBodyweight();
        setListeners();

    }

    private void retrieveBodyweight(){
        String bodypart = mEditBodypart.getSelectedItem().toString();
        mMeasurementRepository.retrieveMeasurementByBodypart(bodypart).observe(this, new Observer<List<Measurement>>() {
            @Override
            public void onChanged(@Nullable List<Measurement> measurements) {
                if(measurements != null){
                    for(int i = 0; i < measurements.size(); i++){
                        float weight = measurements.get(i).getMeasurement();
                        String timestamp = measurements.get(i).getTimestamp();
                        int pos1 = timestamp.indexOf("-");
                        String month = timestamp.substring(pos1 + 1);
                        String date = timestamp.substring(0, 3);
                        String time = UtilityDate.getMonthFromNumber(month);
                        String finalTimestamp = date + time;
                        yValues.add(new Entry(i, weight));
                        xValues.add(finalTimestamp);
                        Log.d(TAG, "onChanged: " + weight);
                        Log.d(TAG, "onChanged: " + yValues);
                        Log.d(TAG, "onChanged: " + xValues);

                    }
                    xAxis = lineChart.getXAxis();
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setTextSize(15);
                    xAxis.setDrawGridLines(false);
                    xAxis.isCenterAxisLabelsEnabled();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
                    xAxis.setGranularity(1f);
                }
                LineDataSet set1 = new LineDataSet(yValues, "Inches");


                set1.setFillAlpha(150);
                set1.setColor(Color.RED);
                set1.setCircleColor(Color.BLACK);
                set1.setLineWidth(2f);
                set1.setDrawFilled(true);
                set1.setFillColor(Color.parseColor("#FF8282"));
                set1.setHighLightColor(Color.BLACK);
                set1.setHighlightEnabled(true);
                set1.setValueTextSize(15f);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                LineData data = new LineData(dataSets);

                lineChart.setData(data);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

                lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        float y = e.getY();
                        Toast.makeText(StatsMeasurementsStatActivity.this, "" + y + " Inches", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });
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
                Intent intent = new Intent(this, StatsMeasurementsActivity.class);
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        yValues.clear();
        xValues.clear();
        retrieveBodyweight();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
