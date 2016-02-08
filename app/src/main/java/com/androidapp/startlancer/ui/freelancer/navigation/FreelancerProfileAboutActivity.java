package com.androidapp.startlancer.ui.freelancer.navigation;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.ui.freelancer.fragments.dialogs.AddFreelancerAboutFragment;

public class FreelancerProfileAboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAddAboutDialog(View view) {
        DialogFragment dialog = AddFreelancerAboutFragment.newInstance(encodedEmail);
        dialog.show(FreelancerProfileAboutActivity.this.getFragmentManager(), "AddFreelancerAboutFragment");
    }
}
