package com.icaboalo.yana.old.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.register.RegisterActivity;
import com.icaboalo.yana.util.EvaluationClickListener;

/**
 * Created by icaboalo on 08/06/16.
 */
public class SelectEvaluationFragment extends Fragment implements View.OnClickListener {

    Button mSkipEvaluationButton, mEvaluationButton;
    EvaluationClickListener mEvaluationClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEvaluationClickListener = (EvaluationClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSkipEvaluationButton = (Button) view.findViewById(R.id.skip_evaluation_button);
        mEvaluationButton = (Button) view.findViewById(R.id.evaluation_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSkipEvaluationButton.setOnClickListener(this);
        mEvaluationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip_evaluation_button:
                startActivity(RegisterActivity.getCallingIntent(getActivity()));
                getActivity().finish();
                break;
            case R.id.evaluation_button:
                mEvaluationClickListener.onClick();
                break;
        }
    }
}
