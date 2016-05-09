package com.androidapp.startlancer.ui.startup.navigation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.startup.adapters.WelcomeStartupPagerAdapter;
import com.androidapp.startlancer.ui.startup.fragments.ApprovedCandidatesFragment;
import com.androidapp.startlancer.ui.startup.fragments.FreelancersSkillsFragment;
import com.androidapp.startlancer.ui.startup.fragments.RejectedCandidatesFragment;
import com.androidapp.startlancer.ui.startup.fragments.TopFreelancersFragment;
import com.androidapp.startlancer.ui.startup.fragments.TrendingFreelancersFragment;

public class StartupResponsesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_responses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Responses");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.startup_responses_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.startup_responses_tabs);
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
        WelcomeStartupPagerAdapter adapter = new WelcomeStartupPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ApprovedCandidatesFragment(), "Approved");
        adapter.addFragment(new RejectedCandidatesFragment(), "Rejected");
        viewPager.setAdapter(adapter);
    }

}
