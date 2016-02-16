package com.androidapp.startlancer.ui.freelancer.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Cofounder;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/16/2016.
 */
public class CofounderAdapter extends FirebaseListAdapter<Cofounder> {
    public CofounderAdapter(Activity activity, Class<Cofounder> modelClass, int modelLayout,
                            Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, Cofounder list) {

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewCofounderName = (TextView) view.findViewById(R.id.cofounder_name);
        TextView textViewCofounderEmail = (TextView) view.findViewById(R.id.cofounder_email);
        TextView textViewCofounderLocation = (TextView) view.findViewById(R.id.cofounder_location);


        /* Set the list name and owner */
        textViewCofounderName.setText(list.getName());
        textViewCofounderEmail.setText(list.getEmail());
        textViewCofounderLocation.setText(list.getLocation());
    }
}
