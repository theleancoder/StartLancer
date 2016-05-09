package com.androidapp.startlancer.ui.freelancer.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;

public class FreelancerProfileProjectsActivity extends FreelancerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.freelancer_projects_fab);
    }

    public void showAddProjectsActivity(View view) {
        startActivity(new Intent(FreelancerProfileProjectsActivity.this, FreelancerProfileAddProjectsActivity.class));
        overridePendingTransition(R.xml.slide_up, R.xml.stay);
    }
}
