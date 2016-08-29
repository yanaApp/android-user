package com.icaboalo.yana.presentation.screens.action_plan.profile.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author icaboalo on 25/08/16.
 */
public class UpdateProfileActivity extends BaseActivity implements GenericPostView<UserViewModel> {

    @Inject
    UpdateProfilePresenter mUpdateProfilePresenter;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etField)
    EditText etField;
    @Bind(R.id.tvDescription)
    TextView tvDescription;
    @Bind(R.id.btSave)
    Button btSave;
    private static String mType, mInfo;

    public static final String FULL_NAME = "full name", EMAIL = "email", BIRTH_DATE = "birth date", GENDER = "gender",
            LOCATION = "location", OCCUPATION = "occupation";

    @Override
    public void initialize() {
        getComponent().inject(this);
        mUpdateProfilePresenter.setView(this);
        mUpdateProfilePresenter.setId(59);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInfo();
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
                showConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btClear)
    void clearText(){
        etField.setText("");
    }

    @OnClick(R.id.btSave)
    void attemptSave(){
        HashMap<String, Object> updateBundle = null;
        switch (mType){
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

    @OnTextChanged(value = R.id.etField, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onEditTextChanged(CharSequence text){
        if (!text.toString().equals(mInfo)){
            btSave.setVisibility(View.VISIBLE);
        } else {
            btSave.setVisibility(View.GONE);
        }
    }

    private void setInfo(){
        etField.setText(mInfo);
        switch (mType){
            case FULL_NAME:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case EMAIL:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case BIRTH_DATE:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case GENDER:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case LOCATION:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case OCCUPATION:
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
        }
    }

    private void showConfirmationDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage(R.string.cupcake_ipsum)
                .setPositiveButton(R.string.exit, (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNegativeButton(R.string.stay, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    public static Intent getCallingIntent(Context context, String type, String info){
        mType = type;
        mInfo = info;
        return new Intent(context, UpdateProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
