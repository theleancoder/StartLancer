package com.androidapp.startlancer.ui.freelancer.navigation;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.ui.freelancer.fragments.dialogs.AddFreelancerProjectsFragment;

public class FreelancerProfileProjectsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.freelancer_projects_fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void showAddProjectsFragment(View view) {
        DialogFragment dialog = AddFreelancerProjectsFragment.newInstance(encodedEmail);
        dialog.show(FreelancerProfileProjectsActivity.this.getFragmentManager(), "AddFreelancerProjectsFragment");
    }
}
