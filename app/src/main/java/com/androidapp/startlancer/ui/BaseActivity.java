package com.androidapp.startlancer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.freelancer.CreateFreelancerAccountActivity;
import com.androidapp.startlancer.ui.freelancer.LoginFreelancerActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by ankit on 29/1/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    protected GoogleApiClient GoogleApiClient;
    protected String provider, encodedEmail;
    protected Firebase firebaseRef;
    protected Firebase.AuthStateListener authStateListener;
    public Uri personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        /* Setup the Google API object to allow Google logins */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        /**
         * Build a GoogleApiClient with access to the Google Sign-In API and the
         * options specified by gso.
         */
        GoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        encodedEmail = sp.getString(Constants.KEY_ENCODED_EMAIL_FREELANCER, null);
        provider = sp.getString(Constants.KEY_PROVIDER_FREELANCER, null);

        if (!((this instanceof LoginFreelancerActivity) || (this instanceof CreateFreelancerAccountActivity))) {
            firebaseRef = new Firebase(Constants.FIREBASE_URL);
            authStateListener = new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    if (authData == null) {
                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(Constants.KEY_ENCODED_EMAIL_FREELANCER, null);
                        spe.putString(Constants.KEY_PROVIDER_FREELANCER, null);

                        takeUserToFreelancerLoginScreenOnUnauth();
                    }
                }
            };
            firebaseRef.addAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void logout() {

        /* Logout if mProvider is not null */
        if (provider != null) {
            firebaseRef.unauth();

            if (provider.equals(Constants.GOOGLE_PROVIDER)) {

                /* Logout from Google+ */
                Auth.GoogleSignInApi.signOut(GoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                //nothing
                            }
                        });
            }
        }
    }

    private void takeUserToFreelancerLoginScreenOnUnauth() {
        Intent intent = new Intent(BaseActivity.this, LoginFreelancerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!((this instanceof LoginFreelancerActivity) || (this instanceof CreateFreelancerAccountActivity))) {
            firebaseRef.removeAuthStateListener(authStateListener);
        }
    }
}
