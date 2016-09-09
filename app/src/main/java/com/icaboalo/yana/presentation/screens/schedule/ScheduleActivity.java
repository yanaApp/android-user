package com.icaboalo.yana.presentation.screens.schedule;

import android.app.TimePickerDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.schedule.view_model.ScheduleViewModel;

import java.security.Key;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @author icaboalo on 07/09/16.
 */
public class ScheduleActivity extends BaseActivity implements GenericPostView<ScheduleViewModel> {

    @Inject
    SchedulePresenter mSchedulePresenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cbStudies)
    SwitchCompat cbStudies;
    @Bind(R.id.cbWork)
    SwitchCompat cbWork;
    @Bind(R.id.llStudyHours)
    LinearLayout llStudyHours;
    @Bind(R.id.llWorkHours)
    LinearLayout llWorkHours;
    @Bind(R.id.etStudyFrom)
    EditText etStudyForm;
    @Bind(R.id.etStudyTo)
    EditText etStudyTo;
    @Bind(R.id.etWorkFrom)
    EditText etWorkFrom;
    @Bind(R.id.etWorkTo)
    EditText etWorkTo;
    @Bind(R.id.etWakeUp)
    EditText etWakeUp;
    @Bind(R.id.etSleep)
    EditText etSleep;
    @Bind(R.id.etBreakfast)
    EditText etBreakfast;
    @Bind(R.id.etLunch)
    EditText etLunch;
    @Bind(R.id.etDinner)
    EditText etDinner;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mSchedulePresenter.setView(this);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_schedules);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(Menu.NONE, 1, Menu.FIRST, "TEST");
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                if (validateData()) {
                    HashMap<String, Object> postBundle = new HashMap<>();
                    if (cbStudies.isChecked()) {
                        postBundle.put("studies", true);
                        postBundle.put("studies_from", etStudyForm.getText().toString());
                        postBundle.put("studies_to", etStudyTo.getText().toString());
                    } else
                        postBundle.put("studies", false);

                    if (cbStudies.isChecked()){
                        postBundle.put("works", true);
                        postBundle.put("works_from", etWorkFrom.getText().toString());
                        postBundle.put("works_to", etWorkTo.getText().toString());
                    } else
                        postBundle.put("works", false);

                    postBundle.put("wake_up", etWakeUp.getText().toString());
                    postBundle.put("sleep", etSleep.getText().toString());
                    postBundle.put("breakfast", etBreakfast.getText().toString());
                    postBundle.put("lunch", etLunch.getText().toString());
                    postBundle.put("dinner", etDinner.getText().toString());
                    mSchedulePresenter.post(postBundle);
                }
                else
                    showError("Debes llenar todos los campos");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postSuccessful(ScheduleViewModel item) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @OnCheckedChanged({R.id.cbStudies, R.id.cbWork})
    void onCheckChanged(CompoundButton view, boolean checked) {
        switch (view.getId()) {
            case R.id.cbStudies:
                if (checked)
                    llStudyHours.setVisibility(View.VISIBLE);
                else
                    llStudyHours.setVisibility(View.GONE);
                break;
            case R.id.cbWork:
                if (checked)
                    llWorkHours.setVisibility(View.VISIBLE);
                else
                    llWorkHours.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.etStudyFrom, R.id.etStudyTo, R.id.etWorkFrom, R.id.etWorkTo, R.id.etWakeUp, R.id.etSleep, R.id.etBreakfast,
            R.id.etLunch, R.id.etDinner})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.etStudyFrom:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etStudyForm.setText(i + ":0" + i1);
                    else
                        etStudyForm.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etStudyTo:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etStudyTo.setText(i + ":0" + i1);
                    else
                        etStudyTo.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etWorkFrom:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etWorkFrom.setText(i + ":0" + i1);
                    else
                        etWorkFrom.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etWorkTo:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etWorkTo.setText(i + ":0" + i1);
                    else
                        etWorkTo.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etWakeUp:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etWakeUp.setText(i + ":0" + i1);
                    else
                        etWakeUp.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etSleep:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etSleep.setText(i + ":0" + i1);
                    else
                        etSleep.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etBreakfast:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etBreakfast.setText(i + ":0" + i1);
                    else
                        etBreakfast.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etLunch:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etLunch.setText(i + ":0" + i1);
                    else
                        etLunch.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
            case R.id.etDinner:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    if (String.valueOf(i1).length() == 1)
                        etDinner.setText(i + ":0" + i1);
                    else
                        etDinner.setText(i + ":" + i1);
                }, 12, 0, false).show();
                break;
        }
    }

    private boolean validateData() {
        if (cbStudies.isChecked()) {
            if (etStudyForm.getText().toString().isEmpty())
                return false;
            else if (etStudyTo.getText().toString().isEmpty())
                return false;
        }

        if (cbWork.isChecked()) {
            if (etWorkFrom.getText().toString().isEmpty())
                return false;
            else if (etWorkTo.getText().toString().isEmpty())
                return false;
        }

        if (etWakeUp.getText().toString().isEmpty())
            return false;
        else if (etSleep.getText().toString().isEmpty())
            return false;
        else if (etBreakfast.getText().toString().isEmpty())
            return false;
        else if (etLunch.getText().toString().isEmpty())
            return false;
        else if (etDinner.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

}
