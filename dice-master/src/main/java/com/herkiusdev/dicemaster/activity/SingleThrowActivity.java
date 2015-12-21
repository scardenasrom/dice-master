package com.herkiusdev.dicemaster.activity;

import android.support.v7.app.AppCompatActivity;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_single_throw)
public class SingleThrowActivity extends AppCompatActivity{

    @AfterViews
    public void initViews() {
        getSupportActionBar().setTitle("Dice Master - Tirada Única");
        getSupportActionBar().setElevation(0);
    }

}
