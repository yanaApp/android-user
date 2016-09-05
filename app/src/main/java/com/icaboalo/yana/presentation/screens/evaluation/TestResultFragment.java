package com.icaboalo.yana.presentation.screens.evaluation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.util.EvaluationClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 05/09/16.
 */
public class TestResultFragment extends BaseFragment {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvDescription)
    TextView tvDescription;
    @Bind(R.id.btContinue)
    Button btContinue;

    EvaluationClickListener mEvaluationClickListener;

    public static TestResultFragment newInstance(){
        return new TestResultFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEvaluationClickListener = (EvaluationClickListener) context;
    }

    @Override
    public void initialize() {
        btContinue.setText(R.string.continue_button);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_title_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btContinue)
    void onClick(){
        if (mEvaluationClickListener != null)
            mEvaluationClickListener.onClick();
    }

    public void setTitle(String title){
        this.tvTitle.setText(title);
    }

    public void setDescription(String description){
        this.tvDescription.setText(description);
    }
}
