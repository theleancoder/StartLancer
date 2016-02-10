package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpeningDetail;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by ankit on 2/2/2016.
 */
public class OpeningListAdapter extends FirebaseListAdapter<OpeningDetail> {
    public OpeningListAdapter(Activity activity, Class<OpeningDetail> modelClass, int modelLayout,
                              Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, OpeningDetail list) {

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewOpeningTitle = (TextView) view.findViewById(R.id.textview_opening_title);
        TextView textViewSalary = (TextView) view.findViewById(R.id.textview_salary);


        /* Set the list name and owner */
        textViewOpeningTitle.setText(list.getTitle());
        textViewSalary.setText(list.getSalary());
    }
}
