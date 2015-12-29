package com.herkiusdev.dicemaster.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_board)
public class BoardFragment extends Fragment {

    @ViewById(R.id.board_fragment_one_vs_one)
    Button exampleButton;

    @AfterViews
    public void initViews() {

    }

    @Click(R.id.board_fragment_one_vs_one)
    public void oneVsOneClick() {
        Snackbar.make(exampleButton, "Uno Contra Uno", Snackbar.LENGTH_SHORT).show();
    }

    @Click(R.id.board_fragment_several_players)
    public void severalPlayersClick() {
        Snackbar.make(exampleButton, "Varios Jugadores", Snackbar.LENGTH_SHORT).show();
    }

}
