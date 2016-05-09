package com.androidapp.startlancer.ui.freelancer.navigation;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class FreelancerProfileAddAboutActivity extends FreelancerBaseActivity {

    private EditText addContactEditText, addGithubAccountEditText, addAboutEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_add_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        addContactEditText = (EditText) findViewById(R.id.edit_text_add_contact);
        addGithubAccountEditText = (EditText) findViewById(R.id.edit_text_add_github_account);
        addAboutEditText = (EditText) findViewById(R.id.edit_text_add_about_yourself);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_about) {
            addAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAbout() {
        String contact = addContactEditText.getText().toString();
        String githubAccount = addGithubAccountEditText.getText().toString();
        String about = addAboutEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.about_coordinator_layout);

        if (!contact.equals("") && !githubAccount.equals("") && !about.equals("")) {
            Firebase profileRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("about");
            Map<String, String> project = new HashMap<>();
            project.put("contact", contact);
            project.put("githubAccout", githubAccount);
            project.put("about", about);
            profileRef.setValue(project);

            Snackbar.make(coordinatorLayout, "About yourself added", Snackbar.LENGTH_SHORT).show();
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
