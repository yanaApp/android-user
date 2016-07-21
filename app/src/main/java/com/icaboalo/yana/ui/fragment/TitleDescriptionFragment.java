package com.icaboalo.yana.ui.fragment;

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
 * Created by icaboalo on 21/06/16.
 */
public class TitleDescriptionFragment extends Fragment {

    TextView mTitle, mDescription;
    Button mContinueButton;
    EvaluationClickListener mEvaluationClickListener;

    public static TitleDescriptionFragment newInstance(String title, String description, String buttonText, String tag){
        TitleDescriptionFragment fragment = new TitleDescriptionFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putString("DESCRIPTION", description);
        args.putString("BUTTON_TEXT", buttonText);
        args.putString("TAG", tag);
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
        return inflater.inflate(R.layout.layout_title_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = (TextView) view.findViewById(R.id.title);
        mDescription = (TextView) view.findViewById(R.id.description);
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
