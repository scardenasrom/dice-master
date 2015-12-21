package com.herkiusdev.dicemaster.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_single_throw)
public class SingleThrowActivity extends AppCompatActivity{

    @AfterViews
    public void initViews() {
        getSupportActionBar().setTitle("Dice Master - Tirada Única");
        getSupportActionBar().setElevation(0);
    }

    @Click(R.id.single_throw_d20_btn)
    public void d20IconClick(){
        Toast.makeText(SingleThrowActivity.this, "Dice pressed!", Toast.LENGTH_SHORT).show();
    }

}
