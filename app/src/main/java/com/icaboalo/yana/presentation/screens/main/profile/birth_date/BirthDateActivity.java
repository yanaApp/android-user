package com.icaboalo.yana.presentation.screens.main.profile.birth_date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class BirthDateActivity extends BaseActivity implements GenericPostView<UserViewModel> {

    @Inject
    BirthDatePresenter mBirthDatePresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_birth_date)
    Button btBirthDate;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;

    private static String mBirthDate;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mBirthDatePresenter.setView(this);
        mBirthDatePresenter.setUserId(PrefUtils.getUserId(getApplicationContext()));
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_birth_date);
        ButterKnife.bind(this);
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
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnTextChanged(value = R.id.bt_birth_date, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence charSequence) {
        if (!charSequence.toString().contentEquals(mBirthDate))
            btSave.setVisibility(View.VISIBLE);
        else
            btSave.setVisibility(View.GONE);
    }

    @OnClick(R.id.bt_birth_date)
    void onClick() {
        if (btBirthDate.getText().toString().contentEquals(getString(R.string.birth_date_title)))
            showBirthDialog(null);
        else
            showBirthDialog(btBirthDate.getText().toString());
    }

    @OnClick(R.id.bt_save)
    void onSaveClick() {
        HashMap<String, Object> postBundle = new HashMap<>();
        postBundle.put("birth_date", btBirthDate.getText().toString());
        postBundle.put("date_format", "MMMM dd, yyyy");
        mBirthDatePresenter.post(postBundle);
    }

    private void showBirthDialog(String birthDate) {
        if (birthDate == null || birthDate.isEmpty()) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(BirthDateActivity.this,
                    (view, year, month, dayOfMonth) ->
                            btBirthDate.setText(Utils.transformDateToText(dayOfMonth + "-" + (month + 1) + "-" + year, "dd-MM-yyyy",
                                    "MMMM dd, yy")), 1990, 0, 1);
            datePickerDialog.show();
        }
        else {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat("MMM dd yy").parse(birthDate));
                DatePickerDialog datePickerDialog = new DatePickerDialog(BirthDateActivity.this,
                        (view, year, month, dayOfMonth) ->
                                btBirthDate.setText(Utils.transformDateToText(dayOfMonth + "-" + (month + 1) + "-" + year, "dd-MM-yyyy",
                                        "MMMM dd, yy")), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
                DatePickerDialog datePickerDialog = new DatePickerDialog(BirthDateActivity.this,
                        (view, year, month, dayOfMonth) ->
                                btBirthDate.setText(Utils.transformDateToText(dayOfMonth + "-" + (month + 1) + "-" + year, "dd-MM-yyyy",
                                        "MMMM dd, yy")), 1990, 0, 1);
                datePickerDialog.show();
            }
        }
    }

    public static Intent getCallingIntent(Context context, String birthDate) {
        mBirthDate = birthDate;
        return new Intent(context, BirthDateActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
