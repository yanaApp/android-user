package com.icaboalo.yana.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.icaboalo.yana.R;
import com.icaboalo.yana.ui.activity.LoginActivity;
import com.icaboalo.yana.ui.activity.MainActivity;
import com.icaboalo.yana.ui.activity.RegisterActivity;

/**
 * Created by icaboalo on 08/06/16.
 */
public class SelectEvaluationFragment extends Fragment {

    Button mEvaluationButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_evaluation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEvaluationButton = (Button) view.findViewById(R.id.skip_evaluation_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEvaluationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(getActivity(), LoginActivity.class);
                startActivity(goToRegister);
                getActivity().finish();
            }
        });
    }
}
