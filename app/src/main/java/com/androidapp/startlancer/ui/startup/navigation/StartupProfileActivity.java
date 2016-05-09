package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.SquareImageView;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class StartupProfileActivity extends StartupBaseActivity {
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView startupEmail = (TextView) findViewById(R.id.text_view_startup_email);
        startupEmail.setText(Utils.decodeEmail(encodedEmail));

        SquareImageView startupImageView = (SquareImageView) findViewById(R.id.image_startup_profile);

        final String decodedEmail = Utils.decodeEmail(encodedEmail);
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(this).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(startupImageView);

        ref = new Firebase(Constants.FIREBASE_URL_STARTUPS).child(encodedEmail);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Startup startup = dataSnapshot.getValue(Startup.class);

                if (startup != null) {
                    String name = startup.getName();
                    setTitle(name);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
//                Log.e(LOG_TAG,
//                        getString(R.string.error_user_data_read_failed) +
//                                firebaseError.getMessage());
            }
        });
    }

    public void goToOpeningsActivity(View view) {
        Intent openingsIntent = new Intent(StartupProfileActivity.this, StartupProfileOpeningActivity.class);
        startActivity(openingsIntent);
    }

    public void goToTeamActivity(View view) {
        Intent teamIntent = new Intent(StartupProfileActivity.this, StartupProfileTeamActivity.class);
        startActivity(teamIntent);
    }

//    public void goToFundingsActivity(View view) {
//        Intent fundingsIntent = new Intent(StartupProfileActivity.this, StartupProfileFundingsActivity.class);
//    }
//
//    public void goToAboutActivity(View view) {
//        Intent aboutActivity = new Intent(StartupProfileActivity.this, StartupProfileAboutActivity.class);
//        startActivity(aboutActivity);
//    }
}
