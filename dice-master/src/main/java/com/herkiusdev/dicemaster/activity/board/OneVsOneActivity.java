package com.herkiusdev.dicemaster.activity.board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_one_vs_one)
public class OneVsOneActivity extends AppCompatActivity {

    @ViewById(R.id.one_vs_one_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.one_vs_one_toolbar_title)
    TextView toolbarTitle;
    @ViewById(R.id.one_vs_one_player_one_name)
    TextView playerOneName;
    @ViewById(R.id.one_vs_one_player_two_name)
    TextView playerTwoName;
    @ViewById(R.id.one_vs_one_player_one_score)
    TextView playerOneScore;
    @ViewById(R.id.one_vs_one_player_two_score)
    TextView playerTwoScore;

    boolean doubleBack = false;

    @AfterViews
    public void initViews(){
        setupToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Snackbar.make(toolbar, R.string.several_players_go_back, Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.one_vs_one_name));
    }

    @Click(R.id.one_vs_one_toolbar_back)
    public void goBack(){
        onBackPressed();
    }

    @Click(R.id.one_vs_one_player_one_name)
    public void changePlayerOneName(){
        showPlayerNameChangeDialog(true);
    }

    @Click(R.id.one_vs_one_player_two_name)
    public void changePlayerTwoName(){
        showPlayerNameChangeDialog(false);
    }

    @Click(R.id.one_vs_one_player_one_score)
    public void changePlayerOneScore(){
        showPlayerScoreChangeDialog(true);
    }

    @Click(R.id.one_vs_one_player_two_score)
    public void changePlayerTwoScore(){
        showPlayerScoreChangeDialog(false);
    }

    private void showPlayerNameChangeDialog(final boolean isPlayerOne) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_edit_text_edit);

        String title;
        if (isPlayerOne) {
            title = getText(R.string.one_vs_one_player_one_dialog_title).toString();
        } else {
            title = getText(R.string.one_vs_one_player_two_dialog_title).toString();
        }
        String message;
        if (isPlayerOne) {
            message = getText(R.string.one_vs_one_player_one_dialog_text).toString();
        } else {
            message = getText(R.string.one_vs_one_player_two_dialog_text).toString();
        }

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(R.string.one_vs_one_player_one_pos_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newName = edt.getText().toString();
                if (newName.isEmpty()) {
                    if (isPlayerOne) {
                        playerOneName.setText(getText(R.string.one_vs_one_player_one_name));
                    } else {
                        playerTwoName.setText(getText(R.string.one_vs_one_player_two_name));
                    }
                } else {
                    if (isPlayerOne) {
                        playerOneName.setText(newName);
                    } else {
                        playerTwoName.setText(newName);
                    }
                }
            }
        });
        dialogBuilder.setNegativeButton(R.string.one_vs_one_player_one_neg_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

        Button negBtn = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(negBtn != null)
            negBtn.setTextColor(getResources().getColor(R.color.color_text_card_view));

        Button posBtn = b.getButton(DialogInterface.BUTTON_POSITIVE);
        if (posBtn != null)
            posBtn.setTextColor(getResources().getColor(R.color.color_accent));
    }

    private void showPlayerScoreChangeDialog(final boolean isPlayerOne) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_number_edit_text, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_edit_text_edit);

        String title;
        if (isPlayerOne) {
            title = getText(R.string.one_vs_one_player_one_score_dialog_title).toString();
        } else {
            title = getText(R.string.one_vs_one_player_two_score_dialog_title).toString();
        }
        String message;
        if (isPlayerOne) {
            message = getText(R.string.one_vs_one_player_one_score_dialog_text).toString();
        } else {
            message = getText(R.string.one_vs_one_player_two_score_dialog_text).toString();
        }

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(getText(R.string.one_vs_one_player_one_pos_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newScore = edt.getText().toString();
                if (!newScore.isEmpty()) {
                    if (isPlayerOne) {
                        playerOneScore.setText(newScore);
                    } else {
                        playerTwoScore.setText(newScore);
                    }
                }
            }
        });
        dialogBuilder.setNegativeButton(getText(R.string.one_vs_one_player_one_neg_button).toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

        Button negBtn = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negBtn != null)
            negBtn.setTextColor(getResources().getColor(R.color.color_text_card_view));

        Button posBtn = b.getButton(DialogInterface.BUTTON_POSITIVE);
        if (posBtn != null)
            posBtn.setTextColor(getResources().getColor(R.color.color_accent));
    }

    @Click(R.id.one_vs_one_player_one_increase_btn)
    public void increasePlayerOneScore(){
        int scoreInt = Integer.parseInt(playerOneScore.getText().toString());
        playerOneScore.setText("" + ++scoreInt);
    }

    @Click(R.id.one_vs_one_player_one_decrease_btn)
    public void decreasePlayerOneScore(){
        int scoreInt = Integer.parseInt(playerOneScore.getText().toString());
        playerOneScore.setText("" + --scoreInt);
    }

    @Click(R.id.one_vs_one_player_two_increase_btn)
    public void increasePlayerTwoScore(){
        int scoreInt = Integer.parseInt(playerTwoScore.getText().toString());
        playerTwoScore.setText("" + ++scoreInt);
    }

    @Click(R.id.one_vs_one_player_two_decrease_btn)
    public void decreasePlayerTwoScore(){
        int scoreInt = Integer.parseInt(playerTwoScore.getText().toString());
        playerTwoScore.setText("" + --scoreInt);
    }

}
