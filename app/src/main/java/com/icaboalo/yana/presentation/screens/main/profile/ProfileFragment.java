package com.icaboalo.yana.presentation.screens.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.main.profile.change_password.ChangePasswordActivity;
import com.icaboalo.yana.presentation.screens.main.profile.update.UpdateProfileActivity;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 24/08/16.
 */
public class ProfileFragment extends BaseFragment implements GenericDetailView<UserViewModel> {

    @Inject
    ProfilePresenter mProfilePresenter;
    //    @BindView(R.id.rlProgress)
//    RelativeLayout rlProgress;
//    @BindView(R.id.rlRetry)
//    RelativeLayout rlRetry;
    @BindView(R.id.tvFullName)
    TextView tvFullName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvBirthDate)
    TextView tvBirthDate;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvOccupation)
    TextView tvOccupation;
    @BindView(R.id.tvDepressionMotive)
    TextView tvDepressionMotive;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mProfilePresenter.setView(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        mProfilePresenter.initialize(String.valueOf(PrefUtils.getUserId(getApplicationContext())));
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
        if (item != null) {
            setInfo(item.getFullName(), item.getEmail(), item.getBirthDate(), item.getGender(), item.getLocation(),
                    item.getOccupation(), item.getDepressionMotive());
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
    void updateFullName() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.FULL_NAME,
                        tvFullName.getText().toString()));
    }

    @OnClick(R.id.rlEmail)
    void updateEmail() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.EMAIL,
                        tvEmail.getText().toString()));
    }

    @OnClick(R.id.rlPassword)
    void updatePassword() {
        navigator.navigateTo(getApplicationContext(), ChangePasswordActivity.getCallingIntent(getApplicationContext()));
    }

    @OnClick(R.id.rlBirthDate)
    void updateBirthDate() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.BIRTH_DATE,
                        tvBirthDate.getText().toString()));
    }

    @OnClick(R.id.rlGender)
    void updateGender() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.GENDER,
                        tvGender.getText().toString()));
    }

    @OnClick(R.id.rlLocation)
    void updateLocation() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.LOCATION,
                        tvLocation.getText().toString()));
    }

    @OnClick(R.id.rlOccupation)
    void updateOccupation() {
        navigator.navigateTo(getApplicationContext(),
                UpdateProfileActivity.getCallingIntent(getApplicationContext(), UpdateProfileActivity.OCCUPATION,
                        tvOccupation.getText().toString()));
    }

    @OnClick(R.id.rlDepressionMotive)
    void updateMotive() {
        navigator.navigateTo(getApplicationContext(), UpdateProfileActivity.getCallingIntent(getApplicationContext(),
                UpdateProfileActivity.MOTIVE, tvDepressionMotive.getText().toString()));
    }

    private void setInfo(String fullName, String email, String birthDate, String gender, String location, String occupation,
                         String depressionMotive) {
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
            switch (gender) {
                case "1":
                    tvGender.setText(R.string.man);
                    break;

                case "2":
                    tvGender.setText(R.string.woman);
                    break;

                case "3":
                    tvGender.setText(R.string.other);
                    break;
            }
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

        if (depressionMotive == null || depressionMotive.length() <= 0)
            tvDepressionMotive.setVisibility(View.GONE);
        else {
            tvDepressionMotive.setText(depressionMotive);
            tvOccupation.setVisibility(View.VISIBLE);
        }
    }
}
