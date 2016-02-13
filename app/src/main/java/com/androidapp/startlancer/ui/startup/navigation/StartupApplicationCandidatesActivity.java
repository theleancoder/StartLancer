package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.ApplicationsListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class StartupApplicationCandidatesActivity extends StartupBaseActivity {
    ListView applicantsList;
    ApplicationsListAdapter applicationsListAdapter;
    private String email, name, title;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_startup_application_candidates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = getIntent().getStringExtra("title");

        ref = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(encodedEmail).child(title);

        applicationsListAdapter = new ApplicationsListAdapter(StartupApplicationCandidatesActivity.this, Application.class, R.layout.single_applicant_list,
                ref);
        applicantsList = (ListView) findViewById(R.id.startup_candidates_list);
        applicantsList.setAdapter(applicationsListAdapter);

        applicantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                email = ((TextView) view.findViewById(R.id.textview_candidate_email)).getText().toString();
                name = ((TextView) view.findViewById(R.id.textview_candidate_name)).getText().toString();
                Intent intent = new Intent(StartupApplicationCandidatesActivity.this, StartupCandidateDetailActivity.class);
                intent.putExtra("userEmail", email);
                intent.putExtra("userName", name);
                startActivity(intent);
            }
        });
    }
}
