package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.freelancer.navigation.FreelancerProfileAddSkillsActivity;

public class StartupProfileOpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile_opening);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Openings");
    }

    public void showAddOpeningsActivity(View view) {
        startActivity(new Intent(StartupProfileOpeningActivity.this, StartupProfileAddOpeningsActivity.class));
        overridePendingTransition(R.xml.slide_up, R.xml.stay);
    }
}
