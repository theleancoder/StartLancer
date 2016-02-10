package com.androidapp.startlancer.ui.startup;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpeningDetail;
import com.androidapp.startlancer.ui.StartupBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class StartupOpeningDetailActivity extends StartupBaseActivity {
    private OpeningDetail detail;
    Object o;

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

        String opening = getIntent().getStringExtra("title");
        setTitle(opening);
        String email = getIntent().getStringExtra("email");

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
        System.out.print(detail);
    }
}
