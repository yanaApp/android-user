package com.icaboalo.yana.old.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.util.EvaluationClickListener;

/**
 * Created by icaboalo on 20/07/16.
 */
public class TestResultFragment extends Fragment {

    TextView mTitle, mDescription;
    Button mContinueButton;
    EvaluationClickListener mEvaluationClickListener;

    public static TestResultFragment newInstance(String title, String description, String buttonText, String tag){
        TestResultFragment fragment = new TestResultFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putString("DESCRIPTION", description);
        args.putString("BUTTON_TEXT", buttonText);
        args.putString("TAG", tag);
        fragment.setArguments(args);
        return fragment;
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setDescription(String description){
        mDescription.setText(description);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEvaluationClickListener = (EvaluationClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_title_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = (TextView) view.findViewById(R.id.tvTitle);
        mDescription = (TextView) view.findViewById(R.id.tvDescription);
        mContinueButton = (Button) view.findViewById(R.id.btContinue);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTitle.setText(getArguments().getString("TITLE"));
        mDescription.setText(getArguments().getString("DESCRIPTION"));
        mContinueButton.setText(getArguments().getString("BUTTON_TEXT"));

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEvaluationClickListener != null)
                    mEvaluationClickListener.onClick();
            }
        });
    }
}
