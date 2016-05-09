package com.androidapp.startlancer.ui.freelancer.fragments;


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
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.freelancer.StartupDetailActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.StartupsListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TrendingStartupsFragment extends Fragment {
    private ListView startupsList;
    private StartupsListAdapter startupsListAdapter;
    private int trendingCount;
    private Firebase ref;
    private Date date;

    public TrendingStartupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trending_startups, container, false);

        ref = new Firebase(Constants.FIREBASE_URL_STARTUPS);
        Query queryRef = ref.orderByChild("topCount").limitToFirst(100);

        startupsList = (ListView) rootView.findViewById(R.id.fragment_trending_startups_list);

        startupsListAdapter = new StartupsListAdapter(getActivity(), Startup.class, R.layout.single_startup_list_item,
                queryRef);
        startupsList.setAdapter(startupsListAdapter);

        startupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StartupDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.single_startup_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.single_startup_email)).getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("email", Utils.encodeEmail(email));

                final Firebase ref2 = new Firebase(Constants.FIREBASE_URL_STARTUPS).child(Utils.encodeEmail(email));

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Startup startup = dataSnapshot.getValue(Startup.class);
                        trendingCount = startup.getTrendingCount();
                        date = startup.getDate();
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
