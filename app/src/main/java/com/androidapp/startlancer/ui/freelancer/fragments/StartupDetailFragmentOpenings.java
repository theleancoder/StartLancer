package com.androidapp.startlancer.ui.freelancer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.models.Opening;
import com.androidapp.startlancer.ui.startup.StartupOpeningDetailActivity;
import com.androidapp.startlancer.ui.startup.adapters.OpeningsListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class StartupDetailFragmentOpenings extends Fragment {

    private ListView openingsList;
    private OpeningsListAdapter openingsListAdapter;
    private Opening opening;
    private Firebase userRef;
    private String name, title;

    private BottomSheetBehavior bottomSheetBehavior;

    public StartupDetailFragmentOpenings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_startup_detail_openings, container, false);
        Firebase.setAndroidContext(getActivity());

        final String email = getArguments().getString("email");

        final Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(Utils.encodeEmail(email));
        openingsListAdapter = new OpeningsListAdapter(getActivity(), Opening.class, R.layout.single_opening_list_item,
                ref);
        openingsList = (ListView) rootView.findViewById(R.id.fragment_openings_list);
        openingsList.setAdapter(openingsListAdapter);

        final View bottomSheet = rootView.findViewById( R.id.bottom_sheet );
        final TextView textViewDescription = (TextView) rootView.findViewById(R.id.text_view_opening_description);
        final TextView textViewRequirements = (TextView) rootView.findViewById(R.id.text_view_opening_requirements);
        final TextView textViewResponsibilties = (TextView) rootView.findViewById(R.id.text_view_opening_responsibilities);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        openingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title = ((TextView) view.findViewById(R.id.single_opening_title)).getText().toString();
                Button applyButton = (Button) rootView.findViewById(R.id.apply_button);

                Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(email).child(title);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        opening = dataSnapshot.getValue(Opening.class);
                        textViewDescription.setText(opening.getDescription());
                        textViewResponsibilties.setText(opening.getResponsibilities());
                        textViewRequirements.setText(opening.getRequirements());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(email);

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);

                        if (freelancer != null) {
                            name = freelancer.getName();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });


                bottomSheetBehavior.setPeekHeight(1000);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            bottomSheetBehavior.setPeekHeight(0);
                        }
                    }

                    @Override
                    public void onSlide(View bottomSheet, float slideOffset) {
                    }
                });

                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.main_content);

                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Firebase applicationRef = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(email).child(title);
                        Application application = new Application(name, email);
                        applicationRef.push().setValue(application);
                        Snackbar.make(coordinatorLayout, "Application Submitted", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                });
            }
        });

        return rootView;
    }
}
