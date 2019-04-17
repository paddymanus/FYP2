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
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;

public class BodyweightRecyclerAdapter extends RecyclerView.Adapter<BodyweightRecyclerAdapter.ViewHolder> {

    private static final String TAG = "BodyweightRecyclerAdapt";

    private ArrayList<Bodyweight> mBodyweight = new ArrayList<>();
    private OnBodyweightListener mOnBodyweightListener;

    public BodyweightRecyclerAdapter(ArrayList<Bodyweight> mBodyweight, OnBodyweightListener onBodyweightlistener) {
        this.mBodyweight = mBodyweight;
        this.mOnBodyweightListener = onBodyweightlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bodyweight_item, viewGroup, false);
        return new ViewHolder(view, mOnBodyweightListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            String month = mBodyweight.get(i).getTimestamp().substring(3);
            month = UtilityDate.getMonthFromNumber(month);
            String date = mBodyweight.get(i).getTimestamp().substring(0, 2);
            String timestamp = date + "\n" + month;
            viewHolder.timestamp.setText(timestamp);
            viewHolder.weight.setText(String.valueOf(mBodyweight.get(i).getWeight()));
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return mBodyweight.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView weight, timestamp;
        OnBodyweightListener onBodyweightListener;

        public ViewHolder(@NonNull View itemView, OnBodyweightListener onBodyweightListener) {
            super(itemView);
            weight = itemView.findViewById(R.id.tvWeight);
            timestamp = itemView.findViewById(R.id.tvTimestamp);
            this.onBodyweightListener = onBodyweightListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBodyweightListener.onBodyweightClicked(getAdapterPosition());
        }
    }

    public interface OnBodyweightListener{
        void onBodyweightClicked(int position);
    }

}
