package com.androidapp.startlancer.ui.startup.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
public class AddOpeningFragment extends DialogFragment {
    private EditText editTextSalary;
    private EditText editTextOpeningTitle;
    private String encodedEmail;
    private ProgressDialog progressDialog;

    public AddOpeningFragment() {
    }

    public static AddOpeningFragment newInstance(String data) {
        AddOpeningFragment addOpeningFragment = new AddOpeningFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        addOpeningFragment.setArguments(bundle);
        return addOpeningFragment;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_add_opening, null);
        editTextOpeningTitle = (EditText) rootView.findViewById(R.id.edit_text_opening);
        editTextSalary = (EditText) rootView.findViewById(R.id.edit_text_salary);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getResources().getString(R.string.login_dialog_loading));
        progressDialog.setMessage(getResources().getString(R.string.login_dialog_message));
        progressDialog.setCancelable(false);

        /**
         * Call addShoppingList() when user taps "Done" keyboard action
         */
        editTextSalary.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addOpeningList();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setMessage("Add Opening");
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addOpeningList();
                    }
                });

        return builder.create();
    }

    public void addOpeningList() {
        String openingName = editTextOpeningTitle.getText().toString();
        String salary = editTextSalary.getText().toString();

        if (!openingName.equals("") && !salary.equals("")) {
            encodedEmail = getArguments().getString("data");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS).child(encodedEmail);
            Map<String, String> opening = new HashMap<>();
            opening.put("title", openingName);
            opening.put("salary", salary);
            ref.push().setValue(opening);

            AddOpeningFragment.this.getDialog().dismiss();
        }
    }
}
