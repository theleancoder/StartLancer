package com.androidapp.startlancer.ui.freelancer.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpenProject;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/22/2016.
 */
public class OpenProjectAdapter extends FirebaseListAdapter<OpenProject> {

    public OpenProjectAdapter(Activity activity, Class<OpenProject> modelClass, int modelLayout,
                              Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, OpenProject list) {

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewProjectTitle = (TextView) view.findViewById(R.id.textview_project_title);
        TextView textViewAuthorName = (TextView) view.findViewById(R.id.textview_project_author);
        TextView textViewAuthorEmail = (TextView) view.findViewById(R.id.textview_project_email);


        /* Set the list name and owner */
        textViewProjectTitle.setText(list.getTitle());
        textViewAuthorName.setText(list.getAuthor());
        textViewAuthorEmail.setText(list.getAuthorEmail());
    }
}
