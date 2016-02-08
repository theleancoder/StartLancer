package com.androidapp.startlancer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.startup.CreateStartupAccountActivity;
import com.androidapp.startlancer.ui.startup.LoginStartupActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

/**
 * Created by ankit on 2/6/2016.
 */
public class StartupBaseActivity extends AppCompatActivity {
    protected String provider, encodedEmail;
    protected Firebase firebaseRef;
    protected Firebase.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(StartupBaseActivity.this);
        encodedEmail = sp.getString(Constants.KEY_ENCODED_EMAIL_STARTUP, null);
        provider = sp.getString(Constants.KEY_PROVIDER_STARTUP, null);

        if (!((this instanceof LoginStartupActivity) || (this instanceof CreateStartupAccountActivity))) {
            firebaseRef = new Firebase(Constants.FIREBASE_URL);
            authStateListener = new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    if (authData == null) {
                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(Constants.KEY_ENCODED_EMAIL_STARTUP, null);
                        spe.putString(Constants.KEY_PROVIDER_STARTUP, null);

                        takeUserToStartupLoginScreenOnUnauth();
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

    private void takeUserToStartupLoginScreenOnUnauth() {
        Intent intent = new Intent(StartupBaseActivity.this, LoginStartupActivity
                .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    protected void logout() {

        /* Logout if mProvider is not null */
        if (provider != null) {
            firebaseRef.unauth();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!((this instanceof LoginStartupActivity) || (this instanceof CreateStartupAccountActivity))) {
            firebaseRef.removeAuthStateListener(authStateListener);
        }
    }
}
