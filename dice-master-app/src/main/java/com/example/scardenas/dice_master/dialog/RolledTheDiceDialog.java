package com.example.scardenas.dice_master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;

import java.util.List;
import java.util.Random;

public class RolledTheDiceDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final Roll roll;

    private LinearLayout rollDialogRootView;
    private TextView rollDialogTitle;
    private TextView resultText;
    private TextView rollResultsText;
    private AppCompatButton rollAgainButton;
    private AppCompatButton closeDialogButton;

    private RollDialogClickListener rollDialogClickListener;

    public RolledTheDiceDialog(Context context,
                               Roll roll,
                               RollDialogClickListener rollDialogClickListener) {
        super(context);
        this.context = context;
        this.roll = roll;
        this.rollDialogClickListener = rollDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_roll);

        rollDialogRootView = (LinearLayout)findViewById(R.id.roll_dialog_root_view);
        rollDialogTitle = (TextView)findViewById(R.id.roll_dialog_title_text);
        resultText = (TextView)findViewById(R.id.roll_dialog_result_text);
        rollResultsText = (TextView)findViewById(R.id.roll_dialog_roll_results_text);
        rollAgainButton = (AppCompatButton)findViewById(R.id.roll_dialog_roll_again_button);
        closeDialogButton = (AppCompatButton)findViewById(R.id.roll_dialog_close_dialog_button);

        configureDialogColors();
        if (roll.getName() != null && !TextUtils.isEmpty(roll.getName())) {
            rollDialogTitle.setText(context.getString(R.string.dialog_favourite_rolled_title, roll.getName(), roll.getRollString()));
        } else {
            rollDialogTitle.setText(roll.getRollString());
        }
        resultText.setText(roll.getRollResult());
        configureRollResults();

        if (rollDialogClickListener != null) {
            rollAgainButton.setOnClickListener(this);
        } else {
            rollAgainButton.setVisibility(View.GONE);

        }
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void configureDialogColors() {
        Random r = new Random();
        int random = r.nextInt(6) + 1;
        switch (random) {
            case 1:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
                break;
            case 2:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.purple_dark));
                break;
            case 3:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.light_blue_dark));
                break;
            case 4:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.light_green_dark));
                break;
            case 5:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.yellow_dark));
                break;
            case 6:
                rollDialogRootView.setBackgroundColor(context.getResources().getColor(R.color.grey_dark));
                break;
            default:
                break;
        }
    }

    private void configureRollResults() {
        String finalText = "";
        for (List<Integer> rollList: roll.getListOfResults()) {
            String thisRoll = "[";
            for (int i = 0; i < rollList.size(); i++) {
                if (i != rollList.size()-1) {
                    thisRoll = thisRoll + rollList.get(i) + ", ";
                } else {
                    thisRoll = thisRoll + rollList.get(i) + "] ";
                }
            }
            finalText = finalText + thisRoll;
        }
        rollResultsText.setText(finalText);
    }

    @Override
    public void onClick(View view) {
        if (view == rollAgainButton) {
            rollDialogClickListener.rollAgain(this, roll);
        }
    }

    public interface RollDialogClickListener {
        void rollAgain(Dialog dialog, Roll roll);
    }

}
