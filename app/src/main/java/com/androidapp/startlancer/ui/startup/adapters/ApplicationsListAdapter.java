package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Application;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

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

        TextView textViewCandidateName = (TextView) view.findViewById(R.id.text_view_candidate_name);
        TextView textViewCandidateEmail = (TextView) view.findViewById(R.id.text_view_candidate_email);
        ImageView imageViewCandidate = (ImageView) view.findViewById(R.id.image_view_candidate);

        String decodedEmail = Utils.decodeEmail(list.getEmail());
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(view.getContext()).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(imageViewCandidate);


        textViewCandidateName.setText(list.getName());
        textViewCandidateEmail.setText(list.getEmail());
    }
}
