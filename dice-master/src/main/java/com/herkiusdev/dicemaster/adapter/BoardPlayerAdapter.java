package com.herkiusdev.dicemaster.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.model.BoardPlayerDTO;

import java.util.List;

public class BoardPlayerAdapter extends RecyclerView.Adapter<BoardPlayerAdapter.BoardPlayerViewHolder> {

    List<BoardPlayerDTO> playerList;
    Context context;

    public BoardPlayerAdapter(List<BoardPlayerDTO> playerList, Context context) {
        this.playerList = playerList;
        this.context = context;
    }

    @Override
    public BoardPlayerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_board_player, viewGroup, false);
        return new BoardPlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BoardPlayerViewHolder holder, final int position) {
        holder.playerName.setText(playerList.get(position).getName());
        holder.playerScore.setText("" + playerList.get(position).getScore());
        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = Integer.parseInt(holder.playerScore.getText().toString());
                holder.playerScore.setText("" + --score);
            }
        });
        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = Integer.parseInt(holder.playerScore.getText().toString());
                holder.playerScore.setText("" + ++score);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playerList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, playerList.size());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.several_players_delete_player_dialog_title));
                builder.setMessage(context.getString(R.string.several_players_delete_player_dialog_text, playerList.get(position).getName()));
                builder.setNegativeButton(context.getText(R.string.one_vs_one_player_one_neg_button).toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton(context.getText(R.string.one_vs_one_player_one_pos_button).toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, playerList.size());
                    }
                });
                AlertDialog b = builder.create();
                b.show();

                Button negBtn = b.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(negBtn != null)
                    negBtn.setTextColor(context.getResources().getColor(R.color.color_text_card_view));

                Button posBtn = b.getButton(DialogInterface.BUTTON_POSITIVE);
                if (posBtn != null)
                    posBtn.setTextColor(context.getResources().getColor(R.color.color_accent));
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class BoardPlayerViewHolder extends RecyclerView.ViewHolder {

        public TextView playerName;
        public Button decreaseButton;
        public Button increaseButton;
        public EditText playerScore;
        public ImageButton deleteButton;

        public BoardPlayerViewHolder(View itemView) {
            super(itemView);

            playerName = (TextView)itemView.findViewById(R.id.view_board_player_name);
            decreaseButton = (Button)itemView.findViewById(R.id.view_board_player_decrease_score);
            increaseButton = (Button)itemView.findViewById(R.id.view_board_player_increase_score);
            playerScore = (EditText)itemView.findViewById(R.id.view_board_player_score);
            deleteButton = (ImageButton)itemView.findViewById(R.id.view_board_player_delete);
        }
    }

}
