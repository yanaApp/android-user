package com.icaboalo.yana.presentation.screens.evaluation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.view_model.ActionPlanViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationActivity extends BaseActivity implements GenericPostView<ActionPlanViewModel> {

    @Inject
    EvaluationPresenter mEvaluationPresenter;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
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
        getComponent().inject(this);
        mEvaluationPresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.fragment_evaluation);
        ButterKnife.bind(this);

        mQuestionProgress.setMax(mQuestionList.length - 1);
        mQuestionProgress.setProgress(mQuestionPosition + 1);


        mQuestion.setText(mQuestionList[mQuestionPosition]);
    }


    @Override
    public void postSuccessful(ActionPlanViewModel item) {
        hideLoading();
        navigator.navigateTo(getApplicationContext(), EvaluationResultActivity.getCallingIntent(getApplicationContext(), mAnswerTotal));
        finish();
    }

    @Override
    public void showLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, mQuestion, message, Snackbar.LENGTH_SHORT);
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

                        if (mTestNumber != 0) {
                            setResult(REQUEST_CODE, new Intent().putExtra("answer", mAnswerTotal).putExtra("testNumber", mTestNumber));
                            finish();
                        }
                        else {
                            HashMap<String, Object> postBundle = new HashMap<>();
                            if (mAnswerTotal < 17)
                                postBundle.put("category", 1);
                            else if (mAnswerTotal < 31)
                                postBundle.put("category", 2);
                            else
                                postBundle.put("category", 3);
                            mEvaluationPresenter.post(postBundle);
                        }



                    } else {
                        mAnswerTotal += mAnswer;
                        mQuestionPosition++;
                        mAnswer = -1;
                        activeButton.setSelected(false);
                        mQuestionProgress.setProgress(mQuestionPosition);
                        mQuestion.setText(mQuestionList[mQuestionPosition]);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_no_answer_selected, Toast.LENGTH_SHORT).show();
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
        return new Intent(context, EvaluationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
