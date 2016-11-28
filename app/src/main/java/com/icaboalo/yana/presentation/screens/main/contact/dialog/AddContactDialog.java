package com.icaboalo.yana.presentation.screens.main.contact.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.icaboalo.yana.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * @author icaboalo on 03/09/16.
 */
public class AddContactDialog extends DialogFragment {

    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.swLiveTogether)
    SwitchCompat swLiveTogether;
    @BindView(R.id.spPhoneNumber)
    Spinner spPhoneNumber;
    @BindView(R.id.spRelation)
    Spinner spRelation;
    OnDialogClickListener mDialogClickListener;

    public interface OnDialogClickListener{
        void onPositiveClick(DialogInterface dialogInterface, String contactName, String phoneNumber, String relation,
                             boolean liveTogether);
        void onNegativeClick(DialogInterface dialogInterface);
    }

    public static final String CONTACT_NAME = "Contact name", PHONE_NUMBERS = "Phone numbers";

    public static AddContactDialog newInstance(String contactName, ArrayList<String> phoneNumber){
        AddContactDialog fragment = new AddContactDialog();
        Bundle args = new Bundle();
        args.putString(CONTACT_NAME, contactName);
        args.putStringArrayList(PHONE_NUMBERS, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_contact, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        setTexts(getArguments().getString(CONTACT_NAME), getArguments().getStringArrayList(PHONE_NUMBERS));

        alertDialog.setTitle(R.string.contact_dialog_title);
        alertDialog.setPositiveButton("Add", (dialog, which) -> {
            if (etFirstName.getText().toString().isEmpty() /* || spPhoneNumber.getSelectedItemPosition() == 0 */ ||
                    spRelation.getSelectedItemPosition() == 0)
                Toast.makeText(getActivity(), R.string.error_incomplete_form, Toast.LENGTH_SHORT).show();
            else
                mDialogClickListener.onPositiveClick(dialog, etFirstName.getText().toString(),
                        (String)spPhoneNumber.getSelectedItem(), (String)spRelation.getSelectedItem(), swLiveTogether.isChecked());
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> mDialogClickListener.onNegativeClick(dialog));
        return alertDialog.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
    }

    public void setDialogClickListener(OnDialogClickListener onDialogClickListener){
        this.mDialogClickListener = onDialogClickListener;
    }

    @OnItemSelected({R.id.spPhoneNumber, R.id.spRelation})
    void onItemSelected() {
        if (!etFirstName.toString().isEmpty() && spinnerSelectionValid(spPhoneNumber) && spinnerSelectionValid(spRelation))
            ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
        else
            ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
    }

    private void setTexts(String contactName, ArrayList<String> phoneNumber){
        etFirstName.setText(contactName);
        if (phoneNumber.size() > 1){
            phoneNumber.add(0, "Select a phone number");
        }
        ArrayAdapter<String> nArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                phoneNumber);
        spPhoneNumber.setAdapter(nArrayAdapter);
    }

    private boolean spinnerSelectionValid(Spinner spinner){
        if (spinner.getCount() == 1)
            return true;
        else if (spinner.getSelectedItemPosition() > 0)
            return true;
        return false;
    }
}
