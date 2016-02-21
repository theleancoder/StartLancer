package com.androidapp.startlancer.ui.freelancer.fragments.dialogs;


import android.app.Dialog;
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
import com.androidapp.startlancer.models.OpenProject;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAnOpenProjectFragment extends android.app.DialogFragment {
    private EditText editTextProjectTitle, editTextProjectDescription, editTextProjectLinks, editTextProjectTechnologies;
    private String authorName, authorEmail;


    public AddAnOpenProjectFragment() {
        // Required empty public constructor
    }

    public static AddAnOpenProjectFragment newInstance(String data1, String data2) {
        AddAnOpenProjectFragment openProjectFragment = new AddAnOpenProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", data1);
        bundle.putString("email", data2);
        openProjectFragment.setArguments(bundle);
        return openProjectFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_add_an_open_project, null);
        editTextProjectTitle = (EditText) rootView.findViewById(R.id.edit_text_project_title);
        editTextProjectDescription = (EditText) rootView.findViewById(R.id.edit_text_project_description);
        editTextProjectLinks = (EditText) rootView.findViewById(R.id.edit_text_project_link);
        editTextProjectTechnologies = (EditText) rootView.findViewById(R.id.edit_text_project_technologies);

        editTextProjectTechnologies.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addAsOpenProject();
                }
                return true;
            }
        });

        builder.setMessage("Add Me As Cofounder");
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addAsOpenProject();
                    }
                });

        return builder.create();
    }

    private void addAsOpenProject() {
        String title = editTextProjectTitle.getText().toString();
        String description = editTextProjectDescription.getText().toString();
        String links = editTextProjectLinks.getText().toString();
        String technologies = editTextProjectTechnologies.getText().toString();

        if (!title.equals("") && !description.equals("") && !links.equals("") && !technologies.equals("")) {
            authorName = getArguments().getString("name");
            authorEmail = getArguments().getString("email");

            Firebase ref = new Firebase(Constants.FIREBASE_URL_OPEN_PROJECTS).child(authorName);

            OpenProject openProject = new OpenProject(title, description, links, technologies, authorName, authorEmail);
            ref.setValue(openProject);

            AddAnOpenProjectFragment.this.getDialog().dismiss();
        }
    }

}
