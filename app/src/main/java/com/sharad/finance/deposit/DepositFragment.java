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

import java.util.ArrayList;
import java.util.List;


/**
 * This fragment inflates a layout with two Floating Action Buttons and acts as a listener to
 * changes on them.
 */
public class DepositFragment extends Fragment {
    private DBAdapter _db;
    private String _where;
    private RecyclerAdapter _adapter;
    private final static String TAG = "DepositFragment";
    public final static String ITEMS_QUERY_KEY = "DepositFragment$queryKey";

    public static DepositFragment createInstance(String where) {
        DepositFragment fragment = new DepositFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ITEMS_QUERY_KEY, where);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.feed_list, container, false);
        setupRecyclerView(recyclerView);
        Bundle bundle = this.getArguments();
        _where = bundle.getString(ITEMS_QUERY_KEY, null);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _adapter = new RecyclerAdapter();
        recyclerView.setAdapter(_adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra(DetailsActivity.ID_KEY, _adapter.getItemList().get(position).get_id());

                        View movingView = getActivity().findViewById(R.id.appBarLayout);
                        Pair<View, String> pair1 = Pair.create(movingView, movingView.getTransitionName());
                        movingView = view.findViewById(R.id.dc_progress);
                        Pair<View, String> pair2 = Pair.create(movingView, movingView.getTransitionName());

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(), pair1, pair2
                        );
                        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                    }
                })
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        _db = new DBAdapter(getActivity());
        _db.open();
        updateItemList();
    }

    @Override
    public void onPause() {
        super.onPause();
        _db.close();
    }

    private void updateItemList() {
        if(_db != null) {
            _db.getDeposits(_adapter.getItemList(), _where);
        }
    }
}
