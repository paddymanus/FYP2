package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.Set;

import java.util.ArrayList;

public class SetRecyclerAdapter extends RecyclerView.Adapter<SetRecyclerAdapter.ViewHolder> {

    private ArrayList<Set> mSets = new ArrayList<>();

    public SetRecyclerAdapter(ArrayList<Set> mSets) {
        this.mSets = mSets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_exercise_field, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.set.setText(String.valueOf(mSets.get(i).get_setNumber()));
        viewHolder.weight.setText(String.valueOf(mSets.get(i).get_setWeight()));
        viewHolder.reps.setText(String.valueOf(mSets.get(i).get_setReps()));
    }

    @Override
    public int getItemCount() {
        return mSets.size();
    }

    public ArrayList<Set> getExerciseSets() {
        return mSets;
    }

    public void addExerciseSet(Set es) {
        mSets.add(es);
    }

    public void setExerciseSets(ArrayList<Set> exerciseSets) {
        mSets = exerciseSets;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView set;
        private EditText weight, reps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            set = itemView.findViewById(R.id.view_exercise_set_number);
            weight = itemView.findViewById(R.id.view_exercise_set_weight);
            reps = itemView.findViewById(R.id.view_exercise_set_reps);
        }
    }



}
