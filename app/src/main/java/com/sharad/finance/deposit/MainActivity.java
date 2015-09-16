package com.sharad.finance.deposit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initFAB();
        initViewPagerAndTabs();
    }

    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);

                    View movingView = findViewById(R.id.appBarLayout);
                    Pair<View, String> pair1 = Pair.create(movingView, movingView.getTransitionName());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MainActivity.this, pair1
                    );
                    ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
                    return true;
                }
                return true;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        String key = DBAdapter.KEY_FD_STATUS+"="+Deposit.STATUS_ACTIVE;
        pagerAdapter.addFragment(DepositFragment.createInstance(key), getString(R.string.tab_1));
        key = DBAdapter.KEY_FD_STATUS+"="+Deposit.STATUS_CLOSED;
        pagerAdapter.addFragment(DepositFragment.createInstance(key), getString(R.string.tab_2));
        key = DBAdapter.KEY_FD_STATUS+"="+Deposit.STATUS_CANCEL;
        pagerAdapter.addFragment(DepositFragment.createInstance(null), getString(R.string.tab_3));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.show();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_dummy:
                setupDummyDatabase();
                return true;
            case R.id.action_chart:
                return true;
            default:
                // Not ours
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDummyDatabase() {
        DBAdapter db = new DBAdapter(this);
        db.open();

        db.insertDeposit(new Deposit(0, "Aug 2015", "SBI Bangalore", "00000035137506353", "note",
                new Date(115, 8, 7), new Date(116, 8, 6), 365, 0, Deposit.STATUS_ACTIVE, 55000, 7.75f, 0, 0, 0));

        db.insertDeposit(new Deposit(0, "Aug 2015", "SBI Bangalore", "00000035131972773", "note",
                new Date(115, 8, 5), new Date(116, 8, 4), 365, 0, Deposit.STATUS_ACTIVE, 100000, 7.75f, 0, 0, 0));

        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000035052169070", "note",
                new Date(115, 7, 7), new Date(116, 7, 6), 365, 0, Deposit.STATUS_ACTIVE, 40000, 8, 0, 0, 0));

        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000035037049648", "note",
                new Date(115, 7, 1), new Date(116, 6, 30), 365, 0, Deposit.STATUS_ACTIVE, 120000, 8, 0, 0, 0));

        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000035037049648", "note",
                new Date(115, 7, 1), new Date(116, 6, 30), 365, 0, Deposit.STATUS_ACTIVE, 120000, 8, 0, 0, 0));

        db.insertDeposit(new Deposit(0, "Jun 2015", "SBI Bangalore", "00000034992218675", "note",
                new Date(115, 6, 12), new Date(116, 6, 11), 365, 0, Deposit.STATUS_ACTIVE, 80000, 8, 0, 0, 0));
        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000034793497379", "note",
                new Date(115, 3, 16), new Date(116, 3, 16), 365, 0, Deposit.STATUS_ACTIVE, 145000, 8.5f, 0, 0, 0));
        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000034684146880", "note",
                new Date(115, 2, 7), new Date(116, 2, 7), 365, 0, Deposit.STATUS_ACTIVE, 160000, 8.5f, 0, 0, 0));
        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000034584644084", "note",
                new Date(115, 1, 9), new Date(116, 1, 9), 365, 0, Deposit.STATUS_ACTIVE, 60000, 8.5f, 0, 0, 0));
        db.insertDeposit(new Deposit(0, "Jul 2015", "SBI Bangalore", "00000034137989110", "note",
                new Date(115, 9, 3), new Date(116, 3, 3), 365, 0, Deposit.STATUS_ACTIVE, 54182, 7.5f, 0, 0, 0));


        db.close();
    }
}
