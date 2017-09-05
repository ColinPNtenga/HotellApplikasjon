package com.example.skolen.hotelapplikasjon.Customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skolen.hotelapplikasjon.Hotel.HotelActivity;
import com.example.skolen.hotelapplikasjon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserEmail;
    private EditText mUserPassword;

    private TextView mTextNotRegistered;

    private Button mLoginButton;

    private FirebaseAuth mFirebaseAuth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        initializeViews();

        mLoginButton.setOnClickListener(this);
        mTextNotRegistered.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == mLoginButton) {
            userLogin();
        }

        if (view == mTextNotRegistered) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void initializeViews() {
        mLoginButton = (Button) findViewById(R.id.login_button);
        mUserEmail = (EditText) findViewById(R.id.UserEmail);
        mUserPassword = (EditText) findViewById(R.id.userPassword);
        mTextNotRegistered = (TextView) findViewById(R.id.sign_up_here);
    }

    private void userLogin() {
        String email = mUserEmail.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();

            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();

            return;
        }

        mProgressDialog.setMessage("Logging in please wait...");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();

                        if (task.isSuccessful()) {
                            mFirebaseAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(LoginActivity.this, HotelActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "User was not found",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
