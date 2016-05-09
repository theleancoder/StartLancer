package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.SavedCandidate;
import com.androidapp.startlancer.ui.startup.adapters.SavedCandidateListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class SavedCandidatesActivity extends AppCompatActivity {
    private ListView savedCandidatesList;
    private SavedCandidateListAdapter candidateListAdapter;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_candidates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref = new Firebase(Constants.FIREBASE_URL_SAVED_CANDIDATES);

        candidateListAdapter = new SavedCandidateListAdapter(SavedCandidatesActivity.this, SavedCandidate.class, R.layout.single_saved_candidate_list,
                ref);
        savedCandidatesList = (ListView) findViewById(R.id.saved_candidates_list);
        savedCandidatesList.setAdapter(candidateListAdapter);

        savedCandidatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = ((TextView) view.findViewById(R.id.candidate_email)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.candidate_name)).getText().toString();
                Intent intent = new Intent(SavedCandidatesActivity.this, SavedCandidateDetailActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

}
