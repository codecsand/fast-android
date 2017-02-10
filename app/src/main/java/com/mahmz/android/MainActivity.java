package com.mahmz.android;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.mahmz.android.Adapters.MainPagerAdapter;
import com.mahmz.android.MainFragments.UsersFragment;
import com.mahmz.android.MainFragments.PostsFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar mActionBar;
    private int[] tabIcons = {
            R.drawable.homeicon,
            R.drawable.profileicon
    };
    public static HashMap<String, Boolean> postsfadein;
    public static HashMap<String, Boolean> users;
    public static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;

    // main constructor
    public MainActivity() {
        postsfadein = new HashMap<String, Boolean>(DEFAULT_CACHE_SIZE);
        users = new HashMap<String, Boolean>(DEFAULT_CACHE_SIZE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // create custome action bar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        // set custome top bar
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        // setup viewpager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostsFragment());
        adapter.addFragment(new UsersFragment());
        viewPager.setAdapter(adapter);
        // load fragments data only once
        viewPager.setOffscreenPageLimit(2);
        // set tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        // set tabs icons
        setupTabIcons();
    }

    // setup tabs icons
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
}
