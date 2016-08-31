package com.icaboalo.yana.presentation.screens.register;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterActivity extends BaseActivity implements GenericPostView<RegisterViewModel> {

    @Inject
    RegisterPresenter mRegisterPresenter;
    @Bind(R.id.etFullName)
    EditText etFullName;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
//    @Bind(R.id.rlRetry)
//    RelativeLayout rlRetry;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mRegisterPresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void postSuccessful(RegisterViewModel item) {
        navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
        finish();
    }

    @Override
    public void showLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.GONE);
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

    @OnClick(R.id.btRegister)
    void register(){
        if (etEmail.getText().toString().isEmpty() || etFullName.getText().toString().isEmpty()
                || etPassword.getText().toString().isEmpty()){
            showError("Debes llenar todos los campos.");
        } else {
            HashMap<String, Object> registerBundle = new HashMap<>(4);
            registerBundle.put("email", etEmail.getText().toString());
            registerBundle.put("full_name", etFullName.getText().toString());
            registerBundle.put("password", etPassword.getText().toString());
            mRegisterPresenter.post(registerBundle);
        }
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
