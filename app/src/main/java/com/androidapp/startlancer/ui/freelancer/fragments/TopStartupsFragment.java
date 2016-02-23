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
import com.androidapp.startlancer.models.Startup;
import com.androidapp.startlancer.ui.freelancer.StartupDetailActivity;
import com.androidapp.startlancer.ui.freelancer.adapters.StartupListAdapter;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStartupsFragment extends Fragment {
    ListView startupList;
    StartupListAdapter startupListAdapter;

    public TopStartupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_startups, container, false);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL_STARTUPS);

        startupList = (ListView) rootView.findViewById(R.id.fragment_top_startups_list);

        startupListAdapter = new StartupListAdapter(getActivity(), Startup.class, R.layout.single_startup_list_item,
                firebaseRef);
        startupList.setAdapter(startupListAdapter);

        startupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StartupDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.single_startup_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.single_startup_email)).getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("email", Utils.encodeEmail(email));
                startActivity(intent);
            }
        });
        return rootView;
    }

}
