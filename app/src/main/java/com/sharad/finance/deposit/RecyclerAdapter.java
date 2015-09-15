package com.sharad.finance.deposit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Deposit> mItemList;

    public RecyclerAdapter() {
        mItemList = new ArrayList<>();
    }

    public ArrayList<Deposit> getItemList() {
        return mItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.deposit_card, parent, false);
        return RecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        holder.setTitle(mItemList.get(position).get_title());
        holder.setInfo(mItemList.get(position).get_info());
        holder.setDate(mItemList.get(position).get_startDateText());
        holder.setProgress(mItemList.get(position).get_progress());
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}
