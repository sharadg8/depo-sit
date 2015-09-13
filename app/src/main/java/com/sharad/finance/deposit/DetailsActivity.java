package com.sharad.finance.deposit;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharad.finance.common.PieChart;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends ActionBarActivity {
    Handler tick_Handler = new Handler();
    AnimThread tick_thread = new AnimThread();
    RelativeLayout actionView;
    List<View> actionViews;
    int curActionId = 0;

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

        float values[] = { 150000, 11000 };
        PieChart graphView = new PieChart(this, values);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.ic_logo);

        actionView = (RelativeLayout) findViewById(R.id.action_view);
        actionView.addView(graphView);

        actionViews = new ArrayList<>();
        actionViews.add(imageView);
        actionViews.add(graphView);
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

    @Override
    public void onStop(){
        tick_Handler.removeCallbacks(tick_thread);
        super.onStop();
    }

    @Override
    public void onResume(){
        tick_Handler.post(tick_thread);
        super.onResume();
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

    private class AnimThread implements Runnable {
        @Override
        public void run() {
            TranslateAnimation exitAnim;
            exitAnim = new TranslateAnimation(0.0f, -500.0f, 0.0f, 0.0f);
            exitAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) { }
                @Override
                public void onAnimationRepeat(Animation arg0) { }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    TranslateAnimation entryAnim = new TranslateAnimation(500.0f, 0.0f, 0.0f, 0.0f);
                    entryAnim.setDuration(2000);
                    entryAnim.setInterpolator(new DecelerateInterpolator(4.0f));
                    actionView.removeAllViews();
                    actionView.addView(actionViews.get(curActionId));
                    actionView.startAnimation(entryAnim);
                    curActionId++;
                    curActionId = (curActionId < actionViews.size()) ? curActionId : 0;
                }
            });
            exitAnim.setDuration(2000);
            exitAnim.setInterpolator(new AccelerateInterpolator(4.0f));
            actionView.startAnimation(exitAnim);
            tick_Handler.postDelayed(tick_thread, 8000);
        }
    }
}
