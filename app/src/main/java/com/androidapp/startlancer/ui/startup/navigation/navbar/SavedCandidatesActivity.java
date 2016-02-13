package com.androidapp.startlancer.ui.startup.navigation.navbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.ui.startup.adapters.ApplicationsListAdapter;
import com.androidapp.startlancer.ui.startup.navigation.SavedCandidateDetailActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class SavedCandidatesActivity extends AppCompatActivity {
    ListView savedCandidatesList;
    ApplicationsListAdapter applicationsListAdapter;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_candidates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref = new Firebase(Constants.FIREBASE_URL_SAVED_CANDIDATES);

        applicationsListAdapter = new ApplicationsListAdapter(SavedCandidatesActivity.this, Application.class, R.layout.single_applicant_list,
                ref);
        savedCandidatesList = (ListView) findViewById(R.id.startup_candidates_list);
        savedCandidatesList.setAdapter(applicationsListAdapter);

        savedCandidatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = ((TextView) view.findViewById(R.id.textview_candidate_email)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.textview_candidate_name)).getText().toString();
                Intent intent = new Intent(SavedCandidatesActivity.this, SavedCandidateDetailActivity.class);
                intent.putExtra("userEmail", email);
                intent.putExtra("userName", name);
                startActivity(intent);
            }
        });
    }

}
