package com.icaboalo.yana.ui.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.UserApiModel;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvDescription)
    TextView tvDescription;
    @Bind(R.id.etField)
    EditText etField;
    @Bind(R.id.tvClear)
    TextView tvClear;
    @Bind(R.id.btSave)
    Button btSave;
    Bundle mBundle;
    private static final String TAG = "EditProfileActivity";


    ProgressDialog mProgressDialog;
    private Realm mRealmInstance;

    public static final String INFO_TYPE = "Information Type";

    public static final String INFO_FULL_NAME = "Full Name";
    public static final String INFO_EMAIL = "Email";
    public static final String INFO_PASSWORD = "Password";
    public static final String INFO_PHONE_NUMBER = "Phone Number";
    public static final String INFO_BIRTH_DATE = "Birth Date";
    public static final String INFO_GENDER = "Gender";
    public static final String INFO_LOCATION = "Location";
    public static final String INFO_OCCUPATION = "Occupation";
    public static final String CONTENT = "Content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealmInstance = Realm.getDefaultInstance();
        setContentView(R.layout.activity_edit_profile);
        mBundle = getIntent().getExtras();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setInfo();

        etField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btSave.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmInstance.close();
    }

    @OnClick(R.id.tvClear)
    void clear(){
        etField.setText("");
    }

    @OnClick(R.id.etField)
    void clickableText(){
        switch ((String) etField.getTag()){

            case INFO_BIRTH_DATE:
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etField.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                }, 2016, 01, 01);
                datePickerDialog.show();
                break;

        }
    }

    @OnClick(R.id.btSave)
    void saveInfo(){
        UserApiModel user = new UserApiModel();
        switch (mBundle.getString(INFO_TYPE, "")){
            case INFO_FULL_NAME:
                user.setFullName(etField.getText().toString());
                break;

            case INFO_EMAIL:
                user.setEmail(etField.getText().toString());
                break;

            case INFO_PHONE_NUMBER:
                user.setPhoneNumber(etField.getText().toString());
                break;

            case INFO_BIRTH_DATE:
                user.setBirthDate(etField.getText().toString());
                break;

            case INFO_GENDER:
                user.setGender(etField.getText().toString());
                break;

            case INFO_LOCATION:
                user.setLocation(etField.getText().toString());
                break;
            case INFO_OCCUPATION:
                user.setOccupation(etField.getText().toString());
                break;
        }
        mProgressDialog = new ProgressDialog(EditProfileActivity.this);
        mProgressDialog.setMessage("Saving");
        mProgressDialog.show();
        Call<UserApiModel> call = ApiClient.getApiService().updateUserInfo(PrefUtils.getToken(this), user, RealmUtils.getUser(mRealmInstance).getId());
        call.enqueue(new Callback<UserApiModel>() {
            @Override
            public void onResponse(Call<UserApiModel> call, Response<UserApiModel> response) {
                if (response.isSuccessful()){
                    RealmUtils.updateUser(mRealmInstance, response.body());
                    mProgressDialog.dismiss();
                    finish();
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                        mProgressDialog.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserApiModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
                mProgressDialog.dismiss();
            }
        });
    }

    public static Intent getCallingIntent(Context context, String type, String content){
        Intent nIntent = new Intent(context, EditProfileActivity.class);
        nIntent.putExtra(EditProfileActivity.INFO_TYPE, type);
        nIntent.putExtra(EditProfileActivity.CONTENT, content);
        return nIntent;
    }

    void setInfo(){
        ActionBar actionBar = getSupportActionBar();
        etField.setText(mBundle.getString(CONTENT));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        switch (mBundle.getString(INFO_TYPE, "")){
            case INFO_FULL_NAME:
                actionBar.setTitle("Full name");
                tvDescription.setText("");
                etField.setTag(INFO_FULL_NAME);
                break;

            case INFO_EMAIL:
                actionBar.setTitle("Email");
                tvDescription.setText("");
                etField.setTag(INFO_EMAIL);
                break;

            case INFO_PHONE_NUMBER:
                actionBar.setTitle("Phone number");
                tvDescription.setText("");
                etField.setTag(INFO_PHONE_NUMBER);
                break;

            case INFO_BIRTH_DATE:
                actionBar.setTitle("Birth date");
                tvDescription.setText("");
                etField.setTag(INFO_BIRTH_DATE);
                break;

            case INFO_GENDER:
                actionBar.setTitle("Gender");
                tvDescription.setText("");
                etField.setTag(INFO_GENDER);
                break;

            case INFO_LOCATION:
                actionBar.setTitle("Location");
                tvDescription.setText("");
                etField.setTag(INFO_LOCATION);
                break;
            case INFO_OCCUPATION:
                actionBar.setTitle("Occupation");
                tvDescription.setText("");
                etField.setTag(INFO_OCCUPATION);
                break;
        }
    }
}
