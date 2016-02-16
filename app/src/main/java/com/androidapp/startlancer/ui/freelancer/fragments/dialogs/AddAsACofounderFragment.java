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
import com.androidapp.startlancer.models.Cofounder;
import com.androidapp.startlancer.models.CofounderDetail;
import com.androidapp.startlancer.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAsACofounderFragment extends DialogFragment {

    private EditText locationEditText, cofounderReqEditText, cofounderReasonEditText;
    private String cofounderName, cofounderEmail;

    public AddAsACofounderFragment() {
        // Required empty public constructor
    }

    public static AddAsACofounderFragment newInstance(String data1, String data2) {
        AddAsACofounderFragment cofounderFragment = new AddAsACofounderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", data1);
        bundle.putString("email", data2);
        cofounderFragment.setArguments(bundle);
        return cofounderFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_add_as_acofounder, null);
        locationEditText = (EditText) rootView.findViewById(R.id.edit_text_location);
        cofounderReqEditText = (EditText) rootView.findViewById(R.id.edit_text_cofounder_req);
        cofounderReasonEditText = (EditText) rootView.findViewById(R.id.edit_text_cofounder_reason);

        cofounderReasonEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addAsCofounder();
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
                        addAsCofounder();
                    }
                });

        return builder.create();
    }

    private void addAsCofounder() {
        String location = locationEditText.getText().toString();
        String cofounderReq = cofounderReqEditText.getText().toString();
        String cofounderReason = cofounderReasonEditText.getText().toString();

        if (!location.equals("") && !cofounderReq.equals("") && !cofounderReason.equals("")) {
            cofounderName = getArguments().getString("name");
            cofounderEmail = getArguments().getString("email");

            Firebase cofounderRef = new Firebase(Constants.FIREBASE_URL_COFOUNDERS);
            Firebase cofounderDetailRef = new Firebase(Constants.FIREBASE_URL_COFOUNDERS_DETAIL).child(cofounderEmail);

            Cofounder cofounder = new Cofounder(cofounderName, cofounderEmail, location);
            cofounderRef.push().setValue(cofounder);

            CofounderDetail cofounderDetail = new CofounderDetail(location, cofounderReq, cofounderReason);
            cofounderDetailRef.setValue(cofounderDetail);

            AddAsACofounderFragment.this.getDialog().dismiss();
        }
    }
}
