package com.icaboalo.yana.presentation.screens.main.profile.update;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.main.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
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
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etField)
    EditText etField;
    @Bind(R.id.spOptions)
    Spinner spOptions;
    @Bind(R.id.tvDescription)
    TextView tvDescription;
    @Bind(R.id.btSave)
    Button btSave;
    private static String mType, mInfo;

    public static final String FULL_NAME = "full_name", EMAIL = "email", BIRTH_DATE = "birth_date", GENDER = "gender",
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
        Log.d("Array", getResources().getStringArray(R.array.genders).toString());
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
                if (!mInfo.equals(etField.getText().toString()))
                    showConfirmationDialog();
                else
                    finish();
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
    void onEditTextChanged(CharSequence text){
        if (!text.toString().equals(mInfo)){
            btSave.setVisibility(View.VISIBLE);
        } else {
            btSave.setVisibility(View.GONE);
        }
    }

    @OnItemSelected(value = R.id.spOptions, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void onSpinnerItemSelected(int position){
        switch (mType){
            case GENDER:
                String[] genders = getResources().getStringArray(R.array.genders);
                if (position == genders.length - 1) {
                    etField.setVisibility(View.VISIBLE);
                    etField.setText("");
                }
                else {
                    etField.setVisibility(View.GONE);
                    etField.setText(genders[position]);
                }

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
                }
                else {
                    etField.setVisibility(View.GONE);
                    etField.setText(occupations[position]);
                }

                if (position == 0)
                    btSave.setVisibility(View.GONE);
                else
                    btSave.setVisibility(View.VISIBLE);
                break;

            //TODO add motives
            case MOTIVE:
                String[] motives = getResources().getStringArray(R.array.motives);
                if (position == motives.length - 1) {
                    etField.setVisibility(View.VISIBLE);
                    etField.setText("");
                }
                else {
                    etField.setVisibility(View.GONE);
                    etField.setText(motives[position]);
                }

                if (position == 0)
                    btSave.setVisibility(View.GONE);
                else
                    btSave.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setInfo(){
        etField.setText(mInfo);
        ActionBar actionBar = getSupportActionBar();
        switch (mType){
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
            case BIRTH_DATE:
                assert actionBar != null;
                actionBar.setTitle(R.string.birth_date_title);
                spOptions.setVisibility(View.GONE);
                tvDescription.setText(R.string.cupcake_ipsum);
                break;
            case GENDER:
                assert actionBar != null;
                actionBar.setTitle(R.string.gender_title);
                spOptions.setVisibility(View.VISIBLE);
                spOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.genders)));
                etField.setVisibility(View.GONE);
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
                spOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.occupations)));
                etField.setVisibility(View.GONE);
                tvDescription.setText(R.string.description_occupation);
                break;

            //TODO change source for list
            case MOTIVE:
                assert actionBar != null;
                actionBar.setTitle("Depression Motive");
                spOptions.setVisibility(View.VISIBLE);
                spOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        new ArrayList()));
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
