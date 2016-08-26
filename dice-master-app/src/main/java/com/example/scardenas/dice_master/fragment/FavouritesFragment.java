package com.example.scardenas.dice_master.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.scardenas.dice_master.BaseFragment;
import com.example.scardenas.dice_master.PreferenceManager;
import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.adapter.FavouriteRollAdapter;
import com.example.scardenas.dice_master.data.Roll;
import com.example.scardenas.dice_master.dialog.RolledTheDiceDialog;
import com.example.scardenas.dice_master.util.RollParser;
import com.example.scardenas.dice_master.util.SnackbarUtils;
import com.example.scardenas.dice_master.view.DividerItemDecoration;
import com.example.scardenas.dice_master.view.FavouriteRollView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

import java.util.Collections;
import java.util.List;

@EFragment(R.layout.fragment_favourites)
public class FavouritesFragment extends BaseFragment {

    @DrawableRes(R.drawable.dark_grey_line) Drawable darkGreyLine;

    @ViewById(R.id.favourites_recycler_view) RecyclerView recyclerView;
    @ViewById(R.id.favourites_empty_layout) RelativeLayout emptyListLayout;

    private PreferenceManager preferenceManager;
    private SnackbarUtils snackbarUtils;
    private Snackbar snackbarUndo;
    private RollParser rollParser;

    private List<Roll> rollList;
    private FavouriteRollAdapter favouriteRollAdapter;

    private FavouritesRollListener favouritesRollListener;

    @AfterViews
    public void initialize() {
        rollParser = RollParser.getInstance();
        favouritesRollListener = (FavouritesRollListener)getActivity();
        preferenceManager = new PreferenceManager(getContext());
        snackbarUtils = new SnackbarUtils(getContext());
        rollList = preferenceManager.getFavouriteRolls();
        Collections.sort(rollList);
        favouriteRollAdapter = new FavouriteRollAdapter(getContext(), rollList, new FavouriteRollView.OnFavouriteRollClickListener() {
            @Override
            public void onFavouriteRollClick(Roll roll) {
                showFavouriteRollDialog(roll);
            }

            @Override
            public void onDeleteRollClick(final Roll roll) {
                deleteFavouiteRoll(roll);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(darkGreyLine, true, true));
        recyclerView.setAdapter(favouriteRollAdapter);
        checkViewState();
    }

    private void deleteFavouiteRoll(final Roll roll) {
        int position = rollList.indexOf(roll);
        rollList.remove(position);
        preferenceManager.setFavouriteRolls(rollList);
        recyclerView.getAdapter().notifyItemRemoved(position);
        snackbarUndo = snackbarUtils.createInfoSnackbarWithUndoAction(recyclerView,
                getString(R.string.favourites_undo_snackbar_text, roll.getName()),
                getString(R.string.favourites_undo_snackbar_action),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarUndo.dismiss();
                        rollList.add(roll);
                        Collections.sort(rollList);
                        int position = rollList.indexOf(roll);
                        preferenceManager.setFavouriteRolls(rollList);
                        recyclerView.getAdapter().notifyItemInserted(position);
                    }
                }, 6000);
        snackbarUndo.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (event == Snackbar.Callback.DISMISS_EVENT_SWIPE || event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    checkViewState();
                }
            }
        });
        snackbarUndo.show();
    }

    private void showFavouriteRollDialog(Roll roll) {
        final String name = roll.getName();
        String rollString = roll.getRollString();
        Roll resultRoll = rollParser.parseExpression(rollString);
        resultRoll.setName(name);
        notifyRollHistory(resultRoll);
        RolledTheDiceDialog rolledTheDiceDialog = new RolledTheDiceDialog(getContext(), resultRoll, new RolledTheDiceDialog.RollDialogClickListener() {
            @Override
            public void rollAgain(Dialog dialog, Roll roll2) {
                dialog.dismiss();
                roll2.setName(name);
                showFavouriteRollDialog(roll2);
            }
        });
        rolledTheDiceDialog.show();
    }

    public void reloadListAndRecyclerView() {
        rollList = preferenceManager.getFavouriteRolls();
        Collections.sort(rollList);
        favouriteRollAdapter.setRollList(rollList);
        favouriteRollAdapter.notifyDataSetChanged();
        checkViewState();
    }

    private void checkViewState() {
        if (rollList.size() == 0) {
            makeViewDisappear(getContext(), recyclerView);
            makeViewAppear(getContext(), emptyListLayout);
        } else {
            makeViewAppear(getContext(), recyclerView);
            makeViewDisappear(getContext(), emptyListLayout);
        }
    }

    @Click(R.id.favourites_empty_list_button)
    public void onEmptyListLayoutClick() {
        favouritesRollListener.onEmptyListLayoutClick();
    }

    private void notifyRollHistory(Roll roll) {
        roll.setTimeOfRoll(System.currentTimeMillis());
        List<Roll> historyRolls = preferenceManager.getHistoryRolls();
        historyRolls.add(roll);
        preferenceManager.setHistoryRolls(historyRolls);
        favouritesRollListener.onFavouriteRollMade();
    }

    public interface FavouritesRollListener {
        void onEmptyListLayoutClick();
        void onFavouriteRollMade();
    }

}
