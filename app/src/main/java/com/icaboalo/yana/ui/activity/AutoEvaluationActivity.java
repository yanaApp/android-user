package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.ui.fragment.AutoEvaluationFragment;
import com.icaboalo.yana.ui.fragment.SelectEvaluationFragment;
import com.icaboalo.yana.ui.fragment.TutorialPageFragment;

public class AutoEvaluationActivity extends AppCompatActivity implements AutoEvaluationFragment.OnAnswerSelect{

    Button mContinue;
    Fragment[] mFragmentPages = {new AutoEvaluationFragment(), new SelectEvaluationFragment()};
    int mFragmentPagesPosition = 0;
    int mAutoEvaluationAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_evaluation);
        mContinue = (Button) findViewById(R.id.continue_button);
        replaceFragment(mFragmentPages[mFragmentPagesPosition]);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentPagesPosition == mFragmentPages.length - 1){
                    Toast.makeText(AutoEvaluationActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    Intent goToEvaluation = new Intent(AutoEvaluationActivity.this, EvaluationActivity.class);
                    startActivity(goToEvaluation);
                    finish();
                } else {
                    if (mAutoEvaluationAnswer != 0){
                        mFragmentPagesPosition = (mFragmentPagesPosition + 1) % mFragmentPages.length;
                        replaceFragment(mFragmentPages[mFragmentPagesPosition]);
                    } else {
                        Toast.makeText(AutoEvaluationActivity.this, "Select one from above", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onAnswerSelect(int answer) {
        mAutoEvaluationAnswer = answer;
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
