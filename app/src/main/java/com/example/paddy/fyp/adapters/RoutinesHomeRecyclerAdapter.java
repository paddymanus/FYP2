package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.models.StatsHome;

import java.util.ArrayList;

public class RoutinesHomeRecyclerAdapter extends RecyclerView.Adapter<RoutinesHomeRecyclerAdapter.ViewHolder> {

    private ArrayList<RoutineHome> mRoutineHome = new ArrayList<>();
    private OnRoutineHomeListener mOnRoutineHomeListener;

    public RoutinesHomeRecyclerAdapter(ArrayList<RoutineHome> mRoutineHome, OnRoutineHomeListener onRoutineHomeListener) {
        this.mRoutineHome = mRoutineHome;
        this.mOnRoutineHomeListener = onRoutineHomeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_routine_home_item, viewGroup, false);
        return new ViewHolder(view, mOnRoutineHomeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.title.setText(mRoutineHome.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mRoutineHome.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        OnRoutineHomeListener onRoutineHomeListener;

        public ViewHolder(@NonNull View itemView, OnRoutineHomeListener onRoutineHomeListener) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRoutineHome);
            this.onRoutineHomeListener = onRoutineHomeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRoutineHomeListener.onRoutineClicked(getAdapterPosition());
        }
    }

    public interface OnRoutineHomeListener{
        void onRoutineClicked(int position);
    }

}
