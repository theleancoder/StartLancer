package com.androidapp.startlancer.ui.startup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.startlancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreelancersSkillsFragment extends Fragment {


    public FreelancersSkillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_freelancers_skills, container, false);
    }

}
