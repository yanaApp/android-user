package com.icaboalo.yana.presentation.screens.evaluation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.old.ui.fragment.AutoEvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.EvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.SelectEvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.TitleDescriptionFragment;
import com.icaboalo.yana.old.ui.widget.NonSwipeableViewPager;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.register.RegisterActivity;
import com.icaboalo.yana.util.EvaluationClickListener;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationActivity extends BaseActivity {

    @BindView(R.id.question_progress)
    ProgressBar mQuestionProgress;
    @BindView(R.id.question_text)
    TextView mQuestion;
    @BindArray(R.array.beck_test)
    String[] mQuestionList;

    int mQuestionPosition = 0, mAnswer = -1, mAnswerTotal = 0;
    private static int mTestNumber;

    public static final int REQUEST_CODE = 101;

    Button activeButton;

    @Override
    public void initialize() {

    }

    @Override
    public void setupUI() {
        setContentView(R.layout.fragment_evaluation);
        ButterKnife.bind(this);

        mQuestionProgress.setMax(mQuestionList.length - 1);
        mQuestionProgress.setProgress(mQuestionPosition + 1);


        mQuestion.setText(mQuestionList[mQuestionPosition]);
    }

    @OnClick({R.id.option_1, R.id.option_2, R.id.option_3, R.id.option_4, R.id.btContinue})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option_1:
                mAnswer = 1;
                activateButton((Button) v);
                mAnswer = 0;
                break;
            case R.id.option_2:
                mAnswer = 2;
                activateButton((Button) v);
                mAnswer = 1;
                break;
            case R.id.option_3:
                mAnswer = 3;
                activateButton((Button) v);
                mAnswer = 2;
                break;
            case R.id.option_4:
                mAnswer = 4;
                activateButton((Button) v);
                mAnswer = 3;
                break;
            case R.id.btContinue:
                if (mAnswer != -1) {
                    if (mQuestionPosition == mQuestionList.length - 1) {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PrefConstants.evaluationFile, Context.MODE_PRIVATE);
                        sharedPref.edit().putInt(PrefConstants.scorePref, mAnswerTotal).apply();

                        setResult(REQUEST_CODE, new Intent().putExtra("answer", mAnswerTotal).putExtra("testNumber", mTestNumber));
                        finish();

                    } else {
                        mAnswerTotal += mAnswer;
                        mQuestionPosition++;
                        mAnswer = -1;
                        activeButton.setSelected(false);
                        mQuestionProgress.setProgress(mQuestionPosition);
                        mQuestion.setText(mQuestionList[mQuestionPosition]);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Select one from the options above", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void activateButton(Button button) {
        if (activeButton != null)
            activeButton.setSelected(false);

        activeButton = button;
        button.setSelected(true);
    }

    public static Intent getCallingIntent(Context context, int testNumber) {
        mTestNumber = testNumber;
        return new Intent(context, EvaluationActivity.class);
    }
}
