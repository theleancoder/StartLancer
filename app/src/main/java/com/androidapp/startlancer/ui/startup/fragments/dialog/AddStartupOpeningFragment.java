package com.androidapp.startlancer.ui.startup.fragments.dialog;


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
public class AddStartupOpeningFragment extends DialogFragment {


    private EditText openingTitleEditText, openingSalaryEditText;
    private String encodedEmail;

    public AddStartupOpeningFragment() {
        // Required empty public constructor
    }

    public static AddStartupOpeningFragment newInstance(String data) {
        AddStartupOpeningFragment openingFragment = new AddStartupOpeningFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        openingFragment.setArguments(bundle);
        return openingFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_add_startup_opening, null);
        openingTitleEditText = (EditText) rootView.findViewById(R.id.editTextOpeningTitle);
        openingSalaryEditText = (EditText) rootView.findViewById(R.id.editTextOpeningSalary);

        openingSalaryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addSkillList();
                }
                return true;
            }
        });

        builder.setMessage("Add Opening");
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
        String title = openingTitleEditText.getText().toString();
        String salary = openingSalaryEditText.getText().toString();

        if (!title.equals("") && !salary.equals("")) {
            encodedEmail = getArguments().getString("data");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
            Map<String, String> opening = new HashMap<>();
            opening.put("title", title);
            opening.put("salary", salary);
            ref.push().setValue(opening);

            AddStartupOpeningFragment.this.getDialog().dismiss();
        }
    }

}
