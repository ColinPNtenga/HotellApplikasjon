package com.example.skolen.hotelapplikasjon.Customer;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.skolen.hotelapplikasjon.Firebase.Entities.Customer;
import com.example.skolen.hotelapplikasjon.Hotel.HotelActivity;
import com.example.skolen.hotelapplikasjon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private boolean isOpen = false;

    private TextView mTextCustomerName;
    private TextView mTextCustomerMobile;
    private TextView mTextCustomerEmail;
    private TextView mTextCustomerRoom;

    private static final String TAG = "MyActivity";
    private final static String TAB = "com.example.skolen.hotelapplication.tab";
    private Animation mAnimationOpen, mAnimationClose, mAnimationClockwise, mAnimationAntiClockwise;
    private FloatingActionButton mFloatingMenuButton, mFloatingHomeButton, mFloatingRestaurantButton, mFloatingInfoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        functionalities();
    }

    private void functionalities() {
        settingToolbar();
        settingCollapsedToolbarLayout();

        initializeTextViews();
        initializeButtons();
        initializeAnimations();
        setupMenuButton();

        menuButtonsFunction();
        getCustomerInfo();

    }

    private void settingToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void settingCollapsedToolbarLayout() {
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.profile_layout);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.rgb(255,20,147));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255,20,147));
    }

    private void initializeButtons() {
        mFloatingHomeButton = (FloatingActionButton) findViewById(R.id.home_button);
        mFloatingRestaurantButton = (FloatingActionButton) findViewById(R.id.restaurant_button);
        mFloatingInfoButton = (FloatingActionButton) findViewById(R.id.information_button);
    }

    private void initializeAnimations() {
        mAnimationOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        mAnimationClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        mAnimationClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        mAnimationAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
    }

    private void initializeTextViews() {
        mTextCustomerEmail = (TextView) findViewById(R.id.customerEmail);
        mTextCustomerMobile = (TextView) findViewById(R.id.customerNumber);
        mTextCustomerName = (TextView) findViewById(R.id.customerName);
        mTextCustomerRoom = (TextView) findViewById(R.id.customerRoom);
    }

    private void getCustomerInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Customers").child(user.getUid());

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Customer customer = postSnapshot.getValue(Customer.class);

                        mTextCustomerName.setText(customer.getName());
                        mTextCustomerEmail.setText(customer.getEmail());
                        mTextCustomerMobile.setText(Integer.toString(customer.getMobile()));
                        mTextCustomerRoom.setText(Integer.toString(customer.getRoomNumber()));

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }

    private void setupMenuButton() {
        mFloatingMenuButton = (FloatingActionButton) findViewById(R.id.menu_button);
        mFloatingMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuButtons();
            }
        });
    }

    private void openMenuButtons() {
        if (isOpen) {
            mFloatingInfoButton.startAnimation(mAnimationClose);
            mFloatingRestaurantButton.startAnimation(mAnimationClose);
            mFloatingHomeButton.startAnimation(mAnimationClose);
            mFloatingMenuButton.startAnimation(mAnimationAntiClockwise);
            deactivateMenuButtons();
            isOpen = false;

        }
        else {
            mFloatingInfoButton.startAnimation(mAnimationOpen);
            mFloatingRestaurantButton.startAnimation(mAnimationOpen);
            mFloatingHomeButton.startAnimation(mAnimationOpen);
            mFloatingMenuButton.startAnimation(mAnimationClockwise);
            activateMenuButtons();
            isOpen = true;
        }
    }

    private void activateMenuButtons() {
        mFloatingInfoButton.setClickable(true);
        mFloatingRestaurantButton.setClickable(true);
        mFloatingHomeButton.setClickable(true);
        menuButtonsFunction();
    }

    private void deactivateMenuButtons() {
        mFloatingInfoButton.setClickable(false);
        mFloatingRestaurantButton.setClickable(false);
        mFloatingHomeButton.setClickable(false);
    }

    private void menuButtonsFunction() {
        mFloatingHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), HotelActivity.class);
                home.putExtra(TAB, "home");
                startActivity(home);
            }
        });

        mFloatingRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurant = new Intent(getApplicationContext(), HotelActivity.class);
                restaurant.putExtra(TAB, "restaurant");
                startActivity(restaurant);
            }
        });

        mFloatingInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent information = new Intent(getApplicationContext(), HotelActivity.class);
                information.putExtra(TAB, "tourism");
                startActivity(information);
            }
        });
    }
}
