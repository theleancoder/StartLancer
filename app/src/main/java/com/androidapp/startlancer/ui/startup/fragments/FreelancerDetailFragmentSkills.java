package com.androidapp.startlancer.ui.startup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Skill;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerSkillAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreelancerDetailFragmentSkills extends Fragment {

    private ListView skillsList;
    private FreelancerSkillAdapter skillsAdapter;


    public FreelancerDetailFragmentSkills() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_freelancer_detail_fragment_skills, container, false);
        String email = getArguments().getString("email");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(email).child("skills");
        skillsAdapter = new FreelancerSkillAdapter(getActivity(), Skill.class, R.layout.single_freelancer_skill_item,
                firebaseRef);
        skillsList = (ListView) rootView.findViewById(R.id.fragment_skill_list);
        skillsList.setAdapter(skillsAdapter);

        skillsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        return rootView;
    }

}
