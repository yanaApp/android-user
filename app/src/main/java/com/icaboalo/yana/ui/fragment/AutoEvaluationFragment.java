package com.icaboalo.yana.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icaboalo.yana.R;

/**
 * Created by icaboalo on 08/06/16.
 */
public class AutoEvaluationFragment extends Fragment implements View.OnClickListener {

    CardView mOption1, mOption2, mOption3;
    OnAnswerSelect mOnAnswerSelect;

    public interface OnAnswerSelect {
        void onAnswerSelect(int answer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnAnswerSelect = (OnAnswerSelect) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auto_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOption1 = (CardView) view.findViewById(R.id.option_1);
        mOption2 = (CardView) view.findViewById(R.id.option_2);
        mOption3 = (CardView) view.findViewById(R.id.option_3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mOption1.setOnClickListener(this);
        mOption2.setOnClickListener(this);
        mOption3.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.option_1:
                v.setElevation(0);
                mOption2.setElevation(16);
                mOption3.setElevation(16);
                mOnAnswerSelect.onAnswerSelect(1);
                break;
            case R.id.option_2:
                mOption1.setElevation(16);
                v.setElevation(0);
                mOption3.setElevation(16);
                mOnAnswerSelect.onAnswerSelect(2);
                break;
            case R.id.option_3:
                mOption1.setElevation(16);
                mOption2.setElevation(16);
                v.setElevation(0);
                mOnAnswerSelect.onAnswerSelect(3);
                break;
        }
    }
}
