package com.icaboalo.yana.presentation.screens.action_plan.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.profile.update.UpdateProfileActivity;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 24/08/16.
 */
public class ProfileFragment extends BaseFragment implements GenericDetailView<UserViewModel> {

    @Inject
    ProfilePresenter mProfilePresenter;
//    @Bind(R.id.rlProgress)
//    RelativeLayout rlProgress;
//    @Bind(R.id.rlRetry)
//    RelativeLayout rlRetry;
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

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mProfilePresenter.setView(this);
        mProfilePresenter.initialize("user@test.com");
    }

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
    public void renderItem(UserViewModel item) {
        if (item != null){
            tvFullName.setText(item.getFullName());
            tvEmail.setText(item.getEmail());
            tvBirthDate.setText(item.getBirthDate());
            tvGender.setText(item.getGender());
            tvLocation.setText(item.getLocation());
            tvOccupation.setText(item.getOccupation());
            setInfo(item.getFullName(), item.getEmail(), item.getBirthDate(), item.getGender(), item.getLocation(),
                    item.getOccupation());
        }
    }

    @Override
    public void showLoading() {
//        if (rlProgress != null)
//            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
//        if (rlProgress != null)
//            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
//        if (rlRetry != null)
//            rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
//        if (rlRetry != null)
//            rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    @OnClick(R.id.rlFullName)
    void updateFullName(){
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.FULL_NAME,
                        tvFullName.getText().toString()));
    }

    @OnClick(R.id.rlEmail)
    void updateEmail(){
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.EMAIL,
                        tvEmail.getText().toString()));
    }

    @OnClick(R.id.rlPassword)
    void updatePassword(){
//        navigator.navigateTo(getApplicationContext(),
//                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.PASSWORD));
    }

    @OnClick(R.id.rlBirthDate)
    void updateBirthDate(){
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.BIRTH_DATE,
                        tvBirthDate.getText().toString()));
    }

    @OnClick(R.id.rlGender)
    void updateGender(){
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.GENDER,
                        tvGender.getText().toString()));
    }

    @OnClick(R.id.rlLocation)
    void updateLocation(){
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.LOCATION,
                        tvLocation.getText().toString()));
    }

    @OnClick(R.id.rlOccupation)
    void updateOccupation(){
        showError("BLAAA");
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.OCCUPATION,
                        tvOccupation.getText().toString()));
    }

    private void setInfo(String fullName, String email, String birthDate, String gender, String location, String occupation){
        if (fullName == null || fullName.length() <= 0)
            tvFullName.setVisibility(View.GONE);
        else {
            tvFullName.setText(fullName);
            tvFullName.setVisibility(View.VISIBLE);
        }

        if (email == null || email.length() <= 0)
            tvEmail.setVisibility(View.GONE);
        else {
            tvEmail.setText(email);
            tvEmail.setVisibility(View.VISIBLE);
        }

        if (birthDate == null || birthDate.length() <= 0)
            tvBirthDate.setVisibility(View.GONE);
        else {
            tvBirthDate.setText(birthDate);
            tvBirthDate.setVisibility(View.VISIBLE);
        }

        if (gender == null || gender.length() <= 0)
            tvGender.setVisibility(View.GONE);
        else {
            tvGender.setText(gender);
            tvGender.setVisibility(View.VISIBLE);
        }

        if (location == null || location.length() <= 0)
            tvLocation.setVisibility(View.GONE);
        else {
            tvLocation.setText(location);
            tvLocation.setVisibility(View.VISIBLE);
        }

        if (occupation == null || occupation.length() <= 0)
            tvOccupation.setVisibility(View.GONE);
        else {
            tvOccupation.setText(occupation);
            tvOccupation.setVisibility(View.VISIBLE);
        }
    }
}
