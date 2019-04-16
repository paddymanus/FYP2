package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.utils.UtilityDate;

import java.util.ArrayList;

public class LogItemRecyclerAdapter extends RecyclerView.Adapter<LogItemRecyclerAdapter.ViewHolder> {

    private static final String TAG = "LogItemRecyclerAdapter";

    private ArrayList<LogItem> mLogItems = new ArrayList<>();
    private OnLogItemListener mOnLogItemListener;

    public LogItemRecyclerAdapter(ArrayList<LogItem> mLogItems, OnLogItemListener onLogItemListener) {
        this.mLogItems = mLogItems;
        this.mOnLogItemListener = onLogItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_log_list_item, viewGroup, false);
        return new ViewHolder(view, mOnLogItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            String month = mLogItems.get(i).getTimestamp().substring(3);
            month = UtilityDate.getMonthFromNumber(month);
            String date = mLogItems.get(i).getTimestamp().substring(0, 2);
            String timestamp = date + "\n" + month;
            viewHolder.timestamp.setText(timestamp);
            viewHolder.title.setText(mLogItems.get(i).getTitle());
            viewHolder.content.setText(mLogItems.get(i).getContent());
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return mLogItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, content, timestamp;
        OnLogItemListener onLogItemListener;

        public ViewHolder(@NonNull View itemView, OnLogItemListener onLogItemListener) {
            super(itemView);
            title = itemView.findViewById(R.id.log_title);
            content = itemView.findViewById(R.id.log_content);
            timestamp = itemView.findViewById(R.id.log_timestamp);
            this.onLogItemListener = onLogItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLogItemListener.onLogItemClick(getAdapterPosition());
        }

    }


    public interface OnLogItemListener{
        void onLogItemClick(int position);
    }
}
