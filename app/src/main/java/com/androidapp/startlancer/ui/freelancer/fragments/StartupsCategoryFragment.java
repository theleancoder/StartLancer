package com.androidapp.startlancer.ui.freelancer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.startlancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartupsCategoryFragment extends Fragment {


    public StartupsCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_startups_category, container, false);
    }

}
