package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.StatsHome;
import com.example.paddy.fyp.models.StatsOptions;

import java.util.ArrayList;

public class StatsOptionsRecyclerAdapter extends RecyclerView.Adapter<StatsOptionsRecyclerAdapter.ViewHolder> {

    private ArrayList<StatsOptions> mStatsOptions = new ArrayList<>();
    private OnStatsOptionsListener mOnStatsOptionsListener;

    public StatsOptionsRecyclerAdapter(ArrayList<StatsOptions> mStatsOptions, OnStatsOptionsListener onStatsOptionsListener) {
        this.mStatsOptions = mStatsOptions;
        this.mOnStatsOptionsListener = onStatsOptionsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_stats_home_item, viewGroup, false);
        return new ViewHolder(view, mOnStatsOptionsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.title.setText(mStatsOptions.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mStatsOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        OnStatsOptionsListener onStatsOptionsListener;

        public ViewHolder(@NonNull View itemView, OnStatsOptionsListener onStatsOptionsListener) {
            super(itemView);
            title = itemView.findViewById(R.id.tvStatsHome);
            this.onStatsOptionsListener = onStatsOptionsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStatsOptionsListener.onStatClicked(getAdapterPosition());
        }
    }

    public interface OnStatsOptionsListener{
        void onStatClicked(int position);
    }

}
