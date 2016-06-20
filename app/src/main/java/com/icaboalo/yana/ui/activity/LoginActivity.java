package com.icaboalo.yana.ui.activity;

import android.app.ProgressDialog;
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
import com.icaboalo.yana.io.model.ActionPlanApiModel;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.io.model.DayApiModel;
import com.icaboalo.yana.io.model.UserApiModel;
import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mUsername, mPassword;
    Button mLogin;
    ProgressDialog mProgressDialog;
    private static final String TAG = "LoginActivity";

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
                    mProgressDialog = new ProgressDialog(LoginActivity.this);
                    mProgressDialog.setMessage(getString(R.string.progress_dialog_login));
                    mProgressDialog.show();
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
                if (response.isSuccessful()) {
                    mProgressDialog.dismiss();
                    SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                    sharedPreferences.edit().putString(PrefConstants.tokenPref, "Token " + response.body().getToken()).apply();
                    Intent goToLoading = new Intent(LoginActivity.this, LoadingActivity.class);
                    startActivity(goToLoading);
                    finish();
                }else{
                    mProgressDialog.dismiss();
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
                mProgressDialog.dismiss();
            }
        });
    }
}
