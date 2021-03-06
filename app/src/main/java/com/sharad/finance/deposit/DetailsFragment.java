/*
 * Copyright 2014, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sharad.finance.deposit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * This fragment inflates a layout with two Floating Action Buttons and acts as a listener to
 * changes on them.
 */
public class DetailsFragment extends Fragment {
    private final static String TAG = "DetailsFragment";
    public final static String ITEM_ID_KEY = "DetailsFragment$idKey";
    private Deposit _deposit;

    public static DetailsFragment createInstance(long id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ITEM_ID_KEY, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.feed_list, container, false);

        Bundle bundle = this.getArguments();
        long id = bundle.getLong(ITEM_ID_KEY, 0);
        DBAdapter db = new DBAdapter(getActivity());
        db.open();
        _deposit = db.getDeposit(id);
        db.close();

        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DetailsRecycler recyclerAdapter = new DetailsRecycler(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<DetailItem> createItemList() {
        List<DetailItem> itemList = new ArrayList<>();
        DecimalFormat nf = new DecimalFormat("##,##,###");
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy");
        itemList.add(new DetailItem(_deposit.get_title(), "Title", R.drawable.ic_label_outline_black_24dp, false));
        itemList.add(new DetailItem(_deposit.get_bank(), "Bank Name", 0, false));
        itemList.add(new DetailItem(_deposit.get_accNum(), "Account Number", 0, true));
        itemList.add(new DetailItem(df.format(_deposit.get_startDate()), "Start Date", R.drawable.ic_access_time_black_24dp, false));
        itemList.add(new DetailItem(""+_deposit.get_tenure()+" Days", "Tenure", 0, true));
        itemList.add(new DetailItem(nf.format(_deposit.get_principle()), "Principle", R.drawable.ic_attach_money_black_24dp, false));
        itemList.add(new DetailItem(""+_deposit.get_rate()+"%", "Rate", 0, false));
        itemList.add(new DetailItem(nf.format(_deposit.get_intPerDay()), "Interest Per Day", 0, false));
        itemList.add(new DetailItem(nf.format(_deposit.get_accInterest()), "Accumulated Interest", 0, true));
        itemList.add(new DetailItem(df.format(_deposit.get_endDate()), "Close Date", R.drawable.ic_card_giftcard_black_24dp, false));
        itemList.add(new DetailItem(""+_deposit.get_daysRemain()+" Days", "Remaining", 0, false));
        itemList.add(new DetailItem(nf.format(_deposit.get_actInterest()), "Interest", 0, false));
        itemList.add(new DetailItem(nf.format(_deposit.get_tds()), "TDS", 0, false));
        itemList.add(new DetailItem(nf.format(_deposit.get_principle()+_deposit.get_actInterest()), "Return Amount", 0, false));

        return itemList;
    }
}
