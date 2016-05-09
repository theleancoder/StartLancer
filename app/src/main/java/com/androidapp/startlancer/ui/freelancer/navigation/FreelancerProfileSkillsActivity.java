package com.androidapp.startlancer.ui.freelancer.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class FreelancerProfileSkillsActivity extends FreelancerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_skills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.freelancer_skill_add_fab);

    }

    public void showAddSkillsActivity(View view) {
        startActivity(new Intent(FreelancerProfileSkillsActivity.this, FreelancerProfileAddSkillsActivity.class));
        overridePendingTransition(R.xml.slide_up, R.xml.stay);
    }
}
