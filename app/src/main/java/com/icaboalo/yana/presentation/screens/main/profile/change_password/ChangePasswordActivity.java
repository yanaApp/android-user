package com.icaboalo.yana.presentation.screens.main.profile.change_password;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ChangePasswordActivity extends BaseActivity implements GenericPostView<UserViewModel> {

    @Inject
    ChangePasswordPresenter mChangePasswordPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlProgress)
    RelativeLayout rlProgress;
    @BindView(R.id.rlRetry)
    RelativeLayout rlRetry;
    @BindView(R.id.etOldPassword)
    EditText etOldPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.tl_new_password)
    TextInputLayout tlNewPassword;
    @BindView(R.id.iv_new_password)
    ImageView ivNewPassword;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.bt_save)
    Button btSave;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mChangePasswordPresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.change_password_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void postSuccessful(UserViewModel item) {
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
        showToastMessage(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnTextChanged(value = R.id.etOldPassword, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onOldPasswordTextChanged() {
        if (etNewPassword.getText().toString().contentEquals(etOldPassword.getText().toString())) {
            tlNewPassword.setError("Las contraseñas coinciden");
            ivNewPassword.setVisibility(View.VISIBLE);
        }
        else {
            tlNewPassword.setError(null);
        }

        if (!etNewPassword.getText().toString().isEmpty() && !etOldPassword.getText().toString().isEmpty())
            if (tlNewPassword.getError() != null) {
                btSave.setVisibility(View.GONE);
                ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
            } else {
                btSave.setVisibility(View.VISIBLE);
                ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
            }
    }

    @OnTextChanged(value = R.id.etNewPassword, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onNewPasswordTextChanged() {
        if (etNewPassword.getText().toString().length() <= 6) {
            ivNewPassword.setVisibility(View.VISIBLE);
            tlNewPassword.setError("La contraseña debe de ser mayor a 6 caracteres.");
        }
        else if (etNewPassword.getText().toString().contentEquals(etOldPassword.getText().toString())) {
            tlNewPassword.setError("Las contraseñas coinciden");
            ivNewPassword.setVisibility(View.VISIBLE);
        }
        else if (etNewPassword.getText().toString().length() > 6) {
            tlNewPassword.setError(null);
            ivNewPassword.setVisibility(View.VISIBLE);

        } else {
            tlNewPassword.setError(null);
            ivNewPassword.setVisibility(View.GONE);
        }

        if (!etNewPassword.getText().toString().isEmpty() && !etOldPassword.getText().toString().isEmpty())
            if (tlNewPassword.getError() != null) {
                btSave.setVisibility(View.GONE);
                ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.indicator_input_error));
            } else {
                btSave.setVisibility(View.VISIBLE);
                ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_valid_20dp));
            }
    }

    @OnClick({R.id.bt_save, R.id.btClear})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                if (!etNewPassword.getText().toString().isEmpty() && !etOldPassword.getText().toString().isEmpty()) {
                    HashMap<String, Object> postBundle = new HashMap<>(2);
                    postBundle.put("old_password", etOldPassword.getText().toString());
                    postBundle.put("new_password", etNewPassword.getText().toString());
                    mChangePasswordPresenter.post(postBundle);
                } else
                    showError(getString(R.string.error_incomplete_form));
                break;

            case R.id.btClear:
                etOldPassword.setText("");
                etNewPassword.setText("");
                break;
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ChangePasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
