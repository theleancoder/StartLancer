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

public class FreelancerProfileAddSkillsActivity extends FreelancerBaseActivity {

    private EditText addSkillEditText, addExpEditText, adduseCasesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile_add_skills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        addSkillEditText = (EditText) findViewById(R.id.edit_text_add_skill);
        addExpEditText = (EditText) findViewById(R.id.edit_text_add_exp);
        adduseCasesEditText = (EditText) findViewById(R.id.edit_text_add_use_cases);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_skill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_skill) {
            addSkill();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addSkill() {

        String skillType = addSkillEditText.getText().toString();
        String yearsOfExp = addExpEditText.getText().toString();
        String useCases = adduseCasesEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.skill_coordinator_layout);

        if (!skillType.equals("") && !yearsOfExp.equals("") && !useCases.equals("")) {
            Firebase profileRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("skills");
            Firebase skillRef = new Firebase(Constants.FIREBASE_URL_SKILLS).child(skillType);
            Map<String, String> skill = new HashMap<>();
            skill.put("skillType", skillType);
            skill.put("yearsOfExperience", yearsOfExp);
            skill.put("useCases", useCases);
            profileRef.push().setValue(skill);
            skillRef.push().setValue(encodedEmail);

            Snackbar.make(coordinatorLayout, "Skill added", Snackbar.LENGTH_SHORT).show();
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
