package com.androidapp.startlancer.ui.startup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Experience;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerExperienceAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreelancerDetailFragmentExperiences extends Fragment {


    private ListView experiencesList;
    private FreelancerExperienceAdapter experiencesAdapter;


    public FreelancerDetailFragmentExperiences() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_freelancer_detail_fragment_experiences, container, false);
        String email = getArguments().getString("email");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(email).child("experiences");
        experiencesAdapter = new FreelancerExperienceAdapter(getActivity(), Experience.class, R.layout.single_freelancer_experience_item,
                firebaseRef);
        experiencesList = (ListView) rootView.findViewById(R.id.fragment_experience_list);
        experiencesList.setAdapter(experiencesAdapter);

        experiencesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final String selected = (String) parent.getSelectedItem();
//                Intent intent = new Intent(getActivity(), OpeningDetailActivity.class);
//                intent.putExtra("title", selected);
//                startActivity(intent);
            }
        });

        return rootView;
    }


}
