package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;

import java.security.PrivilegedAction;

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
                    loginRetrofit();
                }
            }
        });
    }

    void loginRetrofit(){
        SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
        sharedPreferences.edit().putString(PrefConstants.tokenPref, "TOKEN").apply();
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
    }
}
