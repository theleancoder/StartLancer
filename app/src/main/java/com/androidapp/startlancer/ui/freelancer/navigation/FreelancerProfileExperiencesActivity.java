package com.androidapp.startlancer.ui.freelancer.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;

public class FreelancerProfileExperiencesActivity extends FreelancerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_experiences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.freelancer_experiences_fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void showAddExperienceActivity(View view) {
        startActivity(new Intent(FreelancerProfileExperiencesActivity.this, FreelancerProfileAddExperiencesActivity.class));
        overridePendingTransition(R.xml.slide_up, R.xml.stay);
    }
}
