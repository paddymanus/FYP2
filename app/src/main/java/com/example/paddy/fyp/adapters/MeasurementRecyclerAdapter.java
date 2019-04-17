package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Bodyweight;
import com.example.paddy.fyp.models.Measurement;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;

public class MeasurementRecyclerAdapter extends RecyclerView.Adapter<MeasurementRecyclerAdapter.ViewHolder> {

    private static final String TAG = "MeasurementRecyclerAdap";

    private ArrayList<Measurement> mMeasurement = new ArrayList<>();
    private OnMeasurementListener mOnMeasurementListener;

    public MeasurementRecyclerAdapter(ArrayList<Measurement> mMeasurement, OnMeasurementListener onMeasurementListener) {
        this.mMeasurement = mMeasurement;
        this.mOnMeasurementListener = onMeasurementListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_measurement_item, viewGroup, false);
        return new ViewHolder(view, mOnMeasurementListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            String month = mMeasurement.get(i).getTimestamp().substring(3);
            month = UtilityDate.getMonthFromNumber(month);
            String date = mMeasurement.get(i).getTimestamp().substring(0, 2);
            String timestamp = date + "\n" + month;
            viewHolder.timestamp.setText(timestamp);
            viewHolder.measurement.setText(String.valueOf(mMeasurement.get(i).getMeasurement()));
            viewHolder.bodypart.setText(mMeasurement.get(i).getBodypart());
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return mMeasurement.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView measurement, bodypart, timestamp;
        OnMeasurementListener onMeasurementListener;

        public ViewHolder(@NonNull View itemView, OnMeasurementListener onMeasurementListener) {
            super(itemView);
            measurement = itemView.findViewById(R.id.tvMeasurement);
            bodypart = itemView.findViewById(R.id.tvBodypart);
            timestamp = itemView.findViewById(R.id.tvTimestamp);
            this.onMeasurementListener = onMeasurementListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMeasurementListener.onMeasurementClicked(getAdapterPosition());
        }
    }

    public interface OnMeasurementListener{
        void onMeasurementClicked(int position);
    }

}
