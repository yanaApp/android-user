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
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_FULL_NAME);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getFullName());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.rlEmail)
    void updateEmail(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_EMAIL);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getEmail());
        edit.putExtras(bundle);
        startActivity(edit);
    }


    @OnClick(R.id.rlBirthDate)
    void updateBirthDate(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_BIRTH_DATE);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getBirthDate());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.rlGender)
    void updateGender(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_GENDER);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getGender());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.rlLocation)
    void updateLocation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_LOCATION);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getLocation());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    @OnClick(R.id.rlOccupation)
    void updateOccupation(){
        Intent edit = EditProfileActivity.getCallingIntent(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(EditProfileActivity.INFO_TYPE, EditProfileActivity.INFO_OCCUPATION);
        bundle.putString(EditProfileActivity.CONTENT, RealmUtils.getUser(mRealmInstance).getLocation());
        edit.putExtras(bundle);
        startActivity(edit);
    }

    void setText() {
        UserModel user = RealmUtils.getUser(mRealmInstance);
        if (user.getFullName() == null) {
            tvFullName.setVisibility(View.GONE);
        } else tvFullName.setText(user.getFullName());

        if (user.getEmail() == null){
            tvEmail.setVisibility(View.GONE);
        } else tvEmail.setText(user.getEmail());

        if (user.getBirthDate() == null){
            tvBirthDate.setVisibility(View.GONE);
        } else tvBirthDate.setText(user.getBirthDate());

        if (user.getGender() == null){
            tvGender.setVisibility(View.GONE);
        } else tvGender.setText(user.getGender());

        if (user.getLocation() == null){
            tvLocation.setVisibility(View.GONE);
        } else tvLocation.setText(user.getLocation());

        if (user.getOccupation() == null){
            tvOccupation.setVisibility(View.GONE);
        } else tvOccupation.setText(user.getOccupation());
    }
}
