package com.androidapp.startlancer.ui.freelancer.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FreelancerProfileActivity extends FreelancerBaseActivity {

    private Firebase firebaseUserRef;
    private static final String LOG_TAG = FreelancerProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textViewFreelancerEmail = (TextView) findViewById(R.id.text_view_freelancer_email);
        ImageView imageViewFreelancer = (ImageView) findViewById(R.id.image_freelancer_profile);

        final String decodedEmail = Utils.decodeEmail(encodedEmail);
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(this).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(imageViewFreelancer);

        firebaseUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        firebaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    String email = freelancer.getEmail();
                    textViewFreelancerEmail.setText(decodedEmail);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.error_user_data_read_failed) +
                                firebaseError.getMessage());
            }
        });

    }


    public void goToSkillsActivity(View view) {
        Intent skillIntent = new Intent(FreelancerProfileActivity.this, FreelancerProfileSkillsActivity.class);
        startActivity(skillIntent);
    }

    public void goToExperiencesActivity(View view) {
        Intent experienceIntent = new Intent(FreelancerProfileActivity.this, FreelancerProfileExperiencesActivity.class);
        startActivity(experienceIntent);
    }

    public void goToProjectsActivity(View view) {
        Intent projectIntent = new Intent(FreelancerProfileActivity.this, FreelancerProfileProjectsActivity.class);
        startActivity(projectIntent);
    }

    public void goToAboutActivity(View view) {
        Intent aboutIntent = new Intent(FreelancerProfileActivity.this, FreelancerProfileAboutActivity.class);
        startActivity(aboutIntent);
    }
}
