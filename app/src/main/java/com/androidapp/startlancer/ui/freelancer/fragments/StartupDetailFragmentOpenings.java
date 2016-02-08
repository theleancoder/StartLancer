package com.androidapp.startlancer.ui.freelancer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Opening;
import com.androidapp.startlancer.ui.startup.adapters.OpeningListAdapter;
import com.androidapp.startlancer.ui.startup.navigation.OpeningDetailActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartupDetailFragmentOpenings extends Fragment {

    ListView openingList;
    OpeningListAdapter openingListAdapter;

    public StartupDetailFragmentOpenings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_startup_detail_openings, container, false);

        String email = getArguments().getString("email");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(email);
        openingListAdapter = new OpeningListAdapter(getActivity(), Opening.class, R.layout.single_opening_list,
                firebaseRef);
        openingList = (ListView) rootView.findViewById(R.id.fragment_openings_list);
        openingList.setAdapter(openingListAdapter);

        openingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(getActivity(), OpeningDetailActivity.class);
                intent.putExtra("title", selected);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
