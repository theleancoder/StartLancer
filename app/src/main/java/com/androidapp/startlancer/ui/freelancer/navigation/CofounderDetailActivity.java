package com.androidapp.startlancer.ui.freelancer.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.CofounderDetail;
import com.androidapp.startlancer.utils.Constants;
import com.androidapp.startlancer.utils.MD5Util;
import com.androidapp.startlancer.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CofounderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getIntent().getStringExtra("name"));

        String email = getIntent().getStringExtra("email");

        ImageView cofounderimageView = (ImageView) findViewById(R.id.cofounder_profile_imageView);

        final String decodedEmail = Utils.decodeEmail(email);
        String hash = MD5Util.md5Hex(decodedEmail);

        String gravatarUrl = "http://www.gravatar.com/avatar/" + hash +
                "?s=204&d=404";
        Picasso.with(this).load(gravatarUrl).placeholder(R.mipmap.ic_launcher).into(cofounderimageView);

        TextView cofounderName = (TextView) findViewById(R.id.cofounder_name_textView);
        cofounderName.setText(getIntent().getStringExtra("name"));
        final TextView cofounderEmail = (TextView) findViewById(R.id.cofounder_email_textview);
        cofounderEmail.setText(getIntent().getStringExtra("email"));

        final TextView cofounderReq = (TextView) findViewById(R.id.text_view_requirements);
        final TextView cofounderReason = (TextView) findViewById(R.id.text_view_reason);

        final Firebase ref = new Firebase(Constants.FIREBASE_URL_COFOUNDERS_DETAIL).child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CofounderDetail cofounderDetail = dataSnapshot.getValue(CofounderDetail.class);
                cofounderReq.setText(cofounderDetail.getCofounderReq());
                cofounderReason.setText(cofounderDetail.getCofounderReason());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
