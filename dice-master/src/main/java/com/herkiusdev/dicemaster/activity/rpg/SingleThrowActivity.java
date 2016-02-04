package com.herkiusdev.dicemaster.activity.rpg;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.service.AnimatorService;
import com.ogaclejapan.arclayout.ArcLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_single_throw)
public class SingleThrowActivity extends AppCompatActivity {

    @ViewById(R.id.single_throw_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.single_throw_toolbar_title)
    TextView toolbarTitle;

    @ViewById(R.id.single_throw_dice_button)
    ImageButton singleThrowButton;
    @ViewById(R.id.single_throw_dice_menu)
    View diceMenu;
    @ViewById(R.id.single_throw_dice_arc)
    ArcLayout diceArc;

    @ViewById(R.id.single_throw_sound_button)
    Button soundButton;
    @ViewById(R.id.single_throw_result_text)
    TextView throwResult;

    private AnimatorService animService;

    @AfterViews
    public void initViews(){
        setupToolbar();
        soundButton.setSelected(false);
        animService = AnimatorService.getInstance();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.single_throw_name));
    }

    @Click(R.id.single_throw_toolbar_back)
    public void goBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        if (singleThrowButton.isSelected()) {
            animService.hideMenu(diceMenu, diceArc, singleThrowButton);
            singleThrowButton.setSelected(!singleThrowButton.isSelected());
        } else {
            finish();
        }
    }

    @Click(R.id.single_throw_sound_button)
    public void switchSound() {
        soundButton.cancelLongPress();
        if (!soundButton.isSelected()) {
            soundButton.setBackgroundResource(R.drawable.button_sound_on);
        } else {
            soundButton.setBackgroundResource(R.drawable.button_sound_off);
        }
        soundButton.setSelected(!soundButton.isSelected());
    }

    @Click(R.id.single_throw_dice_button)
    public void singleThrowClick() {
        if (singleThrowButton.isSelected()) {
            animService.hideMenu(diceMenu, diceArc, singleThrowButton);
        } else {
            animService.showMenu(diceMenu, diceArc, singleThrowButton);
        }
        singleThrowButton.setSelected(!singleThrowButton.isSelected());
    }

    @Click(R.id.single_throw_d2)
    public void throw2Dice() {
        throwDice(2);
    }

    @Click(R.id.single_throw_d3)
    public void throw3Dice() {
        throwDice(3);
    }

    @Click(R.id.single_throw_d4)
    public void throw4Dice() {
        throwDice(4);
    }

    @Click(R.id.single_throw_d6)
    public void throw6Dice() {
        throwDice(6);
    }

    @Click(R.id.single_throw_d8)
    public void throw8Dice() {
        throwDice(8);
    }

    @Click(R.id.single_throw_d10)
    public void throw10Dice() {
        throwDice(10);
    }

    @Click(R.id.single_throw_d12)
    public void throw12Dice() {
        throwDice(12);
    }

    @Click(R.id.single_throw_d20)
    public void throw20Dice() {
        throwDice(20);
    }

    @Click(R.id.single_throw_d100)
    public void throw100Dice() {
        throwDice(100);
    }

    protected void throwDice(int sides) {
        if (soundButton.isSelected()) {
            MediaPlayer.create(SingleThrowActivity.this, R.raw.dice_roll).start();
        }
        int resultInt = ThreadLocalRandom.current().nextInt(1, sides + 1);
        String result = getString(R.string.single_throw_result, "" + resultInt);
        throwResult.setText(result);
    }

}