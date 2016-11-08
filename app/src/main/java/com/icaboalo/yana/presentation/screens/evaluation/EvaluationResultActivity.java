package com.icaboalo.yana.presentation.screens.evaluation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationResultActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDescription)
    TextView tvMessage;

    @Override
    public void initialize() {
        parseAnswer(getIntent().getIntExtra("answerTotal", 0));
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.layout_title_description);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btContinue)
    void onClick() {
        navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
        finish();
    }

    private void parseAnswer(int answerTotal) {
        if (answerTotal > 0 && answerTotal <= 10) {
            tvTitle.setText("Ups & downs");
            tvMessage.setText(R.string.cupcake_ipsum);
        }
        else if (answerTotal >= 11 && answerTotal < 17) {
            tvTitle.setText(R.string.mild_depression);
            tvMessage.setText(R.string.cupcake_ipsum);

        }
        else if (answerTotal >= 17 && answerTotal < 31) {
            tvTitle.setText(R.string.moderate_depression);
            tvMessage.setText(R.string.cupcake_ipsum);
        }
        else if (answerTotal > 31) {
            tvTitle.setText(R.string.severe_depression);
            tvMessage.setText(R.string.cupcake_ipsum);
        }
    }

    public static Intent getCallingIntent(Context context, int answerTotal) {
        return new Intent(context, EvaluationResultActivity.class).putExtra("answerTotal", answerTotal);
    }
}
