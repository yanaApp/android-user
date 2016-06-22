package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.icaboalo.yana.R;
import com.icaboalo.yana.ui.fragment.AutoEvaluationFragment;
import com.icaboalo.yana.ui.fragment.TitleDescriptionFragment;
import com.icaboalo.yana.ui.fragment.SelectEvaluationFragment;

public class AutoEvaluationActivity extends AppCompatActivity{

    Fragment[] mFragmentPages;
    int mFragmentPagesPosition = 0;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_evaluation);
        mFragmentPages  = new Fragment[]{new AutoEvaluationFragment(),
                TitleDescriptionFragment.newInstance(getString(R.string.depression_title), getString(R.string.depression_description), getString(R.string.continue_button), ""),
                new SelectEvaluationFragment(),
                TitleDescriptionFragment.newInstance(getString(R.string.beck_title), getString(R.string.beck_description), getString(R.string.continue_button), "")};
        nextFragment();
    }


    public void nextFragment(){
        if (mFragmentPages.length == mFragmentPagesPosition){
            Intent goToEvaluation = new Intent(this, EvaluationActivity.class);
            startActivity(goToEvaluation);
            finish();
        } else {
            fragmentManager.beginTransaction().replace(R.id.container, mFragmentPages[mFragmentPagesPosition]).commit();
            mFragmentPagesPosition++;
        }
    }
}
