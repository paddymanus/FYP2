package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.StatsHome;

import java.util.ArrayList;

public class StatsHomeRecyclerAdapter extends RecyclerView.Adapter<StatsHomeRecyclerAdapter.ViewHolder> {

    private ArrayList<StatsHome> mStatsHome = new ArrayList<>();

    public StatsHomeRecyclerAdapter(ArrayList<StatsHome> mStatsHome) {
        this.mStatsHome = mStatsHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_stats_home_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.title.setText(mStatsHome.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mStatsHome.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvStatsHome);
        }
    }

}
