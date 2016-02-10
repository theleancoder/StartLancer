package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class StartupProfileActivity extends StartupBaseActivity {

    private Firebase firebaseUserRef;
    private ValueEventListener valueEventListener;
    private static final String LOG_TAG = StartupProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView textViewName = (TextView) findViewById(R.id.startup_name_textView);
        final TextView textViewEmail = (TextView) findViewById(R.id.startup_email_textview);

        firebaseUserRef = new Firebase(Constants.FIREBASE_URL_STARTUPS).child(encodedEmail);

        firebaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Startup startup = dataSnapshot.getValue(Startup.class);

                if (startup != null) {
                    String name = startup.getName();
                    String email = startup.getEmail();
                    textViewName.setText(name);
                    textViewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        String[] startupProfileList = {"Openings", "Team", "Work", "About"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.single_profile_item_list, startupProfileList);
        ListView listView = (ListView) findViewById(R.id.startup_profile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(StartupProfileActivity.this, StartupProfileOpeningActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(StartupProfileActivity.this, StartupProfileTeamActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(StartupProfileActivity.this, StartupProfileWorkActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(StartupProfileActivity.this, StartupProfileAboutActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        return;
                }
            }
        });

//        String imageUri = "http://imgur.com/gallery/0JCZXou";
//        ImageView imageView = (ImageView) findViewById(R.id.freelancer_profile_imageView);
//        Picasso.with(getApplicationContext()).load(imageUri).into(imageView);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void uploadImage(View view) {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
