package com.example.paddy.fyp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.models.LogItem;

import java.util.ArrayList;

public class LogItemRecyclerAdapter extends RecyclerView.Adapter<LogItemRecyclerAdapter.ViewHolder> {

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
        viewHolder.title.setText(mLogItems.get(i).getTitle());
        viewHolder.content.setText(mLogItems.get(i).getContent());
        viewHolder.timestamp.setText(mLogItems.get(i).getTimestamp());
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
