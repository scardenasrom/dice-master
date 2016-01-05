package com.herkiusdev.dicemaster.activity.board;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.dialog.EditTextDialog;

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

    @AfterViews
    public void initViews(){
        setupToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.one_vs_one_name));
    }

    @Click(R.id.one_vs_one_toolbar_back)
    public void goBack(){
        finish();
    }

    @Click(R.id.one_vs_one_player_one_name)
    public void changePlayerOneName() {
        final EditTextDialog playerOneDialog = new EditTextDialog(OneVsOneActivity.this);
        playerOneDialog.setTitle(R.string.one_vs_one_player_one_dialog_title);
        playerOneDialog.setText(R.string.one_vs_one_player_one_dialog_text);
        playerOneDialog.setNegativeButtonText(R.string.one_vs_one_player_one_neg_button);
        playerOneDialog.setPositiveButtonText(R.string.one_vs_one_player_one_pos_button);
        playerOneDialog.negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneDialog.dismiss();
            }
        });
        playerOneDialog.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneDialog.dismiss();
                String newName = playerOneDialog.getInputText();
                if (newName.isEmpty()) {
                    playerOneName.setText(getText(R.string.one_vs_one_player_one_name));
                } else {
                    playerOneName.setText(newName);
                }
            }
        });
        playerOneDialog.show();
    }

//    @Click(R.id.one_vs_one_player_two_name)
//    public void changePlayerTwoName() {
//        final EditTextDialog playerTwoDialog = new EditTextDialog(OneVsOneActivity.this);
//        playerTwoDialog.setTitle(R.string.one_vs_one_player_two_dialog_title);
//        playerTwoDialog.setText(R.string.one_vs_one_player_two_dialog_text);
//        playerTwoDialog.setNegativeButtonText(R.string.one_vs_one_player_two_neg_button);
//    }

}
