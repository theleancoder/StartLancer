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

public class FreelancerProfileAddProjectsActivity extends FreelancerBaseActivity {

    private EditText addProjectTitleEditText, addProjectLinksEditText, addProjectDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_add_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        addProjectTitleEditText = (EditText) findViewById(R.id.edit_text_add_proect_title);
        addProjectLinksEditText = (EditText) findViewById(R.id.edit_text_add_project_links);
        addProjectDescriptionEditText = (EditText) findViewById(R.id.edit_text_add_project_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_project_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_project) {
            addProject();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addProject() {

        String projectTitle = addProjectTitleEditText.getText().toString();
        String projectLinks = addProjectLinksEditText.getText().toString();
        String projectDescription = addProjectDescriptionEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.project_coordinator_layout);

        if (!projectTitle.equals("") && !projectLinks.equals("") && !projectDescription.equals("")) {
            Firebase profileRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("projects");
            Map<String, String> project = new HashMap<>();
            project.put("project", projectTitle);
            project.put("projectLinks", projectLinks);
            project.put("projectDescription", projectDescription);
            profileRef.push().setValue(project);

            Snackbar.make(coordinatorLayout, "Project added", Snackbar.LENGTH_SHORT).show();
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
