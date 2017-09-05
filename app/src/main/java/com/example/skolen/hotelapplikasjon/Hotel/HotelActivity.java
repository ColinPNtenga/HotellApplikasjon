package com.example.skolen.hotelapplikasjon.Hotel;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.skolen.hotelapplikasjon.Adapter.ViewPagerAdapter;
import com.example.skolen.hotelapplikasjon.ContentFragments.RoomFragment;
import com.example.skolen.hotelapplikasjon.ContentFragments.RestaurantFragment;
import com.example.skolen.hotelapplikasjon.ContentFragments.TourismFragment;
import com.example.skolen.hotelapplikasjon.Customer.ProfileActivity;
import com.example.skolen.hotelapplikasjon.R;

public class HotelActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private final static String TAB = "com.example.skolen.hotelapplication.tab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        activityFunctionality();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        switchTabs();
    }

    private void activityFunctionality() {
        setupToolbar();
        getTabFragments();
        displayTabs();
        setupTabIcons();
        profileButtonFunction();
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void getTabFragments() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragments(new RoomFragment(), "Room");
        mViewPagerAdapter.addFragments(new RestaurantFragment(), "Restaurant");
        mViewPagerAdapter.addFragments(new TourismFragment(), "Tourism");
    }

    private void displayTabs() {
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupTabIcons() {
        if (mTabLayout != null) {
            if (mTabLayout.getTabAt(0) != null)
                mTabLayout.getTabAt(0).setIcon(R.drawable.ic_action_room);
            if (mTabLayout.getTabAt(1) != null)
                mTabLayout.getTabAt(1).setIcon(R.drawable.ic_action_restaurant);
            if (mTabLayout.getTabAt(2) != null)
                mTabLayout.getTabAt(2).setIcon(R.drawable.ic_action_info);
        }
    }

    private void switchTabs() {
        Intent intent = getIntent();
        String tabs = intent.getStringExtra(TAB);

        if (tabs != null && tabs.equals("home")) {
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
        }
        if (tabs != null && tabs.equals("restaurant")) {
            TabLayout.Tab tab = mTabLayout.getTabAt(1);
            tab.select();
        }
        if (tabs != null && tabs.equals("tourism")) {
            TabLayout.Tab tab = mTabLayout.getTabAt(2);
            tab.select();
        }

    }

    private void profileButtonFunction() {
        FloatingActionButton mFloatingProfileButton = (FloatingActionButton) findViewById(R.id.profile_button);
        mFloatingProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profile);
            }
        });
    }

}
