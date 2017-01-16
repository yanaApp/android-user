package com.icaboalo.yana.presentation.screens.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.activity.EvaluationActivity;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.view_model.LoginViewModel;
import com.icaboalo.yana.presentation.screens.view_model.RecoverPasswordViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author icaboalo on 09/08/16.
 */
public class LoginActivity extends BaseActivity implements LoginView<LoginViewModel>{

    @Inject
    LoginPresenter mLoginPresenter;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tl_password)
    TextInputLayout tlPassword;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.rl_forgot_password)
    RelativeLayout rlForgotPassword;
    @BindView(R.id.ll_login_form)
    LinearLayout llLoginForm;
    @BindView(R.id.bt_login)
    Button btLogin;


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
        finish();
    }

    @Override
    public void recoverPasswordSuccess(RecoverPasswordViewModel recoverPasswordViewModel) {
        if (recoverPasswordViewModel.isSuccess()) {
            rlForgotPassword.setVisibility(View.GONE);
            llLoginForm.setVisibility(View.VISIBLE);
            showDialog();
        }
        else
            showError(recoverPasswordViewModel.getError());
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
//        showError(message);
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, btLogin, message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        if (rlForgotPassword.getVisibility() == View.VISIBLE){
            rlForgotPassword.setVisibility(View.GONE);
            llLoginForm.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    @OnTextChanged(value = {R.id.et_email, R.id.et_password}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        if (isEmailValid() && isPasswordValid())
            btLogin.setEnabled(true);
        else
            btLogin.setEnabled(false);
    }

    @OnTextChanged(value = R.id.et_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPasswordTextChanged() {
//        if (etPassword.getText().length() <= 6) {
//            ivPassword.setVisibility(View.VISIBLE);
//            tlPassword.setErrorEnabled(true);
//            tlPassword.setError(getString(R.string.error_password_short));
//        }
//        else if (etPassword.getText().length() > 6) {
//            tlPassword.setError(null);
//            tlPassword.setErrorEnabled(false);
//            ivPassword.setVisibility(View.VISIBLE);
//        }
//        else {
//            tlPassword.setError(null);
//            tlPassword.setErrorEnabled(false);
//            ivPassword.setVisibility(View.GONE);
//        }
//
//        if (tlPassword.getError() != null) {
//            ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
//        } else {
//            ivPassword.setVisibility(View.GONE);
////            ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
//        }
    }

    @OnClick(R.id.bt_login)
    void login(){
        if (etEmail.getText().toString().isEmpty() && etPassword.getText().toString().isEmpty())
            showError("Debes llenar todos los campos.");
        else if (!etEmail.getText().toString().contains("@"))
            showError("Ingresa una dirección de correo valida.");
        else if (etPassword.getText().toString().length() < 6)
            showError("La contraseña debe de ser mayor a 6 caracteres.");
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

    @OnClick(R.id.bt_register)
    void goToRegister(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Bla bla bla...")
                .setMessage(R.string.cupcake_ipsum)
                .setPositiveButton("Ok", (dialog, which) -> navigator.navigateTo(getApplicationContext(),
                        new Intent(getApplicationContext(), EvaluationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
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

    private boolean isPasswordValid() {
//        return etPassword.getText().toString().length() > 6;
        return !etPassword.getText().toString().isEmpty();
    }

    private boolean isEmailValid() {
        return etEmail.getText().toString().contains("@") && (etEmail.getText().toString().contains(".com")
                || etEmail.getText().toString().contains(".net") || etEmail.getText().toString().contains(".mx")
                || etEmail.getText().toString().contains(".org"));
    }
}
