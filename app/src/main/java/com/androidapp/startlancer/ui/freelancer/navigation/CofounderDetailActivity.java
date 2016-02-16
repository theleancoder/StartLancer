package com.androidapp.startlancer.ui.freelancer.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.CofounderDetail;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class CofounderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cofounder_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("name"));

        String email = getIntent().getStringExtra("email");

        final TextView location = (TextView) findViewById(R.id.textView_cofounder_location);
        final TextView cofounderReq = (TextView) findViewById(R.id.textView_cofounder_req);
        final TextView cofounderReason = (TextView) findViewById(R.id.textView_cofounder_reason);

        Firebase ref = new Firebase(Constants.FIREBASE_URL_COFOUNDERS_DETAIL).child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CofounderDetail cofounderDetail = dataSnapshot.getValue(CofounderDetail.class);
                location.setText(cofounderDetail.getLocation());
                cofounderReq.setText(cofounderDetail.getCofounderReq());
                cofounderReason.setText(cofounderDetail.getCofounderReason());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void showCofounderProfile(View view) {
    }

    public void inviteToMeetup(View view) {
    }
}
