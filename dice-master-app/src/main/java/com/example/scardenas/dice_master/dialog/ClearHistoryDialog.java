package com.example.scardenas.dice_master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;

import com.example.scardenas.dice_master.R;

public class ClearHistoryDialog extends Dialog implements View.OnClickListener {

    private AppCompatButton negativeButton;
    private AppCompatButton positiveButton;

    private ClearHistoryClickListener clearHistoryClickListener;

    public ClearHistoryDialog(Context context, ClearHistoryClickListener clearHistoryClickListener) {
        super(context);
        this.clearHistoryClickListener = clearHistoryClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_clear_history);
        negativeButton = (AppCompatButton)findViewById(R.id.clear_history_dialog_neg_button);
        positiveButton = (AppCompatButton)findViewById(R.id.clear_history_dialog_pos_button);

        negativeButton.setOnClickListener(this);
        positiveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == negativeButton) {
            clearHistoryClickListener.onNegativeButtonClick(this);
        } else if (view == positiveButton) {
            clearHistoryClickListener.onPositiveButtonClick(this);
        }
    }

    public interface ClearHistoryClickListener {
        void onNegativeButtonClick(Dialog dialog);
        void onPositiveButtonClick(Dialog dialog);
    }

}
