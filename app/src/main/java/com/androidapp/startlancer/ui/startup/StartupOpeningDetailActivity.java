package com.androidapp.startlancer.ui.startup;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.models.Opening;
import com.androidapp.startlancer.ui.FreelancerBaseActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class StartupOpeningDetailActivity extends FreelancerBaseActivity {
    private Opening opening;
    private String email, openingTitle;
    private Firebase userRef;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_opening_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final TextView textViewsalary = (TextView) findViewById(R.id.text_view_salary);
        final TextView textViewDescription = (TextView) findViewById(R.id.text_view_opening_description);
        final TextView textViewresponsibilities = (TextView) findViewById(R.id.text_view_responsibilities);
        final TextView textViewrequirements = (TextView) findViewById(R.id.text_view_requirements);
        ImageView imageViewOpening = (ImageView) findViewById(R.id.image_view_opening);

        openingTitle = getIntent().getStringExtra("title");
        setTitle(openingTitle);
        email = getIntent().getStringExtra("email");

        char letter = openingTitle.charAt(0);
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(openingTitle);
        TextDrawable drawable = TextDrawable.builder().buildRect(String.valueOf(letter), color);
        imageViewOpening.setImageDrawable(drawable);

        Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(email).child(openingTitle);

        ref.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                opening = dataSnapshot.getValue(Opening.class);
                textViewDescription.setText(opening.getDescription());
                textViewsalary.setText(opening.getSalary());
                textViewresponsibilities.setText(opening.getResponsibilities());
                textViewrequirements.setText(opening.getRequirements());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                if (freelancer != null) {
                    StartupOpeningDetailActivity.this.name = freelancer.getName();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void onApplyPressed(View view) {
        Firebase applicationRef = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(email).child(openingTitle);
        Application application = new Application(name, encodedEmail);
        applicationRef.push().setValue(application);
        Snackbar.make(view, "Application Submitted", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
