package com.icaboalo.yana.old.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.old.ui.fragment.AutoEvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.EvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.SelectEvaluationFragment;
import com.icaboalo.yana.old.ui.fragment.TitleDescriptionFragment;
import com.icaboalo.yana.old.ui.widget.NonSwipeableViewPager;
import com.icaboalo.yana.presentation.screens.register.RegisterActivity;
import com.icaboalo.yana.util.EvaluationClickListener;
import com.icaboalo.yana.presentation.screens.evaluation.TestResultFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationActivity extends AppCompatActivity implements EvaluationClickListener {

    @BindView(R.id.viewPager)
    NonSwipeableViewPager viewPager;

    private int mCurrentPosition = 0;
    private TestResultFragment testResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
        testResultFragment = TestResultFragment.newInstance();
        setupViewPager(createFragmentList());
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPosition > 0) {
            if (mCurrentPosition < 4){
                mCurrentPosition --;
                mCurrentPosition --;
                onClick();
            }
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onClick() {
        mCurrentPosition ++;
        if (mCurrentPosition == createFragmentList().size()){
            startActivity(RegisterActivity.getCallingIntent(this));
            finish();
        } else {
            viewPager.setCurrentItem(mCurrentPosition, true);
        }
    }

    @Override
    public void setAnswer(int answer) {
        getResultScreen(testResultFragment, answer);
    }

    private ArrayList<FragmentPagerModel> createFragmentList(){
        ArrayList<FragmentPagerModel> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentPagerModel(AutoEvaluationFragment.newInstance(0)));
        fragmentList.add(new FragmentPagerModel(TitleDescriptionFragment.newInstance(getString(R.string.depression_title),
                getString(R.string.depression_description),
                getString(R.string.continue_button),
                "")));
        fragmentList.add(new FragmentPagerModel(new SelectEvaluationFragment()));
        fragmentList.add(new FragmentPagerModel(TitleDescriptionFragment.newInstance(getString(R.string.beck_title),
                getString(R.string.beck_description),
                getString(R.string.continue_button),
                "")));
        fragmentList.add(new FragmentPagerModel(new EvaluationFragment()));
        fragmentList.add(new FragmentPagerModel(testResultFragment));
        fragmentList.add(new FragmentPagerModel(TitleDescriptionFragment.newInstance(getString(R.string.you_are_not_alone),
                getString(R.string.you_are_not_alone_description),
                getString(R.string.continue_button),
                "")));
        return fragmentList;
    }

    private void getResultScreen(TestResultFragment testResultFragment, int answer){
//        int answer = getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE).getInt(PrefConstants.scorePref, 0);
//        Toast.makeText(EvaluationActivity.this, "" + answer, Toast.LENGTH_SHORT).show();
        if  (answer > 0 && answer <= 10){
            getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE)
                    .edit()
                    .putInt(PrefConstants.evaluationPref, 1)
                    .apply();
            testResultFragment.setTitle("Ups & downs");
            testResultFragment.setDescription(getString(R.string.cupcake_ipsum));
        }

        else if (answer >= 11 && answer < 17){
            getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE)
                    .edit()
                    .putInt(PrefConstants.evaluationPref, 1)
                    .apply();
            testResultFragment.setTitle(getString(R.string.mild_depression));
            testResultFragment.setDescription(getString(R.string.cupcake_ipsum));
        }

        else if (answer >= 17 && answer < 31){
            getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE)
                    .edit()
                    .putInt(PrefConstants.evaluationPref, 2)
                    .apply();
            testResultFragment.setTitle(getString(R.string.moderate_depression));
            testResultFragment.setDescription(getString(R.string.cupcake_ipsum));
        }

        else if (answer >= 31){
            getSharedPreferences(PrefConstants.evaluationFile, MODE_PRIVATE)
                    .edit()
                    .putInt(PrefConstants.evaluationPref, 3)
                    .apply();
            testResultFragment.setTitle(getString(R.string.severe_depression));
            testResultFragment.setDescription(getString(R.string.cupcake_ipsum));
        }
    }

    private void setupViewPager(ArrayList<FragmentPagerModel> fragmentList){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
    }

}
