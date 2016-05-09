package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Opening;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.OpeningsListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class StartupApplicationsActivity extends StartupBaseActivity {

    ListView openingsList;
    OpeningsListAdapter openingsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_applications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
        openingsListAdapter = new OpeningsListAdapter(StartupApplicationsActivity.this, Opening.class, R.layout.single_opening_list_item,
                firebaseRef);
        openingsList = (ListView) findViewById(R.id.startup_applications_list);
        openingsList.setAdapter(openingsListAdapter);

        openingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.single_opening_title)).getText().toString();
                Intent intent = new Intent(StartupApplicationsActivity.this, StartupApplicationCandidatesActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

    }
}
