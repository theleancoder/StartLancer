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

public class TopStartupsFragment extends Fragment {

    private ListView startupsList;
    private StartupsListAdapter startupsListAdapter;
    private int topCount;
    private Firebase ref;

    public TopStartupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_startups, container, false);

        ref = new Firebase(Constants.FIREBASE_URL_STARTUPS);
        Query queryRef = ref.orderByChild("topCount").limitToFirst(100);

        startupsList = (ListView) rootView.findViewById(R.id.fragment_top_startups_list);

        startupsListAdapter = new StartupsListAdapter(getActivity(), Startup.class, R.layout.single_startup_list_item,
                queryRef);
        startupsList.setAdapter(startupsListAdapter);

        startupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StartupDetailActivity.class);
                String startupName = ((TextView) view.findViewById(R.id.single_startup_name)).getText().toString();
                String startupEmail = ((TextView) view.findViewById(R.id.single_startup_email)).getText().toString();
                intent.putExtra("name", startupName);
                intent.putExtra("email", Utils.encodeEmail(startupEmail));

                final Firebase ref2 = new Firebase(Constants.FIREBASE_URL_STARTUPS).child(Utils.encodeEmail(startupEmail));

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Startup startup = dataSnapshot.getValue(Startup.class);
                        topCount = startup.getTopCount();
                        ref2.child("topCount").setValue(topCount - 1);
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

}
