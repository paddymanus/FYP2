package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.ExerciseActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;

public class ExerciseSetRecyclerAdapter extends RecyclerView.Adapter<ExerciseSetRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ExerciseSetRecyclerAdap";

    private ArrayList<ExerciseSet> mSets = new ArrayList<>();
    private onExerciseSetListener mOnExerciseSetListener;

    public ExerciseSetRecyclerAdapter(ArrayList<ExerciseSet> mSets, onExerciseSetListener onExerciseSetListener) {
        this.mSets = mSets;
        this.mOnExerciseSetListener = onExerciseSetListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_exerciseset, viewGroup, false);
        return new ViewHolder(view, mOnExerciseSetListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            String month = mSets.get(i).getTimestamp().substring(3);
            month = UtilityDate.getMonthFromNumber(month);
            String date = mSets.get(i).getTimestamp().substring(0, 2);
            String timestamp = date + " " + month;
            viewHolder.timestamp.setText(timestamp);
            viewHolder.exerciseTitle.setText(mSets.get(i).getName());
            viewHolder.sets.setText(mSets.get(i).getParameters());
            viewHolder.volume.setText(String.valueOf(mSets.get(i).getVolume()));
            viewHolder.onerepmax.setText(String.valueOf(mSets.get(i).getOnerepmax()));
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: " + e.getMessage() );
        }
    //    viewHolder.weights.setText(String.valueOf(mSets.get(i).getWeight()));
   //     viewHolder.reps.setText(String.valueOf(mSets.get(i).getReps()));
    }


    @Override
    public int getItemCount() {
        return mSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView exerciseTitle, sets, volume, onerepmax, timestamp;
        onExerciseSetListener onExerciseSetListener;

        public ViewHolder(@NonNull View itemView, onExerciseSetListener onExerciseSetListener1) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.title_exercise_name);
            sets = itemView.findViewById(R.id.title_sets);
            volume = itemView.findViewById(R.id.title_volume);
            onerepmax = itemView.findViewById(R.id.title_1rm);
            timestamp = itemView.findViewById(R.id.set_timestamp);
            this.onExerciseSetListener = onExerciseSetListener1;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExerciseSetListener.onExerciseSetClick(getAdapterPosition());
        }
    }

    public interface onExerciseSetListener{
        void onExerciseSetClick(int position);
    }
}
