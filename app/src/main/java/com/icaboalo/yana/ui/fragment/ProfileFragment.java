package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.UserModel;
import com.icaboalo.yana.util.RealmUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by icaboalo on 17/06/16.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.etFullName)
    EditText etFullName;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.etBirthDate)
    EditText etBirthDate;
    @Bind(R.id.rbMan)
    RadioButton rbMan;
    @Bind(R.id.rbWoman)
    RadioButton rbWoman;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        etFullName = (EditText) view.findViewById(R.id.etFullName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        etBirthDate = (EditText) view.findViewById(R.id.etBirthDate);

        rbMan = (RadioButton) view.findViewById(R.id.rbMan);
        rbWoman = (RadioButton) view.findViewById(R.id.rbWoman);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
    }

    @OnClick(R.id.rlFullName)
    void updateName() {

    }

    @OnClick(R.id.rlEmail)
    void updateEmail(){

    }
    @OnClick(R.id.rlPhoneNumber)
    void updatePhoneNumber(){

    }
    @OnClick(R.id.rlBirthDate)
    void updateBirthDate(){

    }
    @OnClick(R.id.rlGender)
    void updateGender(){

    }
    @OnClick(R.id.rlLocation)
    void updateLocation(){

    }

    void setText() {
        UserModel user = RealmUtils.getUser();
        etEmail.setText(user.getEmail());
        etFullName.setText(user.getFullName());
        etPhoneNumber.setText(user.getPhoneNumber());
        etBirthDate.setText(user.getBirthDate());

        if (user.getGenre() != null) {
            if (user.getGenre().equals("man")) {
                rbMan.setChecked(true);
            } else {
                rbWoman.setChecked(true);
            }
        }
    }
}
