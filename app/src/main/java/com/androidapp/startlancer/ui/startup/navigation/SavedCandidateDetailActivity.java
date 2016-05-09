package com.androidapp.startlancer.ui.startup.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerDetailPagerAdaper;
import com.androidapp.startlancer.ui.startup.fragments.FreelancerDetailFragmentAbout;
import com.androidapp.startlancer.ui.startup.fragments.FreelancerDetailFragmentExperiences;
import com.androidapp.startlancer.ui.startup.fragments.FreelancerDetailFragmentProjects;
import com.androidapp.startlancer.ui.startup.fragments.FreelancerDetailFragmentSkills;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Firebase;

public class SavedCandidateDetailActivity extends AppCompatActivity {
    private String name, email;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_candidate_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");
        setTitle(name);
        email = getIntent().getStringExtra("email");
        bundle = new Bundle();
        bundle.putString("email", email);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.candidate_detail_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.candidate_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saved_candidate_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (id == R.id.approve_candidate) {
            approveCandidate();
        }

        if (id == R.id.reject_candidate) {
            rejectCandidate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void approveCandidate() {

        Firebase ref = new Firebase(Constants.FIREBASE_APPLICATION_RESPONSES).child("approved");
        Application application = new Application(name, email);
        ref.push().setValue(application);

        String email = getIntent().getStringExtra("email");
        String decodedEmail = Utils.decodeEmail(email);
        String[] emails = {decodedEmail};
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "We are really glad to tell you that...");
        startActivity(Intent.createChooser(emailIntent, "Complete action using:"));
    }

    private void rejectCandidate() {

        Firebase ref = new Firebase(Constants.FIREBASE_APPLICATION_RESPONSES).child("rejected");
        Application application = new Application(name, email);
        ref.push().setValue(application);

        String email = getIntent().getStringExtra("email");
        String decodedEmail = Utils.decodeEmail(email);
        String[] emails = {decodedEmail};
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "We are really sorry to tell you that...");
        startActivity(Intent.createChooser(emailIntent, "Complete action using:"));
    }

    private void setupViewPager(ViewPager viewPager) {
        FreelancerDetailPagerAdaper adapter = new FreelancerDetailPagerAdaper(getSupportFragmentManager());
        Fragment skills = new FreelancerDetailFragmentSkills();
        skills.setArguments(bundle);
        adapter.addFragment(skills, "Skills");
        Fragment experiences = new FreelancerDetailFragmentExperiences();
        experiences.setArguments(bundle);
        adapter.addFragment(experiences, "Experiences");
        Fragment projects = new FreelancerDetailFragmentProjects();
        projects.setArguments(bundle);
        adapter.addFragment(projects, "Projects");
        Fragment about = new FreelancerDetailFragmentAbout();
        about.setArguments(bundle);
        adapter.addFragment(about, "About");
        viewPager.setAdapter(adapter);
    }

}
