package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Skill;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/8/2016.
 */
public class FreelancerSkillAdapter extends FirebaseListAdapter<Skill> {

    public FreelancerSkillAdapter(Activity activity, Class<Skill> modelClass, int modelLayout,
                                  Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, Skill list) {

        TextView textViewSkill = (TextView) view.findViewById(R.id.text_view_freelancer_skill);
        TextView textViewYearOfExperience = (TextView) view.findViewById(R.id.text_View_freelancer_year_of_experience);

        textViewSkill.setText(list.getSkillType());
        textViewYearOfExperience.setText(list.getYearsOfExperience());
    }
}
