package com.herkiusdev.dicemaster.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_rpg)
public class RPGFragment extends Fragment {

    @ViewById(R.id.rpg_fragment_single_throw)
    Button exampleButton;

    @AfterViews
    public void initViews() {

    }

    @Click(R.id.rpg_fragment_single_throw)
    public void singleThrowClick() {
        Snackbar.make(exampleButton, "Tirada Única", Snackbar.LENGTH_SHORT).show();
    }

    @Click(R.id.rpg_fragment_multiple_throws)
    public void multipleThrowsClick() {
        Snackbar.make(exampleButton, "Múltiples Tiradas", Snackbar.LENGTH_SHORT).show();
    }

    @Click(R.id.rpg_fragment_initiative)
    public void initiativeClick() {
        Snackbar.make(exampleButton, "Tabla de Iniciativa", Snackbar.LENGTH_SHORT).show();
    }

}
