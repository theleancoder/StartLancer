package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/11/2016.
 */
public class ApplicationsListAdapter extends FirebaseListAdapter<Application> {

    public ApplicationsListAdapter(Activity activity, Class<Application> modelClass, int modelLayout,
                                   Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, Application list) {

        TextView textViewCandidateName = (TextView) view.findViewById(R.id.textview_candidate_name);
        TextView textViewCandidateEmail = (TextView) view.findViewById(R.id.textview_candidate_email);

        textViewCandidateName.setText(list.getName());
        textViewCandidateEmail.setText(list.getEmail());
    }
}
