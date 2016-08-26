package com.example.scardenas.dice_master.fragment;

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.scardenas.dice_master.BaseFragment;
import com.example.scardenas.dice_master.PreferenceManager;
import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;
import com.example.scardenas.dice_master.dialog.RolledTheDiceDialog;
import com.example.scardenas.dice_master.dialog.SaveFavouriteRollDialog;
import com.example.scardenas.dice_master.util.RollParser;
import com.example.scardenas.dice_master.util.SnackbarUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_new_roll)
public class NewRollFragment extends BaseFragment {

    @ViewById(R.id.new_roll_text) TextView rollText;
    @ViewById(R.id.new_roll_button) AppCompatButton rollButton;

    private PreferenceManager preferenceManager;
    private RollParser rollParser;
    private SnackbarUtils snackbarUtils;

    private NewRollListener newRollListener;

    private List<Integer> inputLengthList;
    private List<Integer> dieWritten;

    @AfterViews
    public void initialize() {
        newRollListener = (NewRollListener)getActivity();
        preferenceManager = new PreferenceManager(getContext());
        rollParser = RollParser.getInstance();
        snackbarUtils = new SnackbarUtils(getContext());
        inputLengthList = new ArrayList<>();
        dieWritten = new ArrayList<>();
    }

    private void writeZeroToRoll() {
        String written = rollText.getText().toString();
        String string = getString(R.string.new_roll_0);
        if ((dieWritten.get(dieWritten.size()-1)==0) && !TextUtils.isEmpty(written) && !written.endsWith(" ")) {
            rollText.setText(written + string);
            inputLengthList.add(1);
            dieWritten.add(0);
        }
    }

    private void writeToRoll(String string) {
        String written = rollText.getText().toString();
        if (!TextUtils.isEmpty(written)) {
            if ((dieWritten.get(dieWritten.size()-1)==1)) {
                if ((string.contains("+") || string.contains("-"))){
                    rollText.setText(written + string);
                    inputLengthList.add(string.length());
                } else {
                    rollText.setText(written + " + " + string);
                    inputLengthList.add(string.length() + 3);
                }
            } else {
                rollText.setText(written + string);
                inputLengthList.add(string.length());
            }
        } else {
            rollText.setText(string);
            inputLengthList.add(string.length());
        }
        checkRollButtonState();
    }

    private void deleteLastInput() {
        String written = rollText.getText().toString();
        if (!TextUtils.isEmpty(written)) {
            int lastPosition = inputLengthList.size()-1;
            int numberOfCharactesrs = inputLengthList.remove(lastPosition);
            dieWritten.remove(lastPosition);
            String newWritten = written.substring(0, written.length() - numberOfCharactesrs);
            rollText.setText(newWritten);
        }
        checkRollButtonState();
    }

    private void checkRollButtonState() {
        String newWritten = rollText.getText().toString();
        if (TextUtils.isEmpty(newWritten)) {
            rollButton.setEnabled(false);
        } else {
            Character lastCharacter = newWritten.charAt(newWritten.length()-1);
            if (!newWritten.contains("d") || lastCharacter.equals(' ')) {
                rollButton.setEnabled(false);
            } else {
                rollButton.setEnabled(true);
            }
        }
    }

    private void rollTheDie() {
        String expression = rollText.getText().toString();
        Roll resultRoll = rollParser.parseExpression(expression);
        notifyRollHistory(resultRoll);
        showDiceRollDialog(resultRoll);
    }

    private void showFavouriteSavingDialog(final String rollString) {
        SaveFavouriteRollDialog dialog = new SaveFavouriteRollDialog(getContext(), R.style.SaveFavouriteRollDialogTheme, rollString, new SaveFavouriteRollDialog.SaveFavouriteRollClickListener() {
            @Override
            public void onNegativeButtonClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(Dialog dialog, String rollName) {
                dialog.dismiss();
                Roll roll = new Roll(rollName, rollString);
                List<Roll> rollList = preferenceManager.getFavouriteRolls();
                rollList.add(roll);
                preferenceManager.setFavouriteRolls(rollList);
                snackbarUtils.createInfoSnackbar(rollText, R.string.new_roll_favourite_saved).show();
                newRollListener.onNewFavouriteRollSavedListener();
            }
        });
        dialog.show();
    }

