package com.androidapp.startlancer.ui.freelancer.fragments.navbar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.startlancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingCofoundersFragment extends Fragment {


    public TrendingCofoundersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending_cofounders, container, false);
    }

}
