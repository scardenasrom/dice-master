package com.example.scardenas.dice_master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;

import com.example.scardenas.dice_master.R;

public class SaveFavouriteRollDialog extends Dialog implements View.OnClickListener {

    private final String rollString;

    private TextInputEditText nameEditText;
    private TextInputEditText rollEditText;
    private AppCompatButton negativeButton;
    private AppCompatButton positiveButton;

    private SaveFavouriteRollClickListener saveFavouriteRollClickListener;

    public SaveFavouriteRollDialog(Context context,
                                   int themeResId,
                                   String rollString,
                                   SaveFavouriteRollClickListener saveFavouriteRollClickListener) {
        super(context, themeResId);
        this.rollString = rollString;
        this.saveFavouriteRollClickListener = saveFavouriteRollClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_save_favourite_roll);

        nameEditText = (TextInputEditText)findViewById(R.id.dialog_save_favourite_roll_name_edit_text);
        rollEditText = (TextInputEditText)findViewById(R.id.dialog_save_favourite_roll_edit_text);

        rollEditText.setText(rollString);

        negativeButton = (AppCompatButton)findViewById(R.id.dialog_save_favourite_roll_neg_btn);
        positiveButton = (AppCompatButton)findViewById(R.id.dialog_save_favourite_roll_pos_btn);

        negativeButton.setOnClickListener(this);
        positiveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == negativeButton) {
            saveFavouriteRollClickListener.onNegativeButtonClick(this);
        } else if (view == positiveButton) {
            saveFavouriteRollClickListener.onPositiveButtonClick(this, nameEditText.getText().toString());
        }
    }

    public interface SaveFavouriteRollClickListener {
        void onNegativeButtonClick(Dialog dialog);
        void onPositiveButtonClick(Dialog dialog, String rollName);
    }

}
