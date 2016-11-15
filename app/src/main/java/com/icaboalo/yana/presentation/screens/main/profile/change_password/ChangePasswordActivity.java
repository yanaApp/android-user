package com.icaboalo.yana.presentation.screens.main.profile.change_password;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.btSave)
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

    @OnTextChanged(value = {R.id.etNewPassword, R.id.etOldPassword}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {

        if (!etNewPassword.getText().toString().isEmpty() && !etOldPassword.getText().toString().isEmpty())
            btSave.setVisibility(View.VISIBLE);

        else
            btSave.setVisibility(View.GONE);

    }

    @OnClick({R.id.btSave, R.id.btClear})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSave:
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
