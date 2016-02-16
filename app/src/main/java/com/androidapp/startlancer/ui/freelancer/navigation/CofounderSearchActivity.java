package com.androidapp.startlancer.ui.freelancer.navigation;

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
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.WelcomeFreelancerPagerAdapter;
import com.androidapp.startlancer.ui.freelancer.fragments.dialogs.AddAsACofounderFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.navbar.MyCofounderProfileFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.navbar.TopCofoundersFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.navbar.TrendingCofoundersFragment;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class CofounderSearchActivity extends BaseActivity {
    private static final String LOG_TAG = CofounderSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cofounder_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.cofounder_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.cofounder_tabs);
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
        adapter.addFragment(new TopCofoundersFragment(), "Top");
        adapter.addFragment(new TrendingCofoundersFragment(), "Trending");
        adapter.addFragment(new MyCofounderProfileFragment(), "My Profile");
        viewPager.setAdapter(adapter);
    }

    public void addShowAddAsCofounder(View view) {
        Firebase ref = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    String name = freelancer.getName();
                    String email = freelancer.getEmail();

                    DialogFragment dialogFragment = AddAsACofounderFragment.newInstance(name, email);
                    dialogFragment.show(CofounderSearchActivity.this.getFragmentManager(), "AddAsACofounderFragment");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });
    }
}
