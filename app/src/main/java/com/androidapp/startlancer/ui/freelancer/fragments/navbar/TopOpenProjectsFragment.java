package com.androidapp.startlancer.ui.freelancer.fragments.navbar;


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
import com.androidapp.startlancer.models.OpenProject;
import com.androidapp.startlancer.ui.freelancer.adapters.OpenProjectAdapter;
import com.androidapp.startlancer.ui.freelancer.navigation.OpenProjectDetailActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopOpenProjectsFragment extends Fragment {
    ListView projectList;
    OpenProjectAdapter projectAdapter;

    public TopOpenProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_open_projects, container, false);

        Firebase ref = new Firebase(Constants.FIREBASE_URL_OPEN_PROJECTS);

        projectList = (ListView) rootView.findViewById(R.id.fragment_top_projects_list);

        projectAdapter = new OpenProjectAdapter(getActivity(), OpenProject.class, R.layout.single_project_list_item, ref);

        projectList.setAdapter(projectAdapter);

        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OpenProjectDetailActivity.class);
                String title = ((TextView) view.findViewById(R.id.textview_open_project_title)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.textview_open_project_author)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.textview_open_project_email)).getText().toString();
                intent.putExtra("title", title);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
