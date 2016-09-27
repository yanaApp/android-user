package com.icaboalo.yana.presentation.screens.schedule;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.schedule.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.PrefUtils;

import java.security.Key;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

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
    @Bind(R.id.cbWorkout)
    SwitchCompat cbWorkout;
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
    @Bind(R.id.llStudyInfo)
    LinearLayout llStudyInfo;
    @Bind(R.id.llWorkInfo)
    LinearLayout llWorkInfo;
    @Bind(R.id.btStudyMonday)
    ToggleButton btStudyMonday;
    @Bind(R.id.btStudyTuesday)
    ToggleButton btStudyTuesday;
    @Bind(R.id.btStudyWednesday)
    ToggleButton btStudyWednesday;
    @Bind(R.id.btStudyThursday)
    ToggleButton btStudyThursday;
    @Bind(R.id.btStudyFriday)
    ToggleButton btStudyFriday;
    @Bind(R.id.btStudySaturday)
    ToggleButton btStudySaturday;
    @Bind(R.id.btStudySunday)
    ToggleButton btStudySunday;
    @Bind(R.id.btWorkMonday)
    ToggleButton btWorkMonday;
    @Bind(R.id.btWorkTuesday)
    ToggleButton btWorkTuesday;
    @Bind(R.id.btWorkWednesday)
    ToggleButton btWorkWednesday;
    @Bind(R.id.btWorkThursday)
    ToggleButton btWorkThursday;
    @Bind(R.id.btWorkFriday)
    ToggleButton btWorkFriday;
    @Bind(R.id.btWorkSaturday)
    ToggleButton btWorkSaturday;
    @Bind(R.id.btWorkSunday)
    ToggleButton btWorkSunday;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mSchedulePresenter.setView(this);
        mSchedulePresenter.setId(PrefUtils.getScheduleId(getApplicationContext()));
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_schedules);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        cbStudies.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(Menu.NONE, 1, Menu.FIRST, "SAVE");
        menuItem.setIcon(R.drawable.ic_done_black_24dp);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                if (validateData())
                    showConfirmationDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postSuccessful(ScheduleViewModel item) {
        navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
        finish();
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
                    llStudyInfo.setVisibility(View.VISIBLE);
                else{
                    llStudyInfo.setVisibility(View.GONE);
                    cbStudies.requestFocus();
                }
                break;
            case R.id.cbWork:
                if (checked)
                    llWorkInfo.setVisibility(View.VISIBLE);
                else {
                    llWorkInfo.setVisibility(View.GONE);
                    cbStudies.requestFocus();
                }
                break;
        }
    }

    @OnFocusChange({R.id.etStudyFrom, R.id.etStudyTo, R.id.etWorkFrom, R.id.etWorkTo, R.id.etWakeUp, R.id.etSleep, R.id.etBreakfast,
            R.id.etLunch, R.id.etDinner})
    void onFocusChanged(View view, boolean focus) {
        if (focus)
            switch (view.getId()) {
                case R.id.etStudyFrom:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etStudyForm.setText(i + ":0" + i1);
                        else
                            etStudyForm.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etStudyTo:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etStudyTo.setText(i + ":0" + i1);
                        else
                            etStudyTo.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWorkFrom:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etWorkFrom.setText(i + ":0" + i1);
                        else
                            etWorkFrom.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWorkTo:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etWorkTo.setText(i + ":0" + i1);
                        else
                            etWorkTo.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWakeUp:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etWakeUp.setText(i + ":0" + i1);
                        else
                            etWakeUp.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etSleep:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etSleep.setText(i + ":0" + i1);
                        else
                            etSleep.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etBreakfast:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etBreakfast.setText(i + ":0" + i1);
                        else
                            etBreakfast.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etLunch:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etLunch.setText(i + ":0" + i1);
                        else
                            etLunch.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etDinner:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        if (String.valueOf(i1).length() == 1)
                            etDinner.setText(i + ":0" + i1);
                        else
                            etDinner.setText(i + ":" + i1);
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
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
            if (etStudyForm.getText().toString().isEmpty()){
                etStudyForm.setError(getString(R.string.error_empty_field));
                return false;
            }
            else if (etStudyTo.getText().toString().isEmpty()){
                etStudyTo.setError(getString(R.string.error_empty_field));
                return false;
            }
        }

        if (cbWork.isChecked()) {
            if (etWorkFrom.getText().toString().isEmpty()) {
                etWorkFrom.setError(getString(R.string.error_empty_field));
                return false;
            }

            else if (etWorkTo.getText().toString().isEmpty()){
                etStudyTo.setError(getString(R.string.error_empty_field));
                return false;
            }
        }

        if (etWakeUp.getText().toString().isEmpty()){
            etWakeUp.setError(getString(R.string.error_empty_field));
            return false;
        }

        else if (etSleep.getText().toString().isEmpty()) {
            etSleep.setError(getString(R.string.error_empty_field));
            return false;
        }

        else if (etBreakfast.getText().toString().isEmpty()) {
            etBreakfast.setError(getString(R.string.error_empty_field));
            return false;
        }
        else if (etLunch.getText().toString().isEmpty()){
            etLunch.setError(getString(R.string.error_empty_field));
            return false;
        }
        else if (etDinner.getText().toString().isEmpty()){
            etDinner.setError(getString(R.string.error_empty_field));
            return false;
        }
        else
            return true;
    }

    private void showConfirmationDialog() {
        AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                .setTitle("Are your sure you want to save?")
                .setMessage("Once data is saved it will not be available for change.")
                .setPositiveButton("SAVE", (dialogInterface, i) -> {
                    HashMap<String, Object> postBundle = new HashMap<>();
                    if (cbStudies.isChecked()) {
                        postBundle.put("studies", true);
                        postBundle.put("studies_from", etStudyForm.getText().toString());
                        postBundle.put("studies_to", etStudyTo.getText().toString());
                        postBundle.put("studies_monday", btStudyMonday.isChecked());
                        postBundle.put("studies_tuesday", btStudyTuesday.isChecked());
                        postBundle.put("studies_wednesday", btStudyWednesday.isChecked());
                        postBundle.put("studies_thursday", btStudyThursday.isChecked());
                        postBundle.put("studies_friday", btStudyFriday.isChecked());
                        postBundle.put("studies_saturday", btStudySaturday.isChecked());
                        postBundle.put("studies_sunday", btStudySunday.isChecked());
                    } else
                        postBundle.put("studies", false);

                    if (cbStudies.isChecked()) {
                        postBundle.put("works", true);
                        postBundle.put("works_from", etWorkFrom.getText().toString());
                        postBundle.put("works_to", etWorkTo.getText().toString());
                        postBundle.put("works_monday", btWorkMonday.isChecked());
                        postBundle.put("works_tuesday", btWorkTuesday.isChecked());
                        postBundle.put("works_wednesday", btWorkWednesday.isChecked());
                        postBundle.put("works_thursday", btWorkThursday.isChecked());
                        postBundle.put("works_friday", btWorkFriday.isChecked());
                        postBundle.put("works_saturday", btWorkSaturday.isChecked());
                        postBundle.put("works_sunday", btWorkSunday.isChecked());
                    } else
                        postBundle.put("works", false);

                    if (cbWorkout.isChecked())
                        postBundle.put("workout", true);
                    else
                        postBundle.put("workout", false);

                    postBundle.put("wake_up", etWakeUp.getText().toString());
                    postBundle.put("sleep", etSleep.getText().toString());
                    postBundle.put("breakfast", etBreakfast.getText().toString());
                    postBundle.put("lunch", etLunch.getText().toString());
                    postBundle.put("dinner", etDinner.getText().toString());
                    mSchedulePresenter.post(postBundle);
                })
                .setNegativeButton("CANCEL", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .create();

        confirmationDialog.setCancelable(false);
        confirmationDialog.show();
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ScheduleActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}
