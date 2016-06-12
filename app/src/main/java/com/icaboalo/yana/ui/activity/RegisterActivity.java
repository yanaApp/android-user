package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.UserApiModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icaboalo.yana.R.string.error_empty_field;
import static com.icaboalo.yana.R.string.error_invalid_email;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText mFullNameInput, mUsernameInput, mEmailInput, mPasswordInput;
    Button mRegisterButton;
    TextView mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullNameInput = (TextInputEditText) findViewById(R.id.full_name_input);
        mUsernameInput = (TextInputEditText) findViewById(R.id.username_input);
        mEmailInput = (TextInputEditText) findViewById(R.id.email_input);
        mPasswordInput = (TextInputEditText) findViewById(R.id.password_input);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        assert mRegisterButton != null;
        mRegisterButton.setOnClickListener(this);

        mLoginButton = (TextView) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                if (mFullNameInput.getText().toString().isEmpty() || mFullNameInput.getText().toString().length() < 2){
                    mFullNameInput.setError(getString(error_empty_field));
                } else if (mUsernameInput.getText().toString().isEmpty() || mUsernameInput.getText().toString().length() < 2){
                    mUsernameInput.setError(getString(error_empty_field));
                } else if (mEmailInput.getText().toString().isEmpty() || mEmailInput.getText().toString().length() < 2){
                    mEmailInput.setError(getString(error_empty_field));
                } else if (!mEmailInput.getText().toString().contains("@")){
                    mEmailInput.setError(getString(error_invalid_email));
                } else if (mPasswordInput.getText().toString().isEmpty() || mPasswordInput.getText().toString().length() < 2){
                    mPasswordInput.setError(getString(error_empty_field));
                } else {
                    UserApiModel user = new UserApiModel();
                    user.setUserName(mUsernameInput.getText().toString());
                    user.setPassword(mPasswordInput.getText().toString());
                    user.setEmail(mEmailInput.getText().toString());
                    userRegisterAPI(user);
                }
                break;
            case R.id.login_button:
                Log.d("INTENT", "login");
                Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLogin);
                finish();
                break;
        }
    }

    void userRegisterAPI(UserApiModel user){
        Call<UserApiModel> call = ApiClient.getApiService().userRegister(user);
        call.enqueue(new Callback<UserApiModel>() {
            @Override
            public void onResponse(Call<UserApiModel> call, Response<UserApiModel> response) {
                if (response.isSuccessful()){
                    Log.d("RETROFIT_SUCCESS", "success");
                    Log.d("INTENT", "main");
                    Intent goToMain = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(goToMain);
                    finish();
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserApiModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
            }
        });
    }
}
