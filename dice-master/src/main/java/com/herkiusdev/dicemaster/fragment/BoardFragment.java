package com.herkiusdev.dicemaster.fragment;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.activity.board.OneVsOneActivity_;
import com.herkiusdev.dicemaster.activity.board.SeveralPlayersActivity_;

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
        OneVsOneActivity_.intent(getContext()).start();
    }

    @Click(R.id.board_fragment_several_players)
    public void severalPlayersClick() {
        SeveralPlayersActivity_.intent(getContext()).start();
    }

}
