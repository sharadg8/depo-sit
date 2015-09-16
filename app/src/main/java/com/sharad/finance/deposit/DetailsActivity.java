package com.sharad.finance.deposit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.sharad.finance.common.PieChart;
import com.sharad.finance.common.Progress;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends ActionBarActivity {
    public final static String ID_KEY = "DetailsActivity$idKey";
    private ViewFlipper _flipper;
    private Deposit _deposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong(ID_KEY, 0);
            DBAdapter db = new DBAdapter(this);
            db.open();
            _deposit = db.getDeposit(id);
            db.close();
        }

        initToolbar();
        initFragment();
    }

    private void initFragment() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(DetailsFragment.createInstance(_deposit.get_id()), "Details");
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
        progressView.setProgress(_deposit.get_progress());
        rl2.addView(progressView);

        float values[] = { _deposit.get_principle(), _deposit.get_actInterest() };
        PieChart graphView = new PieChart(this, true);
        graphView.setValues(values);
        RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.action_graph);
        rl3.addView(graphView);

        final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
        _flipper = (ViewFlipper) this.findViewById(R.id.action_flipper);
        _flipper.startFlipping();
        _flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
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

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            Context ctx = getApplicationContext();
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    _flipper.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_in));
                    _flipper.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_out));
                    _flipper.stopFlipping();
                    _flipper.showNext();
                    _flipper.startFlipping();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    _flipper.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_in));
                    _flipper.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_out));
                    _flipper.stopFlipping();
                    _flipper.showPrevious();
                    _flipper.startFlipping();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
