package com.herkiusdev.dicemaster.activity.board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.adapter.BoardPlayerAdapter;
import com.herkiusdev.dicemaster.model.BoardPlayerDTO;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_several_players)
public class SeveralPlayersActivity extends AppCompatActivity {

    @ViewById(R.id.several_players_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.several_players_toolbar_title)
    TextView toolbarTitle;
    @ViewById(R.id.several_players_list)
    RecyclerView playerRecyclerView;

    List<BoardPlayerDTO> playerList = new ArrayList<>();
    BoardPlayerAdapter adapter;
    boolean doubleBack = false;

    @AfterViews
    public void initViews(){
        setupToolbar();
        setupRecyclerView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Snackbar.make(toolbar, R.string.several_players_go_back, Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.several_players_name));
    }

    private void setupRecyclerView() {
        playerRecyclerView.setHasFixedSize(true);
        playerRecyclerView.setLayoutManager(new LinearLayoutManager(SeveralPlayersActivity.this));
        adapter = new BoardPlayerAdapter(playerList, SeveralPlayersActivity.this);
        playerRecyclerView.setAdapter(adapter);
    }

    @Click(R.id.several_players_toolbar_back)
    public void goBack() {
        onBackPressed();
    }

    @Click(R.id.several_players_fab)
    public void addNewPlayer() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_edit_text_edit);

        String title = getText(R.string.several_players_new_player_dialog_title).toString();
        String message = getText(R.string.several_players_new_player_dialog_text).toString();

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(R.string.one_vs_one_player_one_pos_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edt.getText().toString();
                if (!name.isEmpty()) {
                    playerList.add(new BoardPlayerDTO(name));
                    adapter.notifyItemInserted(playerList.size());
                }
            }
        });
        dialogBuilder.setNegativeButton(R.string.one_vs_one_player_one_neg_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

        Button negBtn = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(negBtn != null)
            negBtn.setTextColor(getResources().getColor(R.color.color_text_card_view));

        Button posBtn = b.getButton(DialogInterface.BUTTON_POSITIVE);
        if (posBtn != null)
            posBtn.setTextColor(getResources().getColor(R.color.color_accent));
    }

}
