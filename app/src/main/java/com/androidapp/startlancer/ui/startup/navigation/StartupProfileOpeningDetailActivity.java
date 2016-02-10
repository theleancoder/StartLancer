package com.androidapp.startlancer.ui.startup.navigation;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.fragments.dialog.AddStartupOpeningDetailFragment;

public class StartupProfileOpeningDetailActivity extends StartupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile_opening_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void showAddOpeningDetail(View view) {
        String title = getIntent().getStringExtra("title");
        DialogFragment dialog = AddStartupOpeningDetailFragment.newInstance(encodedEmail, title);
        dialog.show(StartupProfileOpeningDetailActivity.this.getFragmentManager(), "AddStartupOpeningDetailFragment");
    }
}
