package com.androidapp.startlancer.ui.freelancer.fragments.navbar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.WelcomeFreelancerPagerAdapter;
import com.androidapp.startlancer.ui.freelancer.fragments.dialogs.AddAnOpenProjectFragment;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class OpenProjectsActivityFreelancer extends FreelancerBaseActivity {

    private static final String LOG_TAG = OpenProjectsActivityFreelancer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.open_projects_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.open_projects_tabs);
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

    private void setupViewPager(ViewPager viewPager) {
        WelcomeFreelancerPagerAdapter adapter = new WelcomeFreelancerPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopOpenProjectsFragment(), "Top");
        adapter.addFragment(new TrendingOpenProjectsFragment(), "Trending");
        adapter.addFragment(new CategoryOpenProjects(), "Category");
        adapter.addFragment(new MyOpenProjectsFragment(), "My Projects");
        viewPager.setAdapter(adapter);
    }

    public void showAddAnOpenProject(View view) {
        Firebase ref = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    String name = freelancer.getName();
                    String email = freelancer.getEmail();

                    DialogFragment dialogFragment = AddAnOpenProjectFragment.newInstance(name, email);
                    dialogFragment.show(OpenProjectsActivityFreelancer.this.getFragmentManager(), "AddAnOpenProjectFragment");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.error_user_data_read_failed) +
                                firebaseError.getMessage());
            }
        });
    }
}

