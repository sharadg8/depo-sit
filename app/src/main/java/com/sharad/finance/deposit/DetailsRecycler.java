package com.sharad.finance.deposit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sharad on 12-Sep-15.
 */
public class DetailsRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DetailItem> mItemList;

    public DetailsRecycler(List<DetailItem> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.detail_card, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        DetailViewHolder holder = (DetailViewHolder) viewHolder;
        holder.setText(mItemList.get(position).get_text());
        holder.setInfo(mItemList.get(position).get_info());
        holder.showDivider(mItemList.get(position).is_divider());
        holder.setImage(mItemList.get(position).get_imgId());
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView _text;
        private TextView _info;
        private ImageView _img;
        private View _divider;

        public DetailViewHolder(final View parent) {
            super(parent);
            _text = (TextView) parent.findViewById(R.id.dt_text);
            _info = (TextView) parent.findViewById(R.id.dt_info);
            _img = (ImageView) parent.findViewById(R.id.dt_image);
            _divider = (View) parent.findViewById(R.id.dt_divider);

            int color = parent.getResources().getColor(R.color.primary_dark);
            _img.setColorFilter(color);
        }

        public void setText(CharSequence text) {   _text.setText(text);   }
        public void setInfo(CharSequence text) {   _info.setText(text);   }
        public void showDivider(boolean show) {
            if(show) {
                _divider.setVisibility(View.VISIBLE);
            } else {
                _divider.setVisibility(View.INVISIBLE);
            }
        }
        public void setImage(int id) {
            if(id > 0) {
                _img.setImageResource(id);
                _img.setVisibility(View.VISIBLE);
            } else {
                _img.setVisibility(View.INVISIBLE);
            }
        }
    }
}
