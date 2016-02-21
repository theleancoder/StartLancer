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
import com.androidapp.startlancer.models.Freelancer;
import com.androidapp.startlancer.ui.startup.FreelancerDetailActivity;
import com.androidapp.startlancer.ui.startup.adapters.FreelancerListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFreelancersFragment extends Fragment {
    ListView freelancerList;
    FreelancerListAdapter freelancerListAdapter;
    private int topCount;
    Firebase firebaseRef;


    public TopFreelancersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_freelancers, container, false);

        firebaseRef = new Firebase(Constants.FIREBASE_URL_USERS);

        freelancerList = (ListView) rootView.findViewById(R.id.fragment_top_freelancers_list);

        freelancerListAdapter = new FreelancerListAdapter(getActivity(), Freelancer.class, R.layout.single_freelancer_list,
                firebaseRef);
        freelancerList.setAdapter(freelancerListAdapter);

        freelancerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FreelancerDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.freelancer_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.freelancer_email)).getText().toString();

//                Firebase topCountRef = firebaseRef.child(email);
//                HashMap<String, Integer> topCountMap = new HashMap<String, Integer>();
//                topCount.put("topCount", topCount++);

                intent.putExtra("name", name);
                intent.putExtra("email", Utils.encodeEmail(email));
                startActivity(intent);
            }
        });
        return rootView;
    }

}
