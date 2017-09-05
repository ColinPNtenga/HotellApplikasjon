package com.example.skolen.hotelapplikasjon.Customer;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.skolen.hotelapplikasjon.Adapter.RoomAdapter;
import com.example.skolen.hotelapplikasjon.Firebase.Entities.Customer;
import com.example.skolen.hotelapplikasjon.Model.Room;
import com.example.skolen.hotelapplikasjon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BookingActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextRoom;
    private EditText mEditTextMobile;
    private EditText mEditTextToDate;
    private EditText mEditTextFromDate;


    private Button mCheckInButton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private String name, email, fromDate, toDate;

    private int mobile;
    private int room;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        initializeViews();

        getRoomNumber();

        checkIn();

    }

    private void initializeViews() {
        mEditTextName = (EditText) findViewById(R.id.customerName);
        mEditTextEmail = (EditText) findViewById(R.id.customerEmail);
        mEditTextMobile = (EditText) findViewById(R.id.customerMobile);
        mEditTextRoom = (EditText) findViewById(R.id.customerRoom);
        mEditTextFromDate = (EditText) findViewById(R.id.fromDate);
        mEditTextToDate = (EditText) findViewById(R.id.toDate);
        mCheckInButton = (Button) findViewById(R.id.check_in_button);

    }

    private void getRoomNumber() {
        Intent intent = getIntent();
        int room = intent.getIntExtra("ROOM", 0);
        mEditTextRoom.setText(Integer.toString(room));
    }

    private void checkIn() {
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomerInfo();

                if (verifyCustomerInfo()) {
                    submitCustomerInfo();
                    startActivity(new Intent(BookingActivity.this, ProfileActivity.class));
                } else {
                    return;
                }
            }
        });
    }

    private void getCustomerInfo() {
        name = mEditTextName.getText().toString().trim();
        email = mEditTextEmail.getText().toString().trim();

        String temp = mEditTextMobile.getText().toString().trim();
        if (temp != null && (!temp.isEmpty())) {
            mobile = Integer.parseInt(temp);
        }

        room = Integer.parseInt(mEditTextRoom.getText().toString().trim());
        fromDate = mEditTextFromDate.getText().toString().trim();
        toDate = mEditTextToDate.getText().toString().trim();
    }

    private boolean verifyCustomerInfo() {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(name) && mobile == 0 && room == 0) {
            Toast.makeText(this, "You must fill the form!!", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (mobile == 0) {
            Toast.makeText(this, "Please enter your number!", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (room == 0) {
            Toast.makeText(this, "Please enter your room number!", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (TextUtils.isEmpty(fromDate)) {
            Toast.makeText(this, "Please enter your check in date!", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (TextUtils.isEmpty(toDate)) {
            Toast.makeText(this, "Please enter your check out date!", Toast.LENGTH_SHORT).show();

            return false;

        }

        return true;
    }

    private void submitCustomerInfo() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user != null) {

            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setMobile(mobile);
            customer.setRoomNumber(room);
            customer.setFromDate(fromDate);
            customer.setToDate(toDate);

            mDatabaseReference.child("Customers").child(user.getUid()).push().setValue(customer);
        }
    }
}
