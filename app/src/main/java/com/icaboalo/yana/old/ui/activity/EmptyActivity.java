package com.icaboalo.yana.old.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.fragment.AutoEvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.EvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.TitleDescriptionFragment;
import com.icaboalo.yana.old.ui.fragment.SelectEvaluationFragment;

public class EmptyActivity extends AppCompatActivity {

    private static Fragment[] mFragmentPages;
    int mFragmentPagesPosition = 0;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_evaluation);
        if (mFragmentPages == null){
            mFragmentPages = new Fragment[]{new AutoEvaluationFragment(),
                    TitleDescriptionFragment.newInstance(getString(R.string.depression_title), getString(R.string.depression_description), getString(R.string.continue_button), ""),
                    new SelectEvaluationFragment(),
                    TitleDescriptionFragment.newInstance(getString(R.string.beck_title), getString(R.string.beck_description), getString(R.string.continue_button), "")};
            nextFragment();
        }
    }


    public void nextFragment() {
        if (mFragmentPages.length == mFragmentPagesPosition) {
            Intent goToEvaluation = new Intent(this, EvaluationFragment.class);
            startActivity(goToEvaluation);
            finish();
        } else {
            fragmentManager.beginTransaction().replace(R.id.container, mFragmentPages[mFragmentPagesPosition]).commit();
            mFragmentPagesPosition++;
        }
    }

    public static Intent getCallingIntent(Context context,  Fragment[] fragmentPages){
        mFragmentPages = fragmentPages;
        return new Intent(context, EmptyActivity.class);
    }
}
