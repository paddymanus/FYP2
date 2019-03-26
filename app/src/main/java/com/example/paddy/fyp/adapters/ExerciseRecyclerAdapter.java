package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Exercise;

import java.util.ArrayList;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {

    private ArrayList<Exercise> mExercises = new ArrayList<>();
    private OnExerciseListener mOnExerciseListener;

    public ExerciseRecyclerAdapter(ArrayList<Exercise> mExercises, OnExerciseListener onExerciseListener) {
        this.mExercises = mExercises;
        this.mOnExerciseListener = onExerciseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row, viewGroup, false);
        return new ViewHolder(view, mOnExerciseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.category.setText(mExercises.get(i).getCategory());
        viewHolder.name.setText(mExercises.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, category;
        OnExerciseListener onExerciseListener;

        public ViewHolder(@NonNull View itemView, OnExerciseListener onExerciseListener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAddExerciseName);
            category = itemView.findViewById(R.id.tvAddExerciseCategory);
            this.onExerciseListener = onExerciseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExerciseListener.onExerciseClick(getAdapterPosition());
        }
    }

    public interface OnExerciseListener{
        void onExerciseClick(int position);
    }
}
