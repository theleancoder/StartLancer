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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFreelancersFragment extends Fragment {
    private ListView freelancerList;
    private FreelancerListAdapter freelancerListAdapter;
    private int trendingCount;
    private Firebase firebaseRef;
    private Date date;


    public TrendingFreelancersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trending_freelancers, container, false);

        firebaseRef = new Firebase(Constants.FIREBASE_URL_USERS);
        Query queryRef = firebaseRef.orderByChild("trendingCount").limitToFirst(100);

        freelancerList = (ListView) rootView.findViewById(R.id.fragment_trending_freelancers_list);

        freelancerListAdapter = new FreelancerListAdapter(getActivity(), Freelancer.class, R.layout.single_freelancer_list_item,
                queryRef);
        freelancerList.setAdapter(freelancerListAdapter);

        freelancerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FreelancerDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.text_view_freelancer_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.text_View_freelancer_email)).getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("email", Utils.encodeEmail(email));

                final Firebase ref2 = new Firebase(Constants.FIREBASE_URL_USERS).child(Utils.encodeEmail(email));

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Freelancer freelancer = dataSnapshot.getValue(Freelancer.class);
                        trendingCount = freelancer.getTrendingCount();
                        date = freelancer.getDate();
                        if ((getDifferenceDays(new Date(), date) == 7)) {
                            ref2.child("trendingCount").setValue(0);
                        } else {
                            ref2.child("trendingCount").setValue(trendingCount - 1);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                startActivity(intent);
            }
        });
        return rootView;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
