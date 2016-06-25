package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;

import static com.icaboalo.yana.R.color.orange;
import static com.icaboalo.yana.R.color.white;

public class EvaluationActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressBar mQuestionProgress;
    TextView mQuestion;
    TextView mOption1, mOption2, mOption3, mOption4;
    Button mContinueButton;
    String[] mQuestionList;
    int mQuestionPosition = 0;
    int mAnswer = -1;
    int mAnswerTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionList = getResources().getStringArray(R.array.beck_test);

        setContentView(R.layout.activity_evaluation);
        mQuestionProgress = (ProgressBar) findViewById(R.id.question_progress);
        mQuestionProgress.setMax(mQuestionList.length);
        mQuestionProgress.setProgress(mQuestionPosition + 1);
        mQuestion = (TextView) findViewById(R.id.question_text);
        mQuestion.setText(mQuestionList[mQuestionPosition]);
        mOption1 = (TextView) findViewById(R.id.option_1);
        mOption1.setOnClickListener(this);
        mOption2 = (TextView) findViewById(R.id.option_2);
        mOption2.setOnClickListener(this);
        mOption3 = (TextView) findViewById(R.id.option_3);
        mOption3.setOnClickListener(this);
        mOption4 = (TextView) findViewById(R.id.option_4);
        mOption4.setOnClickListener(this);
        mContinueButton = (Button) findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.option_1:
                mAnswer = 1;
                mOption1.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                mOption2.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption3.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption4.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mAnswer = 0;
                break;
            case R.id.option_2:
                mAnswer = 2;
                mOption1.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption2.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                mOption3.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption4.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mAnswer = 1;
                break;
            case R.id.option_3:
                mAnswer = 3;
                mOption1.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption2.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption3.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                mOption4.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mAnswer = 2;
                break;
            case R.id.option_4:
                mAnswer = 4;
                mOption1.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption2.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption3.setBackground(getResources().getDrawable(R.drawable.circle_white));
                mOption4.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                mAnswer = 3;
                break;
            case R.id.continue_button:
                if (mAnswer != -1){
                    if (mQuestionPosition == mQuestionList.length -1){
                        SharedPreferences sharedPref = getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE);
                        sharedPref.edit().putInt(PrefConstants.evaluationPref, mAnswerTotal).apply();
                        Intent goToRegister = new Intent(EvaluationActivity.this, RegisterActivity.class);
                        startActivity(goToRegister);
                        finish();
                    } else {
                        mAnswerTotal += mAnswer;
                        mQuestionPosition++;
                        mAnswer = -1;
                        mOption1.setBackground(getResources().getDrawable(R.drawable.circle_white));
                        mOption2.setBackground(getResources().getDrawable(R.drawable.circle_white));
                        mOption3.setBackground(getResources().getDrawable(R.drawable.circle_white));
                        mOption4.setBackground(getResources().getDrawable(R.drawable.circle_white));
                        mQuestionProgress.setProgress(mQuestionPosition);
                        mQuestion.setText(mQuestionList[mQuestionPosition]);
                    }
                } else {
                    mContinueButton.setError("Select one from the options above");
                    Toast.makeText(EvaluationActivity.this, "Select one from the options above", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