    private void showDiceRollDialog(Roll resultRoll) {
        RolledTheDiceDialog rolledTheDiceDialog = new RolledTheDiceDialog(getContext(), resultRoll, new RolledTheDiceDialog.RollDialogClickListener() {
            @Override
            public void rollAgain(Dialog dialog, Roll roll) {
                dialog.dismiss();
                Roll newRoll = rollParser.parseExpression(roll.getRollString());
                notifyRollHistory(newRoll);
                showDiceRollDialog(newRoll);
            }
        });
        rolledTheDiceDialog.show();
    }

    private void notifyRollHistory(Roll roll) {
        roll.setTimeOfRoll(System.currentTimeMillis());
        List<Roll> historyRolls = preferenceManager.getHistoryRolls();
        historyRolls.add(roll);
        preferenceManager.setHistoryRolls(historyRolls);
        newRollListener.onNewRollMade();
    }

    //region Button pushes
    @Click(R.id.new_roll_button)
    public void onRollClick() {
        rollTheDie();
    }

    @Click(R.id.new_roll_d2)
    public void onD2Click() {
        writeToRoll(getString(R.string.new_roll_d2));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d3)
    public void onD3Click() {
        writeToRoll(getString(R.string.new_roll_d3));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d4)
    public void onD4Click() {
        writeToRoll(getString(R.string.new_roll_d4));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d6)
    public void onD6Click() {
        writeToRoll(getString(R.string.new_roll_d6));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d8)
    public void onD8Click() {
        writeToRoll(getString(R.string.new_roll_d8));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d10)
    public void onD10Click() {
        writeToRoll(getString(R.string.new_roll_d10));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d12)
    public void onD12Click() {
        writeToRoll(getString(R.string.new_roll_d12));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d20)
    public void onD20Click() {
        writeToRoll(getString(R.string.new_roll_d20));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_d100)
    public void onD100Click() {
        writeToRoll(getString(R.string.new_roll_d100));
        dieWritten.add(1);
    }

    @Click(R.id.new_roll_plus)
    public void onPlusClick() {
        String written = rollText.getText().toString();
        Character lastChar = written.charAt(written.length()-1);
        if (!lastChar.toString().equals(" ")) {
            writeToRoll(getString(R.string.new_roll_plus_written));
            dieWritten.add(0);
            rollButton.setEnabled(false);
        }
    }

    @Click(R.id.new_roll_minus)
    public void onMinusClick() {
        String written = rollText.getText().toString();
        Character lastChar = written.charAt(written.length()-1);
        if (!lastChar.toString().equals(" ")) {
            writeToRoll(getString(R.string.new_roll_minus_written));
            dieWritten.add(0);
            rollButton.setEnabled(false);
        }
    }

    @Click(R.id.new_roll_0)
    public void on0Click() {
        writeZeroToRoll();
    }

    @Click(R.id.new_roll_1)
    public void on1Click() {
        writeToRoll(getString(R.string.new_roll_1));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_2)
    public void on2Click() {
        writeToRoll(getString(R.string.new_roll_2));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_3)
    public void on3Click() {
        writeToRoll(getString(R.string.new_roll_3));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_4)
    public void on4Click() {
        writeToRoll(getString(R.string.new_roll_4));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_5)
    public void on5Click() {
        writeToRoll(getString(R.string.new_roll_5));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_6)
    public void on6Click() {
        writeToRoll(getString(R.string.new_roll_6));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_7)
    public void on7Click() {
        writeToRoll(getString(R.string.new_roll_7));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_8)
    public void on8Click() {
        writeToRoll(getString(R.string.new_roll_8));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_9)
    public void on9Click() {
        writeToRoll(getString(R.string.new_roll_9));
        dieWritten.add(0);
    }

    @Click(R.id.new_roll_clear)
    public void onClearClick() {
        rollText.setText("");
        dieWritten.add(0);
        rollButton.setEnabled(false);
    }

    @Click(R.id.new_roll_delete_last)
    public void onDeleteLastClick() {
        deleteLastInput();
    }

    @Click(R.id.new_roll_favourite)
    public void onFavouriteClick() {
        String roll = rollText.getText().toString();
        if (roll.contains("d")) {
            showFavouriteSavingDialog(roll);
        }
    }
    //endregion

    public interface NewRollListener {
        void onNewFavouriteRollSavedListener();
        void onNewRollMade();
    }

}
