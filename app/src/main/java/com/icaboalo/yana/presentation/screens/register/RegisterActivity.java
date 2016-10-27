package com.icaboalo.yana.presentation.screens.register;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;
import com.icaboalo.yana.presentation.screens.schedule.ScheduleActivity;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterActivity extends BaseActivity implements GenericPostView<RegisterViewModel> {

    @Inject
    RegisterPresenter mRegisterPresenter;
    @BindView(R.id.etFullName)
    EditText etFullName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.rlProgress)
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
        navigator.navigateTo(getApplicationContext(), ScheduleActivity.getCallingIntent(getApplicationContext()));
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
    void register() {
        if (etEmail.getText().toString().isEmpty() || etFullName.getText().toString().isEmpty()
                || etPassword.getText().toString().isEmpty())
            showError("Debes llenar todos los campos.");
        else if (!etEmail.getText().toString().contains("@"))
            showError("Ingresa una dirección de correo valida.");
        else if (etPassword.getText().toString().length() < 6)
            showError("La contraseña debe de ser mayor a 6 caracteres.");
        else {
            HashMap<String, Object> registerBundle = new HashMap<>(4);
            registerBundle.put("email", etEmail.getText().toString());
            registerBundle.put("full_name", etFullName.getText().toString());
            registerBundle.put("password", etPassword.getText().toString());
            mRegisterPresenter.post(registerBundle);
        }
    }

    @OnClick(R.id.btLogin)
    void login() {
        navigator.navigateTo(getApplicationContext(), LoginActivity.getCallingIntent(getApplicationContext()));
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
