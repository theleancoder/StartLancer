package com.androidapp.startlancer.ui.startup.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.ui.startup.FreelancerDetailActivity;
import com.androidapp.startlancer.ui.startup.adapters.ApplicationsListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RejectedCandidatesFragment extends Fragment {

    private ListView candidatesList;
    private ApplicationsListAdapter candidatesListAdapter;
    private Firebase ref;


    public RejectedCandidatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_approved_candidates, container, false);

        ref = new Firebase(Constants.FIREBASE_APPLICATION_RESPONSES).child("rejecetd");

        candidatesList = (ListView) rootView.findViewById(R.id.fragment_approved_candidates_list);

        candidatesListAdapter = new ApplicationsListAdapter(getActivity(), Application.class, R.layout.single_freelancer_list_item,
                ref);
        candidatesList.setAdapter(candidatesListAdapter);

        candidatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FreelancerDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.text_view_freelancer_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.text_View_freelancer_email)).getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("email", Utils.encodeEmail(email));
                startActivity(intent);
            }
        });
        return rootView;
    }

}
