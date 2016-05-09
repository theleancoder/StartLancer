package com.androidapp.startlancer.ui.startup.navigation;

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
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class StartupProfileAddOpeningsActivity extends StartupBaseActivity {

    private EditText openingEditText, salaryEditText, descriptionEditText, responsibilitiesEditText,
    requirementsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile_add_openings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        openingEditText = (EditText) findViewById(R.id.edit_text_add_opening);
        salaryEditText = (EditText) findViewById(R.id.edit_text_add_salary);
        descriptionEditText = (EditText) findViewById(R.id.edit_text_add_job_description);
        responsibilitiesEditText = (EditText) findViewById(R.id.edit_text_add_job_responsibilities);
        requirementsEditText = (EditText) findViewById(R.id.edit_text_add_job_requirements);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_opening_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_opening) {
            addOpening();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addOpening() {
        String openingTitle = openingEditText.getText().toString();
        String salary = salaryEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String responsibilities = responsibilitiesEditText.getText().toString();
        String requirements = requirementsEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.opening_coordinator_layout);

        if(!openingTitle.equals("") && !salary.equals("") && !description.equals("") && !responsibilities.equals("")
                && !requirements.equals("")) {
            Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
            Map<String, String> opening = new HashMap<>();
            opening.put("title", openingTitle);
            opening.put("salary", salary);
            opening.put("description", description);
            opening.put("requirements", requirements);
            opening.put("responsibilities", responsibilities);

            ref.child(openingTitle).setValue(opening);

            Snackbar.make(coordinatorLayout, "Opening added", Snackbar.LENGTH_SHORT).show();
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
