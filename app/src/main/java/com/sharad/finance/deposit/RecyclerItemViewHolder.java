package com.sharad.finance.deposit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharad.finance.common.Progress;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView _title;
    private final TextView _info;
    private final TextView _date;
    private final Progress _progress;

    public RecyclerItemViewHolder(final View parent, TextView titleView,
                                  TextView infoView, TextView dateView,
                                  Progress progress) {
        super(parent);
        _title = titleView;
        _info = infoView;
        _date = dateView;
        _progress = progress;
        _progress.setColor(parent.getResources().getColor(R.color.primary_dark));
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView titleView = (TextView) parent.findViewById(R.id.dc_title);
        TextView infoView = (TextView) parent.findViewById(R.id.dc_info);
        TextView dateView = (TextView) parent.findViewById(R.id.dc_date);
        RelativeLayout progressView = (RelativeLayout) parent.findViewById(R.id.dc_progress);
        Progress progress = new Progress(parent.getContext(), false);
        progressView.removeAllViews();
        progressView.addView(progress);
        return new RecyclerItemViewHolder(parent, titleView, infoView, dateView, progress);
    }

    public void setTitle(CharSequence text) {   _title.setText(text);   }
    public void setInfo(CharSequence text)  {   _info.setText(text);    }
    public void setDate(CharSequence text)  {   _date.setText(text);    }
    public void setProgress(int progress)  {   _progress.setProgress(progress);    }
}
