package com.icaboalo.yana.presentation.screens.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.action_plan.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.login.view_model.LoginViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 09/08/16.
 */
public class LoginActivity extends BaseActivity implements GenericPostView<LoginViewModel>{

    @Inject
    LoginPresenter mLoginPresenter;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mLoginPresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void postSuccessful(LoginViewModel item) {
        navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingContext(getApplicationContext()));
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
        if (rlRetry != null)
            rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btLogin)
    void login(){
        if (etEmail.getText().toString().isEmpty() && etPassword.getText().toString().isEmpty())
            showError("Debes llenar todos los campos");
        else {
            HashMap<String, Object> loginBundle = new HashMap<>(2);
            loginBundle.put("username", etEmail.getText().toString());
            loginBundle.put("password", etPassword.getText().toString());
            mLoginPresenter.post(loginBundle);
        }

    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
