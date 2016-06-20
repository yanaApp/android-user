package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.UserModel;
import com.icaboalo.yana.util.RealmUtils;

import io.realm.Realm;

/**
 * Created by icaboalo on 17/06/16.
 */
public class ProfileFragment extends Fragment {

    EditText mFullNameInput, mUsernameInput, mEmailInput, mPhoneInput, mBirthDateInput;
    RadioButton mManButton, mWomanButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFullNameInput = (EditText) view.findViewById(R.id.full_name_input);
        mUsernameInput = (EditText) view.findViewById(R.id.username_input);
        mEmailInput = (EditText) view.findViewById(R.id.email_input);
        mPhoneInput = (EditText) view.findViewById(R.id.phone_input);
        mBirthDateInput = (EditText) view.findViewById(R.id.birth_date_input);

        mManButton = (RadioButton) view.findViewById(R.id.man_button);
        mWomanButton = (RadioButton) view.findViewById(R.id.woman_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
    }

    void setText(){
        UserModel user = RealmUtils.getUser();
        mUsernameInput.setText(user.getUserName());
        mEmailInput.setText(user.getEmail());
        mFullNameInput.setText(user.getFullName());
        mPhoneInput.setText(user.getPhoneNumber());
        mBirthDateInput.setText(user.getBirthDate());

        if (user.getGenre() != null){
            if (user.getGenre().equals("man")){
                mManButton.setChecked(true);
            } else {
                mWomanButton.setChecked(true);
            }
        }
    }
}
