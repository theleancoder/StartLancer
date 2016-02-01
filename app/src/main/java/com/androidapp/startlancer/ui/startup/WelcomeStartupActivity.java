package com.androidapp.startlancer.ui.startup;

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
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class WelcomeStartupActivity extends AppCompatActivity {

    ListView freelancerList;
    FreelancerListAdapter freelancerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_welcome_startup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Top Freelancers");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_USERS);
        freelancerList = (ListView) findViewById(R.id.freelancer_list);

        freelancerListAdapter = new FreelancerListAdapter(WelcomeStartupActivity.this, Freelancer.class, R.layout.single_freelancer_list,
                firebaseRef);
        freelancerList.setAdapter(freelancerListAdapter);

        freelancerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(WelcomeStartupActivity.this, FreelancerDetailActivity.class);
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
