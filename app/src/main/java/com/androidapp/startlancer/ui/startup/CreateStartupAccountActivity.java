package com.androidapp.startlancer.ui.startup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateStartupAccountActivity extends StartupBaseActivity {
    private static final String LOG_TAG =   CreateStartupAccountActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private EditText startupnameEditText, emailEditText, passwordEditText;
    private Firebase firebaseRef;
    private String startupname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_create_startup_account);
        firebaseRef = new Firebase("https://startlancer.firebaseio.com/");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeScreen();
    }

    private void initializeScreen() {
        startupnameEditText = (EditText) findViewById(R.id.startupSignupUsername);
        emailEditText = (EditText) findViewById(R.id.startupSignupEmail);
        passwordEditText = (EditText) findViewById(R.id.startupSignupPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.dialog_loading));
        progressDialog.setMessage(getResources().getString(R.string.dialog_message));
        progressDialog.setCancelable(false);
    }


    public void createStartup(View view) {
        startupname = startupnameEditText.getText().toString();
        email = emailEditText.getText().toString().toLowerCase();
        password = passwordEditText.getText().toString();

        boolean validStartupName = isValidStartupname(startupname);
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password);
        if(!validStartupName || !validEmail || !validPassword) return;

        progressDialog.show();

        firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                progressDialog.dismiss();
                Log.i(LOG_TAG, getString(R.string.log_message_create_success));
                createUserInFirebaseHelper();
                firebaseRef.authWithPassword(email, password, new MyAuthResultHandler(Constants.PASSWORD_PROVIDER));

//                Intent intent = new Intent(CreateStartupAccountActivity.this, WelcomeStartupActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
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
        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_STARTUPS ).child(encodedEmail);

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    HashMap<String, Object> timestampJoined = new HashMap<String, Object>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    Startup newtSartup = new Startup(startupname, encodedEmail, timestampJoined);
                    userLocation.setValue(newtSartup);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());

            }
        });
    }

    private void showErrorToast(String message) {
        Toast.makeText(CreateStartupAccountActivity.this, message, Toast.LENGTH_SHORT);
    }

    private boolean isValidStartupname(String startupname) {
        if(startupname.equals("")) {
            startupnameEditText.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            emailEditText.setError(String.format(getString(R.string.error_email_not_valid) +
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

    public void goToLoginStartup(View view) {
        Intent intent = new Intent(CreateStartupAccountActivity.this, LoginStartupActivity.class);
        startActivity(intent);
    }

    private class MyAuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public MyAuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            progressDialog.dismiss();
            Log.i(LOG_TAG, provider + " " + getString(R.string.log_message_login_successful));
            if (authData != null) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spe = sp.edit();

                setAuthenticatedUserPasswordProvider(authData);

                spe.putString(Constants.KEY_PROVIDER_STARTUP, authData.getProvider()).apply();
//                spe.putString(Constants.KEY_ENCODED_EMAIL_STARTUP, encodedEmail).apply();
                spe.putString(Constants.KEY_ENCODED_EMAIL_STARTUP, encodedEmail).apply();

                /* Go to main activity */
                Intent intent = new Intent(CreateStartupAccountActivity.this, WelcomeStartupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            progressDialog.dismiss();

            switch (firebaseError.getCode()) {
                case FirebaseError.INVALID_EMAIL:
                case FirebaseError.USER_DOES_NOT_EXIST:
                    emailEditText.setError(getString(R.string.error_message_email_issue));
                    break;
                case FirebaseError.INVALID_PASSWORD:
                    passwordEditText.setError(firebaseError.getMessage());
                    break;
                case FirebaseError.NETWORK_ERROR:
                    showErrorToast(getString(R.string.error_message_failed_sign_in_no_network));
                    break;
                default:
                    showErrorToast(firebaseError.toString());
            }
        }
    }

    private void setAuthenticatedUserPasswordProvider(AuthData authData) {
        final String unprocessedEmail = authData.getProviderData().get(Constants.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();

        encodedEmail = Utils.encodeEmail(unprocessedEmail);
    }

}
