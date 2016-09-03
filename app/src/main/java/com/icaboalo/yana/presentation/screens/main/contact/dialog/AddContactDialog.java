package com.icaboalo.yana.presentation.screens.main.contact.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.icaboalo.yana.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 03/09/16.
 */
public class AddContactDialog extends DialogFragment {

    @Bind(R.id.etFirstName)
    EditText etFirstName;
    @Bind(R.id.cbLiveTogether)
    AppCompatCheckBox cbLiveTogether;
    @Bind(R.id.spPhoneNumber)
    Spinner spPhoneNumber;
    @Bind(R.id.spRelation)
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

        alertDialog.setTitle("Add a new support contact");
        alertDialog.setPositiveButton("Add", (dialog, which) -> {
            if (etFirstName.getText().toString().isEmpty() /* || spPhoneNumber.getSelectedItemPosition() == 0 */ ||
                    spRelation.getSelectedItemPosition() == 0)
                Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            else
                mDialogClickListener.onPositiveClick(dialog, etFirstName.getText().toString(),
                        (String)spPhoneNumber.getSelectedItem(), (String)spRelation.getSelectedItem(), cbLiveTogether.isChecked());
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
            mDialogClickListener.onNegativeClick(dialog);
        });
        return alertDialog.create();
    }

    public void setDialogClickListener(OnDialogClickListener onDialogClickListener){
        this.mDialogClickListener = onDialogClickListener;
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
}
