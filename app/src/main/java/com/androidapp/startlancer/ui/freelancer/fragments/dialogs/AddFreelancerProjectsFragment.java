package com.androidapp.startlancer.ui.freelancer.fragments.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class AddFreelancerProjectsFragment extends DialogFragment {

    public AddFreelancerProjectsFragment() {
        // Required empty public constructor
    }

    private EditText editTextProject, editTextProjectDescription, editTextProjectLink;
    private String encodedEmail;

    public static AddFreelancerProjectsFragment newInstance(String data) {
        AddFreelancerProjectsFragment projectsFragment = new AddFreelancerProjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        projectsFragment.setArguments(bundle);
        return projectsFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_add_freelancer_projects, null);
        editTextProject = (EditText) rootView.findViewById(R.id.edit_text_experience);
        editTextProjectDescription = (EditText) rootView.findViewById(R.id.edit_text_experience_description);
        editTextProjectLink = (EditText) rootView.findViewById(R.id.edit_text_place);

        editTextProjectLink.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addSkillList();
                }
                return true;
            }
        });

        builder.setMessage("Add Project");
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
        String project = editTextProject.getText().toString();
        String projectDescription = editTextProjectDescription.getText().toString();
        String projectLink = editTextProjectLink.getText().toString();

        if (!project.equals("") && !projectDescription.equals("") && !projectLink.equals("")) {
            encodedEmail = getArguments().getString("data");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("projects");
            Map<String, String> projects = new HashMap<>();
            projects.put("project", project);
            projects.put("projectDescription", projectDescription);
            projects.put("projectLink", projectLink);
            ref.push().setValue(projects);

            AddFreelancerProjectsFragment.this.getDialog().dismiss();
        }
    }


}
