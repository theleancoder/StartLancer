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

    ListView skillList;
    FreelancerSkillAdapter skillAdapter;


    public FreelancerDetailFragmentSkills() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_freelancer_detail_fragment_skills, container, false);
        String email = getArguments().getString("userEmail");

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(email).child("skills");
        skillAdapter = new FreelancerSkillAdapter(getActivity(), Skill.class, R.layout.single_freelancer_skill_item,
                firebaseRef);
        skillList = (ListView) rootView.findViewById(R.id.fragment_skill_list);
        skillList.setAdapter(skillAdapter);

        skillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
