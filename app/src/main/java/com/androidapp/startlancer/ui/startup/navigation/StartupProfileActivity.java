package com.androidapp.startlancer.ui.startup.navigation;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Opening;
import com.androidapp.startlancer.ui.startup.adapters.OpeningListAdapter;
import com.androidapp.startlancer.ui.startup.fragments.AddOpeningFragment;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class StartupProfileActivity extends AppCompatActivity {

    private String data;
    ListView openingList;
    OpeningListAdapter openingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_startup_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getString("encodedEmail");
        }

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(data);
        openingList = (ListView) findViewById(R.id.openings_list);

        openingListAdapter = new OpeningListAdapter(StartupProfileActivity.this, Opening.class, R.layout.single_opening_list,
                firebaseRef);
        openingList.setAdapter(openingListAdapter);

        openingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(StartupProfileActivity.this, OpeningDetailActivity.class);
                intent.putExtra("title", selected);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startup_profile_fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAddOpeningDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getString("encodedEmail");
        }
        DialogFragment dialog = AddOpeningFragment.newInstance(data);
        dialog.show(StartupProfileActivity.this.getFragmentManager(), "AddOpeningFragment");
    }
}
