package com.herkiusdev.dicemaster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_scores)
public class ScoresActivity extends AppCompatActivity {

    @AfterViews
    public void initViews() {
        getSupportActionBar().setTitle("Dice Master - Puntuaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OptionsItem(android.R.id.home)
    public void goBack() {
        finish();
    }

    @Click(R.id.scores_one_vs_one_box)
    public void oneVsOneClick() {
        OneVsOneActivity_.intent(this).start();
    }

}
