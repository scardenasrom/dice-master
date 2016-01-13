package com.herkiusdev.dicemaster.activity.board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    EditText playerOneScore;
    @ViewById(R.id.one_vs_one_player_two_score)
    EditText playerTwoScore;

    boolean doubleBack = false;

    @AfterViews
    public void initViews(){
        setupToolbar();
        playerOneScore.setBackgroundDrawable(null);
        playerTwoScore.setBackgroundDrawable(null);
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

        b.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color_text_card_view));
        b.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color_accent));
    }

    @Click(R.id.one_vs_one_player_one_increase_btn)
    public void increasePlayerOneScore(){
        String scoreStr = playerOneScore.getText().toString();
        if (scoreStr.isEmpty()) {
            playerOneScore.setText("1");
        } else {
            int scoreInt = Integer.parseInt(scoreStr);
            playerOneScore.setText("" + ++scoreInt);
        }
    }

    @Click(R.id.one_vs_one_player_one_decrease_btn)
    public void decreasePlayerOneScore(){
        String scoreStr = playerOneScore.getText().toString();
        if (scoreStr.isEmpty()) {
            playerOneScore.setText("-1");
        } else {
            int scoreInt = Integer.parseInt(scoreStr);
            playerOneScore.setText("" + --scoreInt);
        }
    }

    @Click(R.id.one_vs_one_player_two_increase_btn)
    public void increasePlayerTwoScore(){
        String scoreStr = playerTwoScore.getText().toString();
        if (scoreStr.isEmpty()) {
            playerTwoScore.setText("1");
        } else {
            int scoreInt = Integer.parseInt(scoreStr);
            playerTwoScore.setText("" + ++scoreInt);
        }
    }

    @Click(R.id.one_vs_one_player_two_decrease_btn)
    public void decreasePlayerTwoScore(){
        String scoreStr = playerTwoScore.getText().toString();
        if (scoreStr.isEmpty()) {
            playerTwoScore.setText("-1");
        } else {
            int scoreInt = Integer.parseInt(scoreStr);
            playerTwoScore.setText("" + --scoreInt);
        }
    }

}
