package com.androidapp.startlancer.ui.freelancer.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;

public class FreelancerProfileAboutActivity extends FreelancerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAddAboutActivity(View view) {
        startActivity(new Intent(FreelancerProfileAboutActivity.this, FreelancerProfileAddAboutActivity.class));
        overridePendingTransition(R.xml.slide_up, R.xml.stay);
    }
}
