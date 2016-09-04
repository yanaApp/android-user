package com.icaboalo.yana.presentation.screens.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.activity.EvaluationActivity;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.login.view_model.LoginViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 09/08/16.
 */
public class LoginActivity extends BaseActivity implements LoginView<LoginViewModel>{

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
    @Bind(R.id.rlForgotPassword)
    RelativeLayout rlForgotPassword;
    @Bind(R.id.llLoginForm)
    LinearLayout llLoginForm;


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
        navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
    }

    @Override
    public void recoverPasswordSuccess(boolean success) {
        rlForgotPassword.setVisibility(View.GONE);
        llLoginForm.setVisibility(View.VISIBLE);
        showDialog();
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

    @Override
    public void onBackPressed() {
        if (rlForgotPassword.getVisibility() == View.VISIBLE){
            rlForgotPassword.setVisibility(View.GONE);
            llLoginForm.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    @OnClick(R.id.btLogin)
    void login(){
        if (etEmail.getText().toString().isEmpty() && etPassword.getText().toString().isEmpty())
            showError("Debes llenar todos los campos.");
        else {
            HashMap<String, Object> loginBundle = new HashMap<>(2);
            loginBundle.put("email", etEmail.getText().toString());
            loginBundle.put("password", etPassword.getText().toString());
            mLoginPresenter.post(loginBundle);
        }
    }

    @OnClick(R.id.forgot_password)
    void showForgotPassword(){
        llLoginForm.setVisibility(View.GONE);
        rlForgotPassword.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btRecoverPassword)
    void recoverPassword(){
        if (etEmail.getText().toString().isEmpty())
            showError("Debes llenar todos los campos.");
        else {
            mLoginPresenter.attemptRecoverPassword(etEmail.getText().toString());
        }
    }

    @OnClick(R.id.btRegister)
    void goToRegister(){
        navigator.navigateTo(getApplicationContext(), new Intent(getApplicationContext(), EvaluationActivity.class));
    }

    void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Recuperación");
        alertDialog.setMessage("Por favor revisa tu correo electronico para restablecer tu contraseña.");
        alertDialog.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
