package com.example.scardenas.dice_master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;
import com.example.scardenas.dice_master.view.HistoryRollView;

import java.util.List;

public class HistoryRollAdapter extends RecyclerView.Adapter<HistoryRollView> {

    private Context context;
    private List<Roll> rollList;
    private HistoryRollView.OnHistoryRollClickListener onHistoryRollClickListener;

    public HistoryRollAdapter(Context context, List<Roll> rollList, HistoryRollView.OnHistoryRollClickListener onHistoryRollClickListener) {
        this.context = context;
        this.rollList = rollList;
        this.onHistoryRollClickListener = onHistoryRollClickListener;
    }

    public void setRollList(List<Roll> rollList) {
        this.rollList = rollList;
    }

    @Override
    public HistoryRollView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryRollView(LayoutInflater.from(context).inflate(R.layout.view_history_roll, parent, false), context, onHistoryRollClickListener);
    }

    @Override
    public void onBindViewHolder(HistoryRollView holder, int position) {
        holder.bind(rollList.get(position));
    }

    @Override
    public int getItemCount() {
        return rollList.size();
    }
}
