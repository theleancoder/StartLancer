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
public class AddFreelancerAboutFragment extends DialogFragment {

    private EditText editTextAbout;
    private String encodedEmail;

    public AddFreelancerAboutFragment() {
        // Required empty public constructor
    }

    public static AddFreelancerAboutFragment newInstance(String data) {
        AddFreelancerAboutFragment aboutFragment = new AddFreelancerAboutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        aboutFragment.setArguments(bundle);
        return aboutFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_add_freelancer_about, null);
        editTextAbout = (EditText) rootView.findViewById(R.id.edit_text_about);


        editTextAbout.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addSkillList();
                }
                return true;
            }
        });

        builder.setMessage("About Me");
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
        String aboutMe = editTextAbout.getText().toString();

        if (!aboutMe.equals("")) {
            encodedEmail = getArguments().getString("data");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_FREELANCER_PROFILE).child(encodedEmail).child("about");
            Map<String, String> about = new HashMap<>();
            about.put("experience", aboutMe);
            ref.push().setValue(about);

            AddFreelancerAboutFragment.this.getDialog().dismiss();
        }
    }

}
