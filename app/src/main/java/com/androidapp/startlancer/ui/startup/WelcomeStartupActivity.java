package com.androidapp.startlancer.ui.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerListAdapter;
import com.androidapp.startlancer.ui.startup.navigation.SampleActivity;
import com.androidapp.startlancer.ui.startup.navigation.StartupProfileActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class WelcomeStartupActivity extends BaseActivity {

    ListView freelancerList;
    FreelancerListAdapter freelancerListAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Firebase startupRef;
    private ValueEventListener startupRefListener;
    private static final String LOG_TAG = WelcomeStartupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_welcome_startup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_USERS);
        startupRef = new Firebase(Constants.FIREBASE_URL_STARTUPS).child(encodedEmail);

        startupRefListener = startupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Startup startup = dataSnapshot.getValue(Startup.class);

                if (startup != null) {
                    String name = startup.getName();
                    setTitle(name);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        freelancerList = (ListView) findViewById(R.id.freelancer_list);

        freelancerListAdapter = new FreelancerListAdapter(WelcomeStartupActivity.this, Freelancer.class, R.layout.single_freelancer_list,
                firebaseRef);
        freelancerList.setAdapter(freelancerListAdapter);

        freelancerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(WelcomeStartupActivity.this, FreelancerDetailActivity.class);
                intent.putExtra("title", selected);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
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
//        Fragment fragment = null;
//
//        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_item:
                Intent firstIntent = new Intent(WelcomeStartupActivity.this, SampleActivity.class);
                startActivity(firstIntent);
                break;
            case R.id.nav_second_item:
                Intent secondIntent = new Intent(WelcomeStartupActivity.this, SampleActivity.class);
                startActivity(secondIntent);
                break;
            case R.id.nav_third_item:
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

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

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

}
