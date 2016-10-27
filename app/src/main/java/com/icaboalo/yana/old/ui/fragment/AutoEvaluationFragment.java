package com.icaboalo.yana.old.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.util.EvaluationClickListener;

/**
 * Created by icaboalo on 08/06/16.
 */
public class AutoEvaluationFragment extends Fragment implements View.OnClickListener {

    LinearLayout mOption1, mOption2, mOption3;
    Button mContinueButton;
    int mAnswer = 0;
    EvaluationClickListener mEvaluationClickListener;

    public static AutoEvaluationFragment newInstance(int answer){
        AutoEvaluationFragment fragment = new AutoEvaluationFragment();
        Bundle args = new Bundle();
        args.putInt("ANSWER", answer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEvaluationClickListener = (EvaluationClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auto_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOption1 = (LinearLayout) view.findViewById(R.id.option_1);
        mOption2 = (LinearLayout) view.findViewById(R.id.option_2);
        mOption3 = (LinearLayout) view.findViewById(R.id.option_3);
        mContinueButton = (Button) view.findViewById(R.id.btContinue);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAnswer = getArguments().getInt("ANSWER", 0);
        switch (mAnswer) {
            case 1:
                mOption1.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption2.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption3.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                mOption2.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption1.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption3.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                mOption3.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption1.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption2.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
        mOption1.setOnClickListener(this);
        mOption2.setOnClickListener(this);
        mOption3.setOnClickListener(this);
        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.option_1:
                v.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption2.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption3.setBackgroundColor(Color.parseColor("#ffffff"));
                mAnswer = 1;
                break;

            case R.id.option_2:
                v.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption1.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption3.setBackgroundColor(Color.parseColor("#ffffff"));
                mAnswer = 2;
                break;

            case R.id.option_3:
                v.setBackgroundColor(getActivity().getResources().getColor(R.color.opaque_yana_green));
                mOption1.setBackgroundColor(Color.parseColor("#ffffff"));
                mOption2.setBackgroundColor(Color.parseColor("#ffffff"));
                mAnswer = 3;
                break;
            case R.id.btContinue:
                if (mAnswer == 0){
                    Toast.makeText(getActivity(), R.string.no_answer_selecter, Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(PrefConstants.evaluationFile, Context.MODE_PRIVATE);
                    sharedPref.edit().putInt(PrefConstants.evaluationPref, mAnswer).apply();
//                    mEvaluationClickListener.setAnswer(mAnswer);
                    mEvaluationClickListener.onClick();
                }
                break;
        }
    }
}
