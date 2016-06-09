package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.R;

import static com.icaboalo.yana.R.color.orange;
import static com.icaboalo.yana.R.color.white;

public class EvaluationActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressBar mQuestionProgress;
    TextView mQuestion;
    View mOption1, mOption2, mOption3, mOption4;
    Button mContinueButton;
    String[] mQuestionList = {"Question 1", "Question 2", "Question 3", "Question 4", "Question 5"};
    int mQuestionPosition = 0;
    int mAnswer = 0;
    int mAnswerTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        mQuestionProgress = (ProgressBar) findViewById(R.id.question_progress);
        mQuestionProgress.setMax(20);
        mQuestionProgress.setProgress(mQuestionPosition);
        mQuestion = (TextView) findViewById(R.id.question_text);
        mQuestion.setText(mQuestionList[mQuestionPosition]);
        mOption1 = findViewById(R.id.option_1);
        mOption1.setOnClickListener(this);
        mOption2 = findViewById(R.id.option_2);
        mOption2.setOnClickListener(this);
        mOption3 = findViewById(R.id.option_3);
        mOption3.setOnClickListener(this);
        mOption4 = findViewById(R.id.option_4);
        mOption4.setOnClickListener(this);
        mContinueButton = (Button) findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.option_1:
                mAnswer = 1;
                mOption1.setBackgroundColor(getResources().getColor(orange));
                mOption2.setBackgroundColor(getResources().getColor(white));
                mOption3.setBackgroundColor(getResources().getColor(white));
                mOption4.setBackgroundColor(getResources().getColor(white));
                break;
            case R.id.option_2:
                mAnswer = 2;
                mOption1.setBackgroundColor(getResources().getColor(white));
                mOption2.setBackgroundColor(getResources().getColor(orange));
                mOption3.setBackgroundColor(getResources().getColor(white));
                mOption4.setBackgroundColor(getResources().getColor(white));
                break;
            case R.id.option_3:
                mAnswer = 3;
                mOption1.setBackgroundColor(getResources().getColor(white));
                mOption2.setBackgroundColor(getResources().getColor(white));
                mOption3.setBackgroundColor(getResources().getColor(orange));
                mOption4.setBackgroundColor(getResources().getColor(white));
                break;
            case R.id.option_4:
                mAnswer = 4;
                mOption1.setBackgroundColor(getResources().getColor(white));
                mOption2.setBackgroundColor(getResources().getColor(white));
                mOption3.setBackgroundColor(getResources().getColor(white));
                mOption4.setBackgroundColor(getResources().getColor(orange));
                break;
            case R.id.continue_button:
                if (mAnswer != 0){
                    if (mQuestionPosition == mQuestionList.length -1){
                        Intent goToRegister = new Intent(EvaluationActivity.this, RegisterActivity.class);
                        startActivity(goToRegister);
                        finish();
                    } else {
                        mAnswerTotal += mAnswer;
                        mQuestionPosition++;
                        mAnswer = 0;
                        mOption1.setBackgroundColor(getResources().getColor(white));
                        mOption2.setBackgroundColor(getResources().getColor(white));
                        mOption3.setBackgroundColor(getResources().getColor(white));
                        mOption4.setBackgroundColor(getResources().getColor(white));
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
