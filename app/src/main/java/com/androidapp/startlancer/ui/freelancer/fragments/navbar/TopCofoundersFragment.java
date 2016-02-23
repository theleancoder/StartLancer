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
import com.androidapp.startlancer.models.Cofounder;
import com.androidapp.startlancer.ui.freelancer.adapters.CofounderAdapter;
import com.androidapp.startlancer.ui.freelancer.navigation.CofounderDetailActivity;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopCofoundersFragment extends Fragment {
    ListView cofounderList;
    CofounderAdapter cofounderAdapter;

    public TopCofoundersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_cofounders, container, false);

        Firebase ref = new Firebase(Constants.FIREBASE_URL_COFOUNDERS);

        cofounderList = (ListView) rootView.findViewById(R.id.fragment_top_cofounders_list);

        cofounderAdapter = new CofounderAdapter(getActivity(), Cofounder.class, R.layout.single_cofounder_list_item, ref);

        cofounderList.setAdapter(cofounderAdapter);

        cofounderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CofounderDetailActivity.class);
                String name = ((TextView) view.findViewById(R.id.single_cofounder_name)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.single_cofounder_email)).getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
