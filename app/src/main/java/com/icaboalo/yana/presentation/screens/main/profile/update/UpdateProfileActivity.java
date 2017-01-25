package com.icaboalo.yana.presentation.screens.main.profile.update;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.Utils;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

/**
 * @author icaboalo on 25/08/16.
 */
public class UpdateProfileActivity extends BaseActivity implements GenericPostView<UserViewModel> {

    @Inject
    UpdateProfilePresenter mUpdateProfilePresenter;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etField)
    EditText etField;
    @BindView(R.id.spOptions)
    Spinner spOptions;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.btClear)
    TextView btClear;
    private static String mType, mInfo;

    public static final String FULL_NAME = "full_name", EMAIL = "email", GENDER = "gender",
            LOCATION = "location", OCCUPATION = "occupation", MOTIVE = "motive";

    @Override
    public void initialize() {
        getComponent().inject(this);
        mUpdateProfilePresenter.setView(this);
        mUpdateProfilePresenter.setId(PrefUtils.getUserId(getApplicationContext()));
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
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, btSave, message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (!mInfo.equals(etField.getText().toString()))
                    showConfirmationDialog();
                else
                    finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btClear)
    void clearText() {
        etField.setText("");
    }

    @OnClick(R.id.bt_save)
    void attemptSave() {
        HashMap<String, Object> updateBundle = null;
        switch (mType) {
            case FULL_NAME:
                updateBundle = new HashMap<>();
                updateBundle.put("full_name", etField.getText().toString());
                break;

            case EMAIL:
                updateBundle = new HashMap<>();
                updateBundle.put("email", etField.getText().toString());
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

            case MOTIVE:
                updateBundle = new HashMap<>();
                updateBundle.put("depression_motive", etField.getText().toString());
                break;
        }
        if (updateBundle != null)
            mUpdateProfilePresenter.post(updateBundle);
        else
            showError("ERROR");
    }

    @OnTextChanged(value = R.id.etField, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onEditTextChanged(CharSequence text) {
        if (!text.toString().equals(mInfo) || !text.toString().isEmpty()) {
            btSave.setVisibility(View.VISIBLE);
        } else {
            btSave.setVisibility(View.GONE);
        }
    }

    @OnItemSelected(value = R.id.spOptions, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void onSpinnerItemSelected(int position) {
        switch (mType) {
            case GENDER:
                etField.setVisibility(View.GONE);
                etField.setText(position + "");

                if (position == 0)
                    btSave.setVisibility(View.GONE);
                else
                    btSave.setVisibility(View.VISIBLE);
                break;
            case OCCUPATION:
                String[] occupations = getResources().getStringArray(R.array.occupations);
                if (position == occupations.length - 1) {
                    etField.setVisibility(View.VISIBLE);
                    etField.setText("");
                    btSave.setVisibility(View.VISIBLE);
                    btClear.setVisibility(View.VISIBLE);
                } else {
                    etField.setVisibility(View.GONE);
                    btSave.setVisibility(View.GONE);
                    btClear.setVisibility(View.GONE);
                    etField.setText(occupations[position]);
                }

                if (position == 0)
                    btSave.setVisibility(View.GONE);
                else
                    btSave.setVisibility(View.VISIBLE);
                break;

            case MOTIVE:
                String[] motives = getResources().getStringArray(R.array.motives);
                if (position == motives.length - 1) {
                    etField.setVisibility(View.VISIBLE);
                    etField.setText("");
                    btSave.setVisibility(View.VISIBLE);
                    btClear.setVisibility(View.VISIBLE);
                } else {
                    etField.setVisibility(View.GONE);
                    btSave.setVisibility(View.GONE);
                    btClear.setVisibility(View.GONE);
                    etField.setText(motives[position]);
                }

                if (position == 0)
                    btSave.setVisibility(View.GONE);
                else
                    btSave.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setInfo() {
        etField.setText(mInfo);
        ActionBar actionBar = getSupportActionBar();
        switch (mType) {
            case FULL_NAME:
                assert actionBar != null;
                actionBar.setTitle(R.string.full_name_title);
                spOptions.setVisibility(View.GONE);
                tvDescription.setText(R.string.description_full_name);
                break;
            case EMAIL:
                assert actionBar != null;
                actionBar.setTitle(R.string.email_title);
                spOptions.setVisibility(View.GONE);
                tvDescription.setText(R.string.description_email);
                break;

            case GENDER:
                assert actionBar != null;
                actionBar.setTitle(R.string.gender_title);
                spOptions.setVisibility(View.VISIBLE);
                btClear.setVisibility(View.GONE);
                String[] genders = getResources().getStringArray(R.array.genders);
                if (mInfo != null && !mInfo.isEmpty())
                    genders[0] = mInfo;
                ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                        genders);
                spOptions.setAdapter(genderAdapter);
                etField.setVisibility(View.GONE);
                btSave.setVisibility(View.GONE);
                tvDescription.setText(R.string.cupcake_ipsum);
                break;

            case LOCATION:
                assert actionBar != null;
                actionBar.setTitle(R.string.location_title);
                spOptions.setVisibility(View.GONE);
                etField.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                tvDescription.setText(R.string.description_location);
                break;

            case OCCUPATION:
                assert actionBar != null;
                actionBar.setTitle(R.string.occupation_title);
                spOptions.setVisibility(View.VISIBLE);
                String[] occupations = getResources().getStringArray(R.array.occupations);
                if (mInfo != null && !mInfo.isEmpty())
                    occupations[0] = mInfo;
                Log.d("INFO", mInfo);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        occupations);
                spOptions.setAdapter(arrayAdapter);
                etField.setVisibility(View.GONE);
                btSave.setVisibility(View.GONE);
                tvDescription.setText(R.string.description_occupation);
                break;

            case MOTIVE:
                assert actionBar != null;
                actionBar.setTitle(R.string.depression_motive_title);
                spOptions.setVisibility(View.VISIBLE);
                String[] motives = getResources().getStringArray(R.array.motives);
                if (mInfo != null && !mInfo.isEmpty())
                    motives[0] = mInfo;
                ArrayAdapter<String> motivesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        motives);
                spOptions.setAdapter(motivesAdapter);
                etField.setVisibility(View.GONE);
                btSave.setVisibility(View.GONE);
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
        }
    }

    private void showConfirmationDialog() {
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

    public static Intent getCallingIntent(Context context, String type, String info) {
        mType = type;
        mInfo = info;
        return new Intent(context, UpdateProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
