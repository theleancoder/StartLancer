package com.androidapp.startlancer.ui.startup;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.models.OpeningDetail;
import com.androidapp.startlancer.ui.BaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class StartupOpeningDetailActivity extends BaseActivity {
    private OpeningDetail detail;
    private String email;
    private String opening;
    private Firebase firebaseUserRef;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_startup_opening_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textViewtitle = (TextView) findViewById(R.id.text_view_title);
        final TextView textViewsalary = (TextView) findViewById(R.id.text_view_salary);
        final TextView textViewresponsibilities = (TextView) findViewById(R.id.text_view_responsibility);
        final TextView textViewrequirements = (TextView) findViewById(R.id.text_view_requirements);

        opening = getIntent().getStringExtra("title");
        setTitle(opening);
        email = getIntent().getStringExtra("email");

        Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS_DETAIL).child(email).child(opening);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detail = dataSnapshot.getValue(OpeningDetail.class);
                textViewtitle.setText(detail.getTitle());
                textViewsalary.setText(detail.getSalary());
                textViewresponsibilities.setText(detail.getResponsibilities());
                textViewrequirements.setText(detail.getRequirements());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebaseUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        firebaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    userName = freelancer.getName();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void onApplyPressed(View view) {
        Firebase applicationRef = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(email).child(opening);
        Application application = new Application(userName, encodedEmail);
        applicationRef.child(encodedEmail).setValue(application);
        Snackbar.make(view, "Application Submitted", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
