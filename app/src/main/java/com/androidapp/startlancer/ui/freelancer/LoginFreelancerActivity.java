package com.androidapp.startlancer.ui.freelancer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;

import java.io.IOException;
import java.util.HashMap;

public class LoginFreelancerActivity extends FreelancerBaseActivity {
    private static final String LOG_TAG = LoginFreelancerActivity.class.getSimpleName();
    private Firebase firebaseRef;
    private EditText emailEditText, passwordEditText;
    private String username, email, password;
    private ProgressDialog progressDialog;
    private int topCount = 0;
    private int trendingCount = 0;
    private LoginButton facebookButton;
    CallbackManager callbackManager;


    private boolean GoogleIntentProgress;
    public static final int RC_GOOGLE_LOGIN = 9001;
    GoogleSignInAccount googleSignInAccount;
    private String facebookName;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_freelancer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callbackManager = CallbackManager.Factory.create();
        facebookButton = (LoginButton) findViewById(R.id.facebook_button);
        facebookButton.setReadPermissions("email");

        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookButton.setVisibility(View.GONE);
                Intent facebookIntent = new Intent(LoginFreelancerActivity.this, WelcomeFreelancerActivity.class);
                startActivity(facebookIntent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        initializeScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        accessTokenTracker.stopTracking();
    }

    private void initializeScreen() {
        emailEditText = (EditText) findViewById(R.id.loginEmail);
        passwordEditText = (EditText) findViewById(R.id.loginPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.startup_login_dialog_message));
        progressDialog.setCancelable(false);

        setupGoogleSignIn();
    }

    public void goToCreateAccount(View view) {
        Intent intent = new Intent(LoginFreelancerActivity.this, CreateFreelancerAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void loginUser(View view) {
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
        firebaseRef.authWithPassword(email, password, new MyAuthResultHandler(Constants.PASSWORD_PROVIDER));

    }

    public class MyAuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public MyAuthResultHandler(String provider) {
            this.provider = provider;
        }


        @Override
        public void onAuthenticated(AuthData authData) {
            progressDialog.dismiss();

            if (authData.getProvider().equals(Constants.GOOGLE_PROVIDER)) {
                /**
                 * If google api client is connected, get the lowerCase user email
                 * and save in sharedPreferences
                 */
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spe = sp.edit();
                String unprocessedEmail;
                if (GoogleApiClient.isConnected()) {
                    unprocessedEmail = googleSignInAccount.getEmail().toLowerCase();
                    spe.putString(Constants.KEY_GOOGLE_EMAIL, unprocessedEmail).apply();
                } else {

                    /**
                     * Otherwise get email from sharedPreferences, use null as default value
                     * (this mean that user resumes his session)
                     */
                    unprocessedEmail = sp.getString(Constants.KEY_GOOGLE_EMAIL, null);
                }
                /**
                 * Encode user email replacing "." with "," to be able to use it
                 * as a Firebase db key
                 */
                encodedEmail = Utils.encodeEmail(unprocessedEmail);


                    /* Get username from authData */
                final String userName = (String) authData.getProviderData().get(Constants.PROVIDER_DATA_DISPLAY_NAME);

                    /* If no user exists, make a user */
                final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);
                userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    /* If nothing is there ...*/
                        if (dataSnapshot.getValue() == null) {
                            HashMap<String, Object> timestampJoined = new HashMap<>();
                            timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                            Freelancer freelancer = new Freelancer(userName, encodedEmail, timestampJoined, topCount, trendingCount);
                            userLocation.setValue(freelancer);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
                    }
                });
            }


            if (authData != null) {
                /* Go to main activity */
                Intent intent = new Intent(LoginFreelancerActivity.this, WelcomeFreelancerActivity.class);
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

    private void showErrorToast(String message) {
        Toast.makeText(LoginFreelancerActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void loginWithGoogle(String token) {
        firebaseRef.authWithOAuthToken(Constants.GOOGLE_PROVIDER, token, new MyAuthResultHandler(Constants.GOOGLE_PROVIDER));
    }

    /* Sets up the Google Sign In Button : https://developers.google.com/android/reference/com/google/android/gms/common/SignInButton */
    private void setupGoogleSignIn() {
        SignInButton signInButton = (SignInButton)findViewById(R.id.login_with_google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInGooglePressed(v);
            }
        });
    }

    /**
     * Sign in with Google plus when user clicks "Sign in with Google" textView (button)
     */
    public void onSignInGooglePressed(View view) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(GoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);
        progressDialog.show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        /**
         * An unresolvable error has occurred and Google APIs (including Sign-In) will not
         * be available.
         */
        progressDialog.dismiss();
        showErrorToast(result.toString());
    }


    /**
     * This callback is triggered when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...); */
        if (requestCode == RC_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LOG_TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            /* Signed in successfully, get the OAuth token */
            googleSignInAccount = result.getSignInAccount();
            getGoogleOAuthTokenAndLogin();


        } else {
            if (result.getStatus().getStatusCode() == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                showErrorToast("The sign in was cancelled. Make sure you're connected to the internet and try again.");
            } else {
                showErrorToast("Error handling the sign in: " + result.getStatus().getStatusMessage());
            }
            progressDialog.dismiss();
        }
    }


    /**
     * Gets the GoogleAuthToken and logs in.
     */
    private void getGoogleOAuthTokenAndLogin() {
        /* Get OAuth token in Background */
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String mErrorMessage = null;

            @Override
            protected String doInBackground(Void... params) {
                String token = null;

                try {
                    String scope = String.format(getString(R.string.oauth2_format), new Scope(Scopes.PROFILE)) + " email";

                    token = GoogleAuthUtil.getToken(LoginFreelancerActivity.this, googleSignInAccount.getEmail(), scope);
                } catch (IOException transientEx) {
                    /* Network or server error */
                    Log.e(LOG_TAG, getString(R.string.google_error_auth_with_google) + transientEx);
                    mErrorMessage = getString(R.string.google_error_network_error) + transientEx.getMessage();
                } catch (UserRecoverableAuthException e) {
                    Log.w(LOG_TAG, getString(R.string.google_error_recoverable_oauth_error) + e.toString());

                    /* We probably need to ask for permissions, so start the intent if there is none pending */
                    if (!GoogleIntentProgress) {
                        GoogleIntentProgress = true;
                        Intent recover = e.getIntent();
                        startActivityForResult(recover, RC_GOOGLE_LOGIN);
                    }
                } catch (GoogleAuthException authEx) {
                    /* The call is not ever expected to succeed assuming you have already verified that
                     * Google Play services is installed. */
                    Log.e(LOG_TAG, " " + authEx.getMessage(), authEx);
                    mErrorMessage = getString(R.string.google_error_auth_with_google) + authEx.getMessage();
                }
                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                progressDialog.dismiss();
                if (token != null) {
                    /* Successfully got OAuth token, now login with Google */
                    loginWithGoogle(token);
                } else if (mErrorMessage != null) {
                    showErrorToast(mErrorMessage);
                }
            }
        };

        task.execute();
    }
}
