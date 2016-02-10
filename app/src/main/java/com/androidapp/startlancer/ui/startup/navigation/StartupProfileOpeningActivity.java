package com.androidapp.startlancer.ui.startup.navigation;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpeningDetail;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.OpeningListAdapter;
import com.androidapp.startlancer.ui.startup.fragments.dialog.AddStartupOpeningFragment;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class StartupProfileOpeningActivity extends StartupBaseActivity {
    ListView openingList;
    OpeningListAdapter openingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_startupr_profile_opening);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startup_opening_fab);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
        openingListAdapter = new OpeningListAdapter(StartupProfileOpeningActivity.this, OpeningDetail.class, R.layout.single_opening_list,
                firebaseRef);
        openingList = (ListView) findViewById(R.id.startup_openings_list);
        openingList.setAdapter(openingListAdapter);

        openingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.textview_opening_title)).getText().toString();
                Intent intent = new Intent(StartupProfileOpeningActivity.this, StartupProfileOpeningDetailActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

    }

    public void showAddOpeningsFragment(View view) {
        DialogFragment dialog = AddStartupOpeningFragment.newInstance(encodedEmail);
        dialog.show(StartupProfileOpeningActivity.this.getFragmentManager(), "AddStartupOpeningFragment");
    }
}
