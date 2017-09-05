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

import com.example.skolen.hotelapplikasjon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSignUpButton;

    private EditText mEditRegisterEmail;
    private EditText mEditRegisterPassword;
    private EditText mEditConfirmPassword;

    private TextView mTextAlreadyRegistered;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;

    String email, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        initializeViews();

        mSignUpButton.setOnClickListener(this);
        mTextAlreadyRegistered.setOnClickListener(this);
    }

    private void initializeViews() {
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mEditRegisterEmail = (EditText) findViewById(R.id.editTextEmail);
        mTextAlreadyRegistered = (TextView) findViewById(R.id.sign_in_here);
        mEditRegisterPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
    }


    private void getUserInformation() {
        email = mEditRegisterEmail.getText().toString().trim();
        password = mEditRegisterPassword.getText().toString().trim();
        confirmPassword = mEditConfirmPassword.getText().toString().trim();
    }

    private boolean validateUserInformation() {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show();

            return false;
        }

        mProgressDialog.setMessage("Registering User...");
        mProgressDialog.show();

        return true;
    }

    private void registerUser() {
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.hide();
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Could not register... please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view  == mSignUpButton) {
            getUserInformation();

            if (validateUserInformation()) {
                registerUser();
            }
        }

        if (view == mTextAlreadyRegistered) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }
}
