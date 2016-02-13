package com.androidapp.startlancer.ui.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.WelcomeStartupPagerAdapter;
import com.androidapp.startlancer.ui.startup.fragments.FreelancersSkillsFragment;
import com.androidapp.startlancer.ui.startup.fragments.TopFreelancersFragment;
import com.androidapp.startlancer.ui.startup.fragments.TrendingFreelancersFragment;
import com.androidapp.startlancer.ui.startup.navigation.SampleActivity;
import com.androidapp.startlancer.ui.startup.navigation.StartupProfileActivity;
import com.androidapp.startlancer.ui.startup.navigation.navbar.SavedCandidatesActivity;
import com.androidapp.startlancer.ui.startup.navigation.navbar.StartupApplicationsActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

public class WelcomeStartupActivity extends StartupBaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_welcome_startup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.welcome_startup_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.welcome_startup_tabs);
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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.startup_nav_drawer);
        setupDrawerContent(navigationView);
    }

    private void setupViewPager(ViewPager viewPager) {
        WelcomeStartupPagerAdapter adapter = new WelcomeStartupPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopFreelancersFragment(), "Top");
        adapter.addFragment(new TrendingFreelancersFragment(), "Trending");
        adapter.addFragment(new FreelancersSkillsFragment(), "Skills");
        viewPager.setAdapter(adapter);
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
//        Fragment fragment = null;
//
//        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_applications:
                Intent firstIntent = new Intent(WelcomeStartupActivity.this, StartupApplicationsActivity.class);
                startActivity(firstIntent);
                break;
            case R.id.nav_responses:
                Intent secondIntent = new Intent(WelcomeStartupActivity.this, SampleActivity.class);
                startActivity(secondIntent);
                break;
            case R.id.nav_saved:
                Intent savedIntent = new Intent(WelcomeStartupActivity.this, SavedCandidatesActivity.class);
                startActivity(savedIntent);
                break;
            case R.id.nav_queries:
                Intent thirdIntent = new Intent(WelcomeStartupActivity.this, SampleActivity.class);
                startActivity(thirdIntent);
                break;
            case R.id.nav_profile:
                Intent fourthIntent = new Intent(WelcomeStartupActivity.this, StartupProfileActivity.class);
                fourthIntent.putExtra("encodedEmail", encodedEmail);
                startActivity(fourthIntent);
                break;
            case R.id.nav_settings:
                Intent fifthIntent = new Intent(WelcomeStartupActivity.this, SampleActivity.class);
                startActivity(fifthIntent);
                break;
            default:
                Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_USERS);
                firebaseRef.unauth();
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
