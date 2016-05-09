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

public class StartupProfileAddTeamActivity extends StartupBaseActivity {

    private EditText memberNameEditText, memberEmailEditText, memberPositionEditText, memberContactEditText,
            memberAboutEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile_add_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_18dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        memberNameEditText = (EditText) findViewById(R.id.edit_text_add_member_name);
        memberEmailEditText = (EditText) findViewById(R.id.edit_text_add_member_email);
        memberPositionEditText = (EditText) findViewById(R.id.edit_text_add_member_position);
        memberContactEditText = (EditText) findViewById(R.id.edit_text_add_member_contact);
        memberAboutEditText = (EditText) findViewById(R.id.edit_text_add_member_about);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_member) {
            addMember();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMember() {
        String name = memberNameEditText.getText().toString();
        String email = memberEmailEditText.getText().toString();
        String position = memberPositionEditText.getText().toString();
        String contact = memberContactEditText.getText().toString();
        String about = memberAboutEditText.getText().toString();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.team_coordinator_layout);

        if (!name.equals("") && !email.equals("") && !position.equals("") && !contact.equals("") && !about.equals("")) {
            Firebase ref = new Firebase(Constants.FIREBASE_URL_STARTUP_MEMBERS).child(encodedEmail);

            Map<String, String> member = new HashMap<>();
            member.put("name", name);
            member.put("email", email);
            member.put("position", position);
            member.put("contact", contact);
            member.put("about", about);

            ref.push().setValue(member);
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
