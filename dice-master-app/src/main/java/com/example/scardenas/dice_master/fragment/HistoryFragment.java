package com.example.scardenas.dice_master.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.scardenas.dice_master.BaseFragment;
import com.example.scardenas.dice_master.PreferenceManager;
import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.adapter.HistoryRollAdapter;
import com.example.scardenas.dice_master.data.Roll;
import com.example.scardenas.dice_master.dialog.ClearHistoryDialog;
import com.example.scardenas.dice_master.dialog.RolledTheDiceDialog;
import com.example.scardenas.dice_master.util.RollParser;
import com.example.scardenas.dice_master.util.SnackbarUtils;
import com.example.scardenas.dice_master.view.DividerItemDecoration;
import com.example.scardenas.dice_master.view.HistoryRollView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@EFragment(R.layout.fragment_history)
public class HistoryFragment extends BaseFragment {

    @DrawableRes(R.drawable.dark_grey_line) Drawable darkGreyLine;

    @ViewById(R.id.history_recycler_view) RecyclerView recyclerView;
    @ViewById(R.id.history_clear_history_button) AppCompatButton buttonClearHistory;
    @ViewById(R.id.history_empty_layout) RelativeLayout emptyListLayout;

    private PreferenceManager preferenceManager;
    private RollParser rollParser;
    private SnackbarUtils snackbarUtils;

    private List<Roll> rollList;
    private HistoryRollAdapter historyRollAdapter;

    private HistoryEmptyListLayoutClickListener historyEmptyListLayoutClickListener;

    private Comparator<Roll> timeOfRollComparator = new Comparator<Roll>() {
        @Override
        public int compare(Roll roll1, Roll roll2) {
            if (roll1.getTimeOfRoll() - roll2.getTimeOfRoll() < 0) {
                return 1;
            } else if (roll1.getTimeOfRoll() - roll2.getTimeOfRoll() > 0) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    @AfterViews
    public void initialize() {
        historyEmptyListLayoutClickListener = (HistoryEmptyListLayoutClickListener)getActivity();
        preferenceManager = new PreferenceManager(getContext());
        rollParser = RollParser.getInstance();
        snackbarUtils = new SnackbarUtils(getContext());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        rollList = preferenceManager.getHistoryRolls();
        Collections.sort(rollList, timeOfRollComparator);
        historyRollAdapter = new HistoryRollAdapter(getContext(), rollList, new HistoryRollView.OnHistoryRollClickListener() {
            @Override
            public void onHistoryRollClick(Roll roll) {
                showNewRollDialog(roll);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(darkGreyLine, true, true));
        recyclerView.setAdapter(historyRollAdapter);
        checkViewState();
    }

    public void reloadListAndRecyclerView() {
        rollList = preferenceManager.getHistoryRolls();
        Collections.sort(rollList, timeOfRollComparator);
        historyRollAdapter.setRollList(rollList);
        historyRollAdapter.notifyDataSetChanged();
        checkViewState();
    }

    private void checkViewState() {
        if (rollList.size() == 0) {
            makeViewDisappear(getContext(), recyclerView);
            makeViewDisappear(getContext(), buttonClearHistory);
            makeViewAppear(getContext(), emptyListLayout);
        } else {
            makeViewAppear(getContext(), recyclerView);
            makeViewAppear(getContext(), buttonClearHistory);
            makeViewDisappear(getContext(), emptyListLayout);
        }
    }

    private void showNewRollDialog(Roll roll) {
        RolledTheDiceDialog rolledTheDiceDialog = new RolledTheDiceDialog(getContext(), roll, new RolledTheDiceDialog.RollDialogClickListener() {
            @Override
            public void rollAgain(Dialog dialog, Roll roll) {
                dialog.dismiss();
                String name = roll.getName();
                String rollString = roll.getRollString();
                Roll newRoll = rollParser.parseExpression(rollString);
                newRoll.setName(name);
                newRoll.setTimeOfRoll(System.currentTimeMillis());
                List<Roll> historyRolls = preferenceManager.getHistoryRolls();
                historyRolls.add(newRoll);
                preferenceManager.setHistoryRolls(historyRolls);
                rollList.add(0, newRoll);
                historyRollAdapter.setRollList(rollList);
                historyRollAdapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
                showNewRollDialog(newRoll);
            }
        });
        rolledTheDiceDialog.show();
    }

    @Click(R.id.history_clear_history_button)
    public void clearHistoryClick() {
        ClearHistoryDialog clearHistoryDialog = new ClearHistoryDialog(getContext(), new ClearHistoryDialog.ClearHistoryClickListener() {
            @Override
            public void onNegativeButtonClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(Dialog dialog) {
                dialog.dismiss();
                rollList = new ArrayList<>();
                historyRollAdapter.setRollList(rollList);
                historyRollAdapter.notifyDataSetChanged();
                preferenceManager.setHistoryRolls(new ArrayList<Roll>());
                makeViewDisappear(getContext(), recyclerView);
                makeViewDisappear(getContext(), buttonClearHistory);
                makeViewAppear(getContext(), emptyListLayout);
                snackbarUtils.createInfoSnackbar(recyclerView, R.string.history_empty_list_snackbar).show();
            }
        });
        clearHistoryDialog.show();
    }

    @Click(R.id.history_empty_list_button)
    public void onEmptyListLayoutClick() {
        historyEmptyListLayoutClickListener.onHistoryEmptyListLayoutClick();
    }

    public interface HistoryEmptyListLayoutClickListener {
        void onHistoryEmptyListLayoutClick();
    }

}
