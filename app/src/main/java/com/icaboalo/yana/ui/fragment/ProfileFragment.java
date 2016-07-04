package com.icaboalo.yana.ui.fragment;

import android.content.Intent;
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
import com.icaboalo.yana.ui.activity.EditProfileActivity;
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
    @Bind(R.id.etBirthDate)
    EditText etBirthDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
    }

    @OnClick(R.id.ivFullName)
    void updateName() {
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_FULL_NAME);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getFullName());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.ivEmail)
    void updateEmail(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_EMAIL);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getEmail());
        edit.putExtras(bundle);
        startActivity(edit);
    }


    @OnClick(R.id.ivBirthDate)
    void updateBirthDate(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_BIRTH_DATE);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getBirthDate());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.ivGender)
    void updateGender(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_GENDER);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getGender());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.ivLocation)
    void updateLocation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_LOCATION);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getLocation());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.ivOccupation)
    void updateOccupation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_OCCUPATION);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser().getLocation());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    void setText() {
        UserModel user = RealmUtils.getUser();
        etEmail.setText(user.getEmail());
        etFullName.setText(user.getFullName());
        etBirthDate.setText(user.getBirthDate());
    }
}
