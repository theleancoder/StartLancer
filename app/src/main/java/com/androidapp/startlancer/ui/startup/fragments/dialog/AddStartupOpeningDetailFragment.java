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
import com.androidapp.startlancer.models.OpeningDetail;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStartupOpeningDetailFragment extends DialogFragment {

    private EditText openingTitleEditText, openingResponsibilitiesExpEditText, openingRequirementsEditText,
            openingSalaryEditText;
    private String encodedEmail;

    public AddStartupOpeningDetailFragment() {
        // Required empty public constructor
    }

    public static AddStartupOpeningDetailFragment newInstance(String data, String extraData) {
        AddStartupOpeningDetailFragment openingFragment = new AddStartupOpeningDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putString("extraData", extraData);
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
        View rootView = inflater.inflate(R.layout.fragment_add_startup_opening_detail, null);
        openingTitleEditText = (EditText) rootView.findViewById(R.id.editTextOpeningTitle);
        openingResponsibilitiesExpEditText = (EditText) rootView.findViewById(R.id.editTextOpeningResponsibilities);
        openingRequirementsEditText = (EditText) rootView.findViewById(R.id.editTextOpeningPrerequisites);
        openingSalaryEditText = (EditText) rootView.findViewById(R.id.editTextOpeningSalary);

        openingRequirementsEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addOpeningList();
                }
                return true;
            }
        });

        builder.setMessage("Opening Details");
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
        String title = openingTitleEditText.getText().toString();
        String salary = openingSalaryEditText.getText().toString();
        String responsibilities = openingResponsibilitiesExpEditText.getText().toString();
        String requirements = openingRequirementsEditText.getText().toString();

        if (!title.equals("") && !salary.equals("") && !responsibilities.equals("") && !requirements.equals("")) {
            encodedEmail = getArguments().getString("data");
            String openingTitle = getArguments().getString("extraData");
            Firebase ref = new Firebase(Constants.FIREBASE_URL_OPENINGS_DETAIL).child(encodedEmail).child(openingTitle);
            OpeningDetail openingDetail = new OpeningDetail(requirements, responsibilities, salary, title);
            ref.setValue(openingDetail);

            AddStartupOpeningDetailFragment.this.getDialog().dismiss();
        }
    }

}
