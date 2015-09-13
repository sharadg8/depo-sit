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

import java.util.ArrayList;
import java.util.List;


/**
 * This fragment inflates a layout with two Floating Action Buttons and acts as a listener to
 * changes on them.
 */
public class DetailsFragment extends Fragment {
    private final static String TAG = "DetailsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.feed_list, container, false);
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

        itemList.add(new DetailItem("Title Text", "Title", R.drawable.ic_label_outline_black_24dp, false));
        itemList.add(new DetailItem("SBI Bangalore", "Bank Name", 0, false));
        itemList.add(new DetailItem("FD123456789", "Account Number", 0, true));
        itemList.add(new DetailItem("Sat, 12 Sep 2015", "Start Date", R.drawable.ic_access_time_black_24dp, false));
        itemList.add(new DetailItem("365 Days", "Tenure", 0, true));
        itemList.add(new DetailItem("1,50,000", "Principle", R.drawable.ic_attach_money_black_24dp, false));
        itemList.add(new DetailItem("8.6%", "Rate", 0, false));
        itemList.add(new DetailItem("23", "Interest Per Day", 0, false));
        itemList.add(new DetailItem("4,123", "Accumulated Interest", 0, true));
        itemList.add(new DetailItem("Sun, 11 Sep 2016", "Maturity Date", R.drawable.ic_card_giftcard_black_24dp, false));
        itemList.add(new DetailItem("126 Days", "Remaining", 0, false));
        itemList.add(new DetailItem("23", "Interest Per Day", 0, false));
        itemList.add(new DetailItem("8,123", "Interest", 0, false));
        itemList.add(new DetailItem("123", "TDS", 0, false));
        itemList.add(new DetailItem("1,58,000", "Return Amount", 0, false));

        return itemList;
    }
}
