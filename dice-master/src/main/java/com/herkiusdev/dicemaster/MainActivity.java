package com.herkiusdev.dicemaster;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.herkiusdev.dicemaster.activity.ScoresActivity_;
import com.herkiusdev.dicemaster.activity.SingleThrowActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    public void initViews() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Click(R.id.main_screen_single_throw_box)
    public void singleThrowClick() {
        SingleThrowActivity_.intent(this).start();
    }

    @Click(R.id.main_screen_multiple_throws_box)
    public void multipleThrowClick() {

    }

    @Click(R.id.main_screen_scores_box)
    public void scoresClick() {
        ScoresActivity_.intent(this).start();
    }

}
