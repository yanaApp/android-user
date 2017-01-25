package com.icaboalo.yana.presentation.screens.register;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;
import com.icaboalo.yana.presentation.screens.schedule.ScheduleActivity;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterActivity extends BaseActivity implements GenericPostView<RegisterViewModel> {

    @Inject
    RegisterPresenter mRegisterPresenter;
    @BindView(R.id.etFullName)
    EditText etFullName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tl_email)
    TextInputLayout tlEmail;
    @BindView(R.id.iv_email)
    ImageView ivEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tl_password)
    TextInputLayout tlPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.tl_confirm_password)
    TextInputLayout tlConfirmPassword;
    @BindView(R.id.iv_confirm_password)
    ImageView ivConfirmPassword;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.bt_register)
    Button btRegister;
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
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, btRegister, message, Snackbar.LENGTH_SHORT);
    }

    @OnTextChanged(value = R.id.et_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPasswordTextChanged() {
        if (etPassword.getText().length() <= 6) {
            tlPassword.setErrorEnabled(true);
            ivPassword.setVisibility(View.VISIBLE);
            tlPassword.setError(getString(R.string.error_password_short));
        } else if (etPassword.getText().length() > 6) {
            tlPassword.setError(null);
            tlPassword.setErrorEnabled(false);
            ivPassword.setVisibility(View.VISIBLE);
        } else {
            tlPassword.setError(null);
            tlPassword.setErrorEnabled(false);
            ivPassword.setVisibility(View.GONE);
        }

        if (tlPassword.getError() != null) {
            ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
        } else {
            ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
        }
    }

    @OnTextChanged(value = R.id.et_confirm_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onConfirmPasswordTextChanged() {
        ivConfirmPassword.setVisibility(View.VISIBLE);

        if (etConfirmPassword.getText().toString().contentEquals(etPassword.getText().toString())) {
            tlConfirmPassword.setError(null);
            tlConfirmPassword.setErrorEnabled(false);
        }
        else {
            tlConfirmPassword.setErrorEnabled(true);
            tlConfirmPassword.setError(getString(R.string.error_password_not_match));
        }

        if (tlConfirmPassword.getError() != null)
            ivConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
        else
            ivConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
    }

    @OnTextChanged(value = R.id.et_email, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onEmailPasswordTextChanged() {
        if (!isEmailValid()) {
            tlEmail.setErrorEnabled(true);
            ivEmail.setVisibility(View.VISIBLE);
            tlEmail.setError(getString(R.string.error_invalid_email));
        } else if (isEmailValid()) {
            tlEmail.setError(null);
            tlEmail.setErrorEnabled(false);
            ivEmail.setVisibility(View.VISIBLE);
        } else {
            tlEmail.setError(null);
            tlEmail.setErrorEnabled(false);
            ivEmail.setVisibility(View.GONE);
        }

        if (tlEmail.getError() != null)
            ivEmail.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
        else
            ivEmail.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
    }

    @OnTextChanged(value = {R.id.et_password, R.id.et_confirm_password, R.id.et_email}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        if (isEmailValid() && isPasswordValid())
            btRegister.setEnabled(true);
        else
            btRegister.setEnabled(false);
    }

    @OnClick(R.id.bt_register)
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

    @OnClick(R.id.bt_login)
    void login() {
        navigator.navigateTo(getApplicationContext(), LoginActivity.getCallingIntent(getApplicationContext()));
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private boolean isPasswordValid() {
        return etPassword.getText().toString().length() > 6 &&
                etPassword.getText().toString().contentEquals(etConfirmPassword.getText().toString());
    }

    private boolean isEmailValid() {
        return etEmail.getText().toString().contains("@") && (etEmail.getText().toString().contains(".com")
                || etEmail.getText().toString().contains(".net") || etEmail.getText().toString().contains(".mx")
                || etEmail.getText().toString().contains(".org"));
    }
}
