package com.icaboalo.yana.old.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.io.ApiClient;
import com.icaboalo.yana.old.io.model.UserApiModel;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.llLoginForm)
    LinearLayout llLoginForm;
    @Bind(R.id.rlForgotPassword)
    RelativeLayout rlForgotPassword;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etEmail)
    EditText etEmail;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (rlForgotPassword.getVisibility() == View.VISIBLE){
            showLoginForm();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.login_button)
    void login(){
        if (etEmail.getText().toString().isEmpty()){
            etEmail.setError(getString(R.string.error_empty_field));
        }
        else if (etPassword.getText().toString().isEmpty()){
            etPassword.setError(getString(R.string.error_empty_field));
        }
        else {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage(getString(R.string.progress_dialog_login));
            mProgressDialog.show();
            UserApiModel user = new UserApiModel();
            user.setUserName(etEmail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            loginRetrofit(user);
        }
    }

    @OnClick(R.id.forgot_password)
    void showForgotPassword(){
        llLoginForm.setVisibility(View.GONE);
        rlForgotPassword.setVisibility(View.VISIBLE);
    }

    void showLoginForm(){
        rlForgotPassword.setVisibility(View.GONE);
        llLoginForm.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_recover_password)
    void recoverPassword(){
        if (etEmail.getText().toString().isEmpty()){
            etEmail.setError(getString(R.string.error_empty_field));
        } else {
            etEmail.setError(null);
            showDialog();
        }
    }

    void loginRetrofit(UserApiModel user){
        Call<UserApiModel> call = ApiClient.getApiService().login(user);
        call.enqueue(new Callback<UserApiModel>() {
            @Override
            public void onResponse(Call<UserApiModel> call, Response<UserApiModel> response) {
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
            public void onFailure(Call<UserApiModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
                mProgressDialog.dismiss();
            }
        });
    }

    void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Recuperación");
        alertDialog.setMessage("Por favor revisa tu correo electronico para restablecer tu contraseña.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showLoginForm();
            }
        });
        alertDialog.show();
    }
}
