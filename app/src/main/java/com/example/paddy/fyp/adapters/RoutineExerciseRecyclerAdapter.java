package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.RoutineExercise;

import java.util.ArrayList;

public class RoutineExerciseRecyclerAdapter extends RecyclerView.Adapter<RoutineExerciseRecyclerAdapter.ViewHolder> {

    private ArrayList<RoutineExercise> mRoutineExercises = new ArrayList<>();
    private OnRoutineExerciseListener mOnRoutineExerciseListener;

    public RoutineExerciseRecyclerAdapter(ArrayList<RoutineExercise> mRoutineExercises, OnRoutineExerciseListener onRoutineExerciseListener) {
        this.mRoutineExercises = mRoutineExercises;
        this.mOnRoutineExerciseListener = onRoutineExerciseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row, viewGroup, false);
        return new ViewHolder(view, mOnRoutineExerciseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.category.setText(mRoutineExercises.get(i).getCategory());
        viewHolder.name.setText(mRoutineExercises.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return mRoutineExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, category;
        OnRoutineExerciseListener onRoutineExerciseListener;

        public ViewHolder(@NonNull View itemView, OnRoutineExerciseListener onRoutineExerciseListener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAddExerciseName);
            category = itemView.findViewById(R.id.tvAddExerciseCategory);
            this.onRoutineExerciseListener = onRoutineExerciseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRoutineExerciseListener.onRoutineExerciseClick(getAdapterPosition());
        }
    }

    public interface OnRoutineExerciseListener{
        void onRoutineExerciseClick(int position);
    }
}
