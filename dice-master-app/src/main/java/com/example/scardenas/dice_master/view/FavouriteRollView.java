package com.example.scardenas.dice_master.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;

public class FavouriteRollView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;

    private Roll roll;

    private RelativeLayout rollDeleteButton;
    private LinearLayout rollRootView;
    private TextView rollNameTextView;
    private TextView rollTextView;

    private OnFavouriteRollClickListener onFavouriteRollClickListener;

    public FavouriteRollView(View itemView, Context context, OnFavouriteRollClickListener onFavouriteRollClickListener) {
        super(itemView);
        this.context = context;
        this.onFavouriteRollClickListener = onFavouriteRollClickListener;

        rollDeleteButton = (RelativeLayout)itemView.findViewById(R.id.favourite_roll_view_delete_button);
        rollRootView = (LinearLayout)itemView.findViewById(R.id.favourite_roll_view_root_view);
        rollNameTextView = (TextView)itemView.findViewById(R.id.favourite_roll_view_name);
        rollTextView = (TextView)itemView.findViewById(R.id.favourite_roll_view_roll);

        rollDeleteButton.setOnClickListener(this);
        rollRootView.setOnClickListener(this);
    }

    public Roll getRoll() {
        return roll;
    }

    public void bind(Roll roll) {
        this.roll = roll;
        rollNameTextView.setText(roll.getName());
        rollTextView.setText(roll.getRollString());
    }

    @Override
    public void onClick(View view) {
        if (view == rollRootView) {
            onFavouriteRollClickListener.onFavouriteRollClick(roll);
        } else if (view == rollDeleteButton) {
            onFavouriteRollClickListener.onDeleteRollClick(roll);
        }
    }

    public interface OnFavouriteRollClickListener {
        void onFavouriteRollClick(Roll roll);
        void onDeleteRollClick(Roll roll);
    }

}
