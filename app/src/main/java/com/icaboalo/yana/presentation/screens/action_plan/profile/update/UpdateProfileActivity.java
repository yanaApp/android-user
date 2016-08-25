package com.icaboalo.yana.presentation.screens.action_plan.profile.update;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

import java.security.Key;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 25/08/16.
 */
public class UpdateProfileActivity extends BaseActivity implements GenericPostView<UserViewModel> {

    @Inject
    UpdateProfilePresenter mUpdateProfilePresenter;
    @Bind(R.id.etField)
    EditText etField;

    public static final String FULL_NAME = "full name", EMAIL = "email", BIRTH_DATE = "birth date", GENDER = "gender",
            LOCATION = "location", OCCUPATION = "occupation";

    @Override
    public void initialize() {
        getComponent().inject(this);
        mUpdateProfilePresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void postSuccessful(UserViewModel item) {

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
        showToastMessage(message);
    }

    @OnClick(R.id.btSave)
    void attemptSave(){
        HashMap<String, Object> updateBundle = null;
        switch (getIntent().getStringExtra("TYPE")){

            case FULL_NAME:
                updateBundle = new HashMap<>();
                updateBundle.put("full_name", etField.getText().toString());
                break;

            case EMAIL:
                updateBundle = new HashMap<>();
                updateBundle.put("email", etField.getText().toString());
                break;

            case BIRTH_DATE:
                updateBundle = new HashMap<>();
                updateBundle.put("birth_date", etField.getText().toString());
                break;

            case GENDER:
                updateBundle = new HashMap<>();
                updateBundle.put("gender", etField.getText().toString());
                break;

            case LOCATION:
                updateBundle = new HashMap<>();
                updateBundle.put("location", etField.getText().toString());
                break;

            case OCCUPATION:
                updateBundle = new HashMap<>();
                updateBundle.put("occupation", etField.getText().toString());
                break;
        }
        if (updateBundle != null)
            mUpdateProfilePresenter.post(updateBundle);
        else
            showError("ERROR");
    }

    public static Intent getCallingIntent(Context context, String type){
        return new Intent(context, UpdateProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("TYPE", type);
    }
}
