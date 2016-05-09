package com.androidapp.startlancer.ui.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.WelcomeFreelancerPagerAdapter;
import com.androidapp.startlancer.ui.freelancer.fragments.StartupsCategoryFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.TopStartupsFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.TrendingStartupsFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.navbar.OpenProjectsActivityFreelancer;
import com.androidapp.startlancer.ui.freelancer.navigation.CofounderSearchActivity;
import com.androidapp.startlancer.ui.freelancer.navigation.FreelancerProfileActivity;
import com.androidapp.startlancer.ui.startup.navigation.SampleActivity;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WelcomeFreelancerActivity extends FreelancerBaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Firebase ref;
    private static final String LOG_TAG = WelcomeFreelancerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_freelancer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Startups");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.welcome_freelancer_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.welcome_freelancer_tabs);
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

        drawerLayout = (DrawerLayout) findViewById(R.id.freelancer_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.freelancer_nav_drawer);

        setupDrawerContent(navigationView);

        View headerLayout = navigationView.getHeaderView(0);
        ImageView nav_image = (ImageView) headerLayout.findViewById(R.id._freelancer_nav_image);
        final TextView nav_name = (TextView) headerLayout.findViewById(R.id.freelancer_nav_name);
        final TextView nav_email = (TextView) headerLayout.findViewById(R.id.freelancer_nav_email);

        final String decodedEmail = Utils.decodeEmail(encodedEmail);
        String hash = MD5Util.md5Hex(decodedEmail);
        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(this).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).fit().into(nav_image);

        ref = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    String name = freelancer.getName();
                    nav_name.setText(name);
                    nav_email.setText(decodedEmail);
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

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_cofounder_search:
                Intent firstIntent = new Intent(WelcomeFreelancerActivity.this, CofounderSearchActivity.class);
                startActivity(firstIntent);
                break;
            case R.id.nav_open_projects:
                Intent secondIntent = new Intent(WelcomeFreelancerActivity.this, OpenProjectsActivityFreelancer.class);
                startActivity(secondIntent);
                break;
            case R.id.nav_showcase_idea:
                Intent thirdIntent = new Intent(WelcomeFreelancerActivity.this, SampleActivity.class);
                startActivity(thirdIntent);
                break;
            case R.id.nav_profile:
                Intent fourthIntent = new Intent(WelcomeFreelancerActivity.this, FreelancerProfileActivity.class);
                startActivity(fourthIntent);
                break;
            case R.id.nav_settings:
                Intent fifthIntent = new Intent(WelcomeFreelancerActivity.this, SampleActivity.class);
                startActivity(fifthIntent);
                break;
            case R.id.nav_logout:
                firebaseRef.unauth();
                break;
            default:
                return;
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void setupViewPager(ViewPager viewPager) {
        WelcomeFreelancerPagerAdapter freelancerPagerAdapter = new WelcomeFreelancerPagerAdapter(getSupportFragmentManager());
        freelancerPagerAdapter.addFragment(new TopStartupsFragment(), "Top");
        freelancerPagerAdapter.addFragment(new TrendingStartupsFragment(), "Trending");
        freelancerPagerAdapter.addFragment(new StartupsCategoryFragment(), "Category");
        viewPager.setAdapter(freelancerPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
