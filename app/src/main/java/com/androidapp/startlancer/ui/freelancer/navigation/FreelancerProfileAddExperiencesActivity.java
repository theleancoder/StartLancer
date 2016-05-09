package com.androidapp.startlancer.ui.freelancer.navigation;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class FreelancerProfileAddExperiencesActivity extends FreelancerBaseActivity {

    private EditText addExpEditText, addExpDuration, addExpLocationEditText, addExpDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_add_exxperiences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        addExpEditText = (EditText) findViewById(R.id.edit_text_add_experience);
        addExpDuration = (EditText) findViewById(R.id.edit_text_add_experience_duration);
        addExpLocationEditText = (EditText) findViewById(R.id.edit_text_add_experience_location);
        addExpDescriptionEditText = (EditText) findViewById(R.id.edit_text_add_experience_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_experience_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_experience) {
            addExp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addExp() {
        String exp = addExpEditText.getText().toString();
        String expDuration = addExpDuration.getText().toString();
        String expLocation= addExpLocationEditText.getText().toString();
        String expDescription = addExpDescriptionEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.experience_coordinator_layout);

        if (!exp.equals("") && !expDuration.equals("") && !expLocation.equals("") && !expDescription.equals("")) {
            Firebase ref = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("experiences");
            Map<String, String> experience = new HashMap<>();
            experience.put("exp", exp);
            experience.put("expLocation", expLocation);
            experience.put("expDuration", expDuration);
            experience.put("expDescription", expDescription);
            ref.push().setValue(experience);

            Snackbar.make(coordinatorLayout, "Experience added", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(coordinatorLayout, "Please add all the entries", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.xml.stay, R.xml.slide_down);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
