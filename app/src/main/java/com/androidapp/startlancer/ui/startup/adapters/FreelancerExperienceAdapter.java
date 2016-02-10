package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Experience;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/8/2016.
 */
public class FreelancerExperienceAdapter extends FirebaseListAdapter<Experience> {
    public FreelancerExperienceAdapter(Activity activity, Class<Experience> modelClass, int modelLayout,
                                       Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, Experience list) {

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewExperience = (TextView) view.findViewById(R.id.freelancer_experience);
        TextView textViewExperienceDescription = (TextView) view.findViewById(R.id.freelancer_experience_description);
        TextView textViewPlace = (TextView) view.findViewById(R.id.freelancer_place);


        /* Set the list name and owner */
        textViewExperience.setText(list.getExperience());
        textViewExperienceDescription.setText(list.getExperienceDescription());
        textViewPlace.setText(list.getPlace());
    }
}
