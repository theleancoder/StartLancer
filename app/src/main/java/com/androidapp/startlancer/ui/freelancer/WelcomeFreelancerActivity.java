package com.androidapp.startlancer.ui.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.WelcomeFreelancerPagerAdapter;
import com.androidapp.startlancer.ui.freelancer.fragments.StartupsCategoryFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.TopStartupsFragment;
import com.androidapp.startlancer.ui.freelancer.fragments.TrendingStartupsFragment;
import com.androidapp.startlancer.ui.freelancer.navigation.FreelancerProfileActivity;
import com.androidapp.startlancer.ui.startup.navigation.SampleActivity;
import com.firebase.client.Firebase;

public class WelcomeFreelancerActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_welcome_freelancer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Intent firstIntent = new Intent(WelcomeFreelancerActivity.this, SampleActivity.class);
                startActivity(firstIntent);
                break;
            case R.id.nav_open_projects:
                Intent secondIntent = new Intent(WelcomeFreelancerActivity.this, SampleActivity.class);
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
        WelcomeFreelancerPagerAdapter adapter = new WelcomeFreelancerPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopStartupsFragment(), "Top");
        adapter.addFragment(new TrendingStartupsFragment(), "Trending");
        adapter.addFragment(new StartupsCategoryFragment(), "Category");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }
}
