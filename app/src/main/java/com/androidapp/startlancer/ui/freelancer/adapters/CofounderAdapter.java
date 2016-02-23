package com.androidapp.startlancer.ui.freelancer.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.Cofounder;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

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
        TextView textViewCofounderName = (TextView) view.findViewById(R.id.single_cofounder_name);
        TextView textViewCofounderEmail = (TextView) view.findViewById(R.id.single_cofounder_email);
        TextView textViewCofounderLocation = (TextView) view.findViewById(R.id.single_cofounder_location);
        ImageView imageViewCofounderImage = (ImageView) view.findViewById(R.id.single_cofounder_image);

        String decodedEmail = Utils.decodeEmail(list.getEmail());
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(view.getContext()).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(imageViewCofounderImage);


        /* Set the list name and owner */
        textViewCofounderName.setText(list.getName());
        textViewCofounderEmail.setText(list.getEmail());
        textViewCofounderLocation.setText(list.getLocation());
    }
}
