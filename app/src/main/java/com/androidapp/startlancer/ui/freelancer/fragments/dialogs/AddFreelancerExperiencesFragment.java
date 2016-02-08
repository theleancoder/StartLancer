package com.androidapp.startlancer.ui.freelancer.fragments.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFreelancerExperiencesFragment extends DialogFragment {

    private EditText editTextExperience, editTextExperienceDescription, editTextPlace;
    private String encodedEmail;

    public AddFreelancerExperiencesFragment() {
        // Required empty public constructor
    }

    public static AddFreelancerExperiencesFragment newInstance(String data) {
        AddFreelancerExperiencesFragment experiencesFragment = new AddFreelancerExperiencesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        experiencesFragment.setArguments(bundle);
        return experiencesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_add_freelancer_experiences, null);
        editTextExperience = (EditText) rootView.findViewById(R.id.edit_text_experience);
        editTextExperienceDescription = (EditText) rootView.findViewById(R.id.edit_text_experience_description);
        editTextPlace = (EditText) rootView.findViewById(R.id.edit_text_place);

        editTextExperienceDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addSkillList();
                }
                return true;
            }
        });

        builder.setMessage("Add Experience");
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addSkillList();
                    }
                });

        return builder.create();
    }

    public void addSkillList() {
        String experience = editTextExperience.getText().toString();
        String experienceDescription = editTextExperienceDescription.getText().toString();
        String place = editTextPlace.getText().toString();

        if (!experience.equals("") && !experienceDescription.equals("") && !place.equals("")) {
            encodedEmail = getArguments().getString("data");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("experiences");
            Map<String, String> experiences = new HashMap<>();
            experiences.put("experience", experience);
            experiences.put("experienceDescription", experienceDescription);
            experiences.put("place", place);
            ref.push().setValue(experiences);

            AddFreelancerExperiencesFragment.this.getDialog().dismiss();
        }
    }
}
