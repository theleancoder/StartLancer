package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.SavedCandidate;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/15/2016.
 */
public class SavedCandidateListAdapter extends FirebaseListAdapter<SavedCandidate> {

    public SavedCandidateListAdapter(Activity activity, Class<SavedCandidate> modelClass, int modelLayout,
                                     Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, SavedCandidate list) {

        TextView textViewCandidateName = (TextView) view.findViewById(R.id.candidate_name);
        TextView textViewCandidateEmail = (TextView) view.findViewById(R.id.candidate_email);
        TextView textViewOpeningTitle = (TextView) view.findViewById(R.id.opening_title);

        textViewCandidateName.setText(list.getName());
        textViewCandidateEmail.setText(list.getEmail());
        textViewOpeningTitle.setText(list.getOpening());
    }
}
