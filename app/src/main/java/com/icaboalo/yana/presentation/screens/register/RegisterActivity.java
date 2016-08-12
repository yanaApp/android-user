package com.icaboalo.yana.presentation.screens.register;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterActivity extends BaseActivity implements GenericPostView<RegisterViewModel> {

    @Inject
    RegisterPresenter mRegisterPresenter;

    @Override
    public void initialize() {

    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void postSuccessful(RegisterViewModel item) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
