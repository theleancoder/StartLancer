package com.androidapp.startlancer.ui.startup.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.SavedCandidate;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

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
        ImageView imageViewSavedCandidate = (ImageView) view.findViewById(R.id.candidate_image);

        String decodedEmail = Utils.decodeEmail(list.getEmail());
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(view.getContext()).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(imageViewSavedCandidate);

        textViewCandidateName.setText(list.getName());
        textViewCandidateEmail.setText(list.getEmail());
        textViewOpeningTitle.setText(list.getOpening());
    }
}
