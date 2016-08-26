package com.example.scardenas.dice_master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.scardenas.dice_master.R;
import com.example.scardenas.dice_master.data.Roll;
import com.example.scardenas.dice_master.view.FavouriteRollView;

import java.util.List;

public class FavouriteRollAdapter extends RecyclerView.Adapter<FavouriteRollView> {

    private Context context;
    private List<Roll> rollList;
    private FavouriteRollView.OnFavouriteRollClickListener onFavouriteRollClickListener;

    public FavouriteRollAdapter(Context context, List<Roll> rollList, FavouriteRollView.OnFavouriteRollClickListener onFavouriteRollClickListener) {
        this.context = context;
        this.rollList = rollList;
        this.onFavouriteRollClickListener = onFavouriteRollClickListener;
    }

    public void setRollList(List<Roll> rollList) {
        this.rollList = rollList;
    }

    @Override
    public FavouriteRollView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteRollView(LayoutInflater.from(context).inflate(R.layout.view_favourite_roll, parent, false), context, onFavouriteRollClickListener);
    }

    @Override
    public void onBindViewHolder(FavouriteRollView holder, int position) {
        holder.bind(rollList.get(position));
    }

    @Override
    public int getItemCount() {
        return rollList.size();
    }
}
