package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.realm.UserModel;

import java.io.IOException;
import java.security.PrivilegedAction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mUsername, mPassword;
    Button mLogin, mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (TextInputEditText) findViewById(R.id.username_input);
        mPassword = (TextInputEditText) findViewById(R.id.password_input);
        mLogin = (Button) findViewById(R.id.login_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsername.getText().toString().isEmpty()){
                    mUsername.setError(getString(R.string.error_empty_field));
                }
                else if (mPassword.getText().toString().isEmpty()){
                    mPassword.setError(getString(R.string.error_empty_field));
                }
                else {
                    UserModel user = new UserModel();
                    user.setUserName(mUsername.getText().toString());
                    user.setPassword(mPassword.getText().toString());
                    loginRetrofit(user);
                }
            }
        });
    }

    void loginRetrofit(UserModel user){
        Call<UserModel> call = ApiClient.getApiService().login(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                    sharedPreferences.edit().putString(PrefConstants.tokenPref, "Token " + response.body().getToken()).apply();
                    Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(goToMain);
                }else{
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}
