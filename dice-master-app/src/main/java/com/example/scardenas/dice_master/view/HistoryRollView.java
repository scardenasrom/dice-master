package com.example.scardenas.dice_master.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;

public class HistoryRollView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;

    private Roll roll;

    private RelativeLayout rollViewRootView;
    private TextView rollNameTextView;
    private TextView rollStringTextView;
    private TextView rollResultTextView;

    private OnHistoryRollClickListener onHistoryRollClickListener;

    public HistoryRollView(View itemView, Context context, OnHistoryRollClickListener onHistoryRollClickListener) {
        super(itemView);
        this.context = context;
        this.onHistoryRollClickListener = onHistoryRollClickListener;

        rollViewRootView = (RelativeLayout)itemView.findViewById(R.id.history_roll_root_view);
        rollNameTextView = (TextView)itemView.findViewById(R.id.history_roll_name);
        rollStringTextView = (TextView)itemView.findViewById(R.id.history_roll_roll_string);
        rollResultTextView = (TextView)itemView.findViewById(R.id.history_roll_result);

        rollViewRootView.setOnClickListener(this);
    }

    public Roll getRoll() {
        return roll;
    }

    public void bind(Roll roll) {
        this.roll = roll;
        if (roll.getName() != null) {
            rollNameTextView.setText(roll.getName());
        }
        rollStringTextView.setText(roll.getRollString());
        rollResultTextView.setText(roll.getRollResult());
    }

    @Override
    public void onClick(View view) {
        if (view == rollViewRootView) {
            onHistoryRollClickListener.onHistoryRollClick(roll);
        }
    }

    public interface OnHistoryRollClickListener {
        void onHistoryRollClick(Roll roll);
    }

}
