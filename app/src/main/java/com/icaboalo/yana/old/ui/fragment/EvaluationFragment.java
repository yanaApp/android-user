package com.icaboalo.yana.old.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.util.EvaluationClickListener;

public class EvaluationFragment extends Fragment implements View.OnClickListener{

    ProgressBar mQuestionProgress;
    TextView mQuestion;
    TextView mOption1, mOption2, mOption3, mOption4;
    Button mContinueButton;
    EvaluationClickListener mEvaluationClickListener;
    String[] mQuestionList;
    int mQuestionPosition = 0;
    int mAnswer = -1;
    int mAnswerTotal = 0;

    Button activeButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEvaluationClickListener = (EvaluationClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuestionList = getResources().getStringArray(R.array.beck_test);

        mQuestionProgress = (ProgressBar) view.findViewById(R.id.question_progress);
        mQuestionProgress.setMax(mQuestionList.length - 1);
        mQuestionProgress.setProgress(mQuestionPosition + 1);
        mQuestion = (TextView) view.findViewById(R.id.question_text);
        mQuestion.setText(mQuestionList[mQuestionPosition]);
        mOption1 = (TextView) view.findViewById(R.id.option_1);
        mOption1.setOnClickListener(this);
        mOption2 = (TextView) view.findViewById(R.id.option_2);
        mOption2.setOnClickListener(this);
        mOption3 = (TextView) view.findViewById(R.id.option_3);
        mOption3.setOnClickListener(this);
        mOption4 = (TextView) view.findViewById(R.id.option_4);
        mOption4.setOnClickListener(this);
        mContinueButton = (Button) view.findViewById(R.id.btContinue);
        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.option_1:
                mAnswer = 1;
                activateButton((Button) mOption1);
                mAnswer = 0;
                break;
            case R.id.option_2:
                mAnswer = 2;
                activateButton((Button) mOption2);
                mAnswer = 1;
                break;
            case R.id.option_3:
                mAnswer = 3;
                activateButton((Button) mOption3);
                mAnswer = 2;
                break;
            case R.id.option_4:
                mAnswer = 4;
                activateButton((Button) mOption4);
                mAnswer = 3;
                break;
            case R.id.btContinue:
                if (mAnswer != -1){
                    if (mQuestionPosition == mQuestionList.length -1){
                        SharedPreferences sharedPref = getActivity().getSharedPreferences(PrefConstants.evaluationFile, Context.MODE_PRIVATE);
                        sharedPref.edit().putInt(PrefConstants.scorePref, mAnswerTotal).apply();
                        mEvaluationClickListener.setAnswer(mAnswerTotal);
                        mEvaluationClickListener.onClick();
                    } else {
                        mAnswerTotal += mAnswer;
                        mQuestionPosition++;
                        mAnswer = -1;
                        activeButton.setSelected(false);
                        mQuestionProgress.setProgress(mQuestionPosition);
                        mQuestion.setText(mQuestionList[mQuestionPosition]);
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error_no_answer_selected, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void activateButton(Button button){
        if (activeButton != null)
            activeButton.setSelected(false);

        activeButton = button;
        button.setSelected(true);
    }
}
