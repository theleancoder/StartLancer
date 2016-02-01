package com.androidapp.startlancer.ui.freelancer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateFreelancerAccountActivity extends AppCompatActivity {
    private static final String LOG_TAG =   CreateFreelancerAccountActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private EditText usernameEditText, emailEditText, passwordEditText;
    private Firebase firebaseRef;
    private String username, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_create_freelancer_account);
        firebaseRef = new Firebase("https://startlancer.firebaseio.com/");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeScreen();
    }

    private void initializeScreen() {
        usernameEditText = (EditText) findViewById(R.id.freelancerSignupUsername);
        emailEditText = (EditText) findViewById(R.id.freelancerSignupEmail);
        passwordEditText = (EditText) findViewById(R.id.freelancerSignupPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.dialog_loading));
        progressDialog.setMessage(getResources().getString(R.string.dialog_message));
        progressDialog.setCancelable(false);
    }

    public void createUser(View view) {
        username = usernameEditText.getText().toString();
        email = emailEditText.getText().toString().toLowerCase();
        password = passwordEditText.getText().toString();

        boolean validUserName = isValidUsername(username);
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password);
        if(!validUserName || !validEmail || !validPassword) return;

        progressDialog.show();

        firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                progressDialog.dismiss();
                Log.i(LOG_TAG, getString(R.string.log_message_create_success));
                createUserInFirebaseHelper();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.d(LOG_TAG, firebaseError + "");
                progressDialog.dismiss();

                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    emailEditText.setError(getString(R.string.error_email_taken));
                } else {
                    showErrorToast(firebaseError.getMessage());
                }
            }
        });
    }

    private void createUserInFirebaseHelper() {
        final String encodedEmail = Utils.encodeEmail(email);
        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS ).child(encodedEmail);

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    HashMap<String, Object> timestampJoined = new HashMap<String, Object>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    Freelancer newFreelancer = new Freelancer(username, encodedEmail, timestampJoined);
                    userLocation.setValue(newFreelancer);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());

            }
        });
    }

    private void showErrorToast(String message) {
        Toast.makeText(CreateFreelancerAccountActivity.this, message, Toast.LENGTH_SHORT);
    }

    private boolean isValidUsername(String username) {
        if(username.equals("")) {
            usernameEditText.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            emailEditText.setError(String.format(getString(R.string.error_email_not_valid),
                    email));
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            passwordEditText.setError(getResources().getString(R.string.error_password_not_valid));
            return false;
        }
        return true;
    }
}
