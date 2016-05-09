package com.androidapp.startlancer.ui.startup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginStartupActivity extends StartupBaseActivity {

    private static final String LOG_TAG = LoginStartupActivity.class.getSimpleName();
    private Firebase ref;
    private EditText emailEditText, passwordEditText;
    private String email, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_startup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ref = new Firebase(Constants.FIREBASE_URL);

        initializeScreen();

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    signInPassword();
                }
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeScreen() {
        emailEditText = (EditText) findViewById(R.id.edit_text_startup_login_email);
        passwordEditText = (EditText) findViewById(R.id.edit_text_startup_login_password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.startup_login_dialog_message));
        progressDialog.setCancelable(false);
    }

    public void loginStartup(View view) {
        signInPassword();
    }

    public void signInPassword() {
        email = emailEditText.getText().toString().toLowerCase();
        password = passwordEditText.getText().toString();

        if (email.equals("")) {
            emailEditText.setError(getString(R.string.error_email_cannot_be_empty));
            return;
        }

        if (password.equals("")) {
            passwordEditText.setError(getString(R.string.error_password_cannot_be_empty));
            return;
        }

        progressDialog.show();
        ref.authWithPassword(email, password, new MyAuthResultHandler(Constants.PASSWORD_PROVIDER));

    }

    public void goToCreateStartupAccount(View view) {
        Intent intent = new Intent(LoginStartupActivity.this, CreateStartupAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private class MyAuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public MyAuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            Log.i(LOG_TAG, provider + " " + getString(R.string.log_message_login_successful));
            if (authData != null) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spe = sp.edit();

                setAuthenticatedUserPasswordProvider(authData);

                spe.putString(Constants.KEY_PROVIDER_STARTUP, authData.getProvider()).apply();
                spe.putString(Constants.KEY_ENCODED_EMAIL_STARTUP, encodedEmail).apply();

                progressDialog.dismiss();

                Intent intent = new Intent(LoginStartupActivity.this, WelcomeStartupActivity.class);
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
                    emailEditText.setError(getString(R.string.error_user_does_not_exist));
                    break;
                case FirebaseError.INVALID_PASSWORD:
                    passwordEditText.setError(firebaseError.getMessage());
                    break;
                case FirebaseError.NETWORK_ERROR:
                    showErrorToast(getString(R.string.error_no_network_detected));
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

    private void showErrorToast(String message) {
        Toast.makeText(LoginStartupActivity.this, message, Toast.LENGTH_LONG).show();
    }


}