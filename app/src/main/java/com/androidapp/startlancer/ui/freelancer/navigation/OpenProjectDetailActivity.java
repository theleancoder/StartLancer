package com.androidapp.startlancer.ui.freelancer.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androidapp.startlancer.R;
import com.androidapp.startlancer.models.OpenProject;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class OpenProjectDetailActivity extends AppCompatActivity {

    private OpenProject openProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_project_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textViewTitle = (TextView) findViewById(R.id.text_view_open_project_title);
        final TextView textViewDescription = (TextView) findViewById(R.id.text_view_open_project_description);
        final TextView textViewAuthor = (TextView) findViewById(R.id.text_view_open_project_author);
        final TextView textViewLinks = (TextView) findViewById(R.id.text_view_open_project_links);
        final TextView textViewTechnologies = (TextView) findViewById(R.id.text_view_open_project_technologies);
        ImageView imageViewProject = (ImageView) findViewById(R.id.single_open_project_image);

        String project = getIntent().getStringExtra("title");

        char letter = project.charAt(0);

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(project);

        TextDrawable drawable = TextDrawable.builder().buildRect(String.valueOf(letter), color);

        imageViewProject.setImageDrawable(drawable);


        String email = getIntent().getStringExtra("email");

        Firebase ref = new Firebase(Constants.FIREBASE_URL_OPEN_PROJECTS).child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                openProject = dataSnapshot.getValue(OpenProject.class);
                textViewTitle.setText(openProject.getTitle());
                textViewDescription.setText(openProject.getDescription());
                textViewAuthor.setText(openProject.getAuthor());
                textViewLinks.setText(openProject.getLinks());
                textViewTechnologies.setText(openProject.getTechnologies());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
