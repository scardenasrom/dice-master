package com.herkiusdev.dicemaster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_one_vs_one)
public class OneVsOneActivity extends AppCompatActivity{

    @AfterViews
    public void initViews() {
        getSupportActionBar().setTitle("Dice Master - Uno Contra Uno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
