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

        TextView textViewExperience = (TextView) view.findViewById(R.id.text_View_freelancer_experience);
        TextView textViewExperienceDuration = (TextView) view.findViewById(R.id.text_view_experience_duration);
        TextView textViewLocation = (TextView) view.findViewById(R.id.text_View_freelancer_location);

        textViewExperience.setText(list.getExp());
        textViewExperienceDuration.setText(list.getExperienceDescription());
        textViewLocation.setText(list.getExperienceLocation());
    }
}
