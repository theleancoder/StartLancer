package com.androidapp.startlancer.ui.startup.navigation.navbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpeningDetail;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.OpeningListAdapter;
import com.androidapp.startlancer.ui.startup.navigation.StartupApplicationCandidatesActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class StartupApplicationsActivity extends StartupBaseActivity {

    ListView openingList;
    OpeningListAdapter openingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_startup_applications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
        openingListAdapter = new OpeningListAdapter(StartupApplicationsActivity.this, OpeningDetail.class, R.layout.single_opening_list,
                firebaseRef);
        openingList = (ListView) findViewById(R.id.startup_applications_list);
        openingList.setAdapter(openingListAdapter);

        openingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.textview_opening_title)).getText().toString();
                Intent intent = new Intent(StartupApplicationsActivity.this, StartupApplicationCandidatesActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

    }


}
