package com.androidapp.startlancer.ui.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.freelancer.adapters.StartupListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class WelcomeFreelancerActivity extends AppCompatActivity {

    ListView startupList;
    StartupListAdapter startupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_welcome_freelancer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Top Startups");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_STARTUPS);
        startupList = (ListView) findViewById(R.id.startup_list);

        startupListAdapter = new StartupListAdapter(WelcomeFreelancerActivity.this, Startup.class, R.layout.single_startup_list,
                firebaseRef);
        startupList.setAdapter(startupListAdapter);

        startupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(WelcomeFreelancerActivity.this, StartupDetailActivity.class);
                intent.putExtra("title", selected);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
