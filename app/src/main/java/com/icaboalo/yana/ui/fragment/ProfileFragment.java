package com.icaboalo.yana.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.UserModel;
import com.icaboalo.yana.ui.activity.EditProfileActivity;
import com.icaboalo.yana.util.RealmUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by icaboalo on 17/06/16.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.tvFullName)
    TextView tvFullName;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.tvBirthDate)
    TextView tvBirthDate;
    @Bind(R.id.tvGender)
    TextView tvGender;
    @Bind(R.id.tvLocation)
    TextView tvLocation;
    @Bind(R.id.tvOccupation)
    TextView tvOccupation;

    Realm mRealmInstance;

    private static final String TAG = "ProfileFragment";
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
    public void onStart() {
        super.onStart();
        mRealmInstance = Realm.getDefaultInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealmInstance.close();
    }

    @OnClick(R.id.rlFullName)
    void updateName() {
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_FULL_NAME,
                RealmUtils.getUser(mRealmInstance).getFullName());
        startActivity(edit);
    }

    @OnClick(R.id.rlEmail)
    void updateEmail(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_EMAIL,
                RealmUtils.getUser(mRealmInstance).getEmail());
        startActivity(edit);
    }

    @OnClick(R.id.rlPassword)
    void changePassword(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_PASSWORD, "");
        startActivity(edit);
    }


    @OnClick(R.id.rlBirthDate)
    void updateBirthDate(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_BIRTH_DATE,
                RealmUtils.getUser(mRealmInstance).getBirthDate());
        startActivity(edit);
    }

    @OnClick(R.id.rlGender)
    void updateGender(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_GENDER,
                RealmUtils.getUser(mRealmInstance).getGender());
        startActivity(edit);
    }

    @OnClick(R.id.rlLocation)
    void updateLocation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_LOCATION,
                RealmUtils.getUser(mRealmInstance).getLocation());
        startActivity(edit);
    }

    @OnClick(R.id.rlOccupation)
    void updateOccupation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity(), EditProfileActivity.INFO_OCCUPATION,
                RealmUtils.getUser(mRealmInstance).getOccupation());
        startActivity(edit);
    }

    void setText() {
        UserModel user = RealmUtils.getUser(mRealmInstance);
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            tvFullName.setVisibility(View.GONE);
        } else tvFullName.setText(user.getFullName());

        if (user.getEmail() == null || user.getEmail().isEmpty()){
            tvEmail.setVisibility(View.GONE);
        } else tvEmail.setText(user.getEmail());

        if (user.getBirthDate() == null || user.getBirthDate().isEmpty()){
            tvBirthDate.setVisibility(View.GONE);
        } else tvBirthDate.setText(user.getBirthDate());

        if (user.getGender() == null || user.getGender().isEmpty()){
            tvGender.setVisibility(View.GONE);
        } else tvGender.setText(user.getGender());

        if (user.getLocation() == null || user.getLocation().isEmpty()){
            tvLocation.setVisibility(View.GONE);
        } else tvLocation.setText(user.getLocation());

        if (user.getOccupation() == null || user.getOccupation().isEmpty()){
            tvOccupation.setVisibility(View.GONE);
        } else tvOccupation.setText(user.getOccupation());
    }
}
