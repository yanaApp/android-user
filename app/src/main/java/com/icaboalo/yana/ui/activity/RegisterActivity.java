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
                    Log.d("INTENT", "main");
                    Intent goToMain = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(goToMain);
                    finish();
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
}
