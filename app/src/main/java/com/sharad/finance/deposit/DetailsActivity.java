package com.sharad.finance.deposit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.sharad.finance.common.PieChart;
import com.sharad.finance.common.Progress;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initToolbar();
        initFragment();
    }

    private void initFragment() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new DetailsFragment(), "Details");
        viewPager.setAdapter(pagerAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.ic_logo);
        RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.action_logo);
        rl1.addView(imageView);

        Progress progressView = new Progress(this, true);
        RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.action_progress);
        progressView.setProgress(60);
        rl2.addView(progressView);

        float values[] = { 150000, 11000 };
        PieChart graphView = new PieChart(this, values);
        RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.action_graph);
        rl3.addView(graphView);

        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.action_flipper);
        flipper.startFlipping();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
