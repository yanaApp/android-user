package com.icaboalo.yana.presentation.screens.schedule;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.notification.BreakfastReceiver;
import com.icaboalo.yana.presentation.notification.DinnerReceiver;
import com.icaboalo.yana.presentation.notification.LunchReceiver;
import com.icaboalo.yana.presentation.notification.SleepReceiver;
import com.icaboalo.yana.presentation.notification.WakeUpReceiver;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.Utils;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import static com.icaboalo.yana.PrefConstants.*;
import static com.icaboalo.yana.PrefConstants.NIGHT_NOTIFICATION_TIME;

/**
 * @author icaboalo on 07/09/16.
 */
public class ScheduleActivity extends BaseActivity implements GenericPostView<ScheduleViewModel> {

    @Inject
    SchedulePresenter mSchedulePresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cbStudies)
    SwitchCompat cbStudies;
    @BindView(R.id.cbWork)
    SwitchCompat cbWork;
    @BindView(R.id.cbWorkout)
    SwitchCompat cbWorkout;
    @BindView(R.id.etStudyFrom)
    EditText etStudyForm;
    @BindView(R.id.etStudyTo)
    EditText etStudyTo;
    @BindView(R.id.etWorkFrom)
    EditText etWorkFrom;
    @BindView(R.id.etWorkTo)
    EditText etWorkTo;
    @BindView(R.id.etWakeUp)
    EditText etWakeUp;
    @BindView(R.id.etSleep)
    EditText etSleep;
    @BindView(R.id.etBreakfast)
    EditText etBreakfast;
    @BindView(R.id.etLunch)
    EditText etLunch;
    @BindView(R.id.etDinner)
    EditText etDinner;
    @BindView(R.id.llStudyInfo)
    LinearLayout llStudyInfo;
    @BindView(R.id.llWorkInfo)
    LinearLayout llWorkInfo;
    @BindView(R.id.btStudyMonday)
    ToggleButton btStudyMonday;
    @BindView(R.id.btStudyTuesday)
    ToggleButton btStudyTuesday;
    @BindView(R.id.btStudyWednesday)
    ToggleButton btStudyWednesday;
    @BindView(R.id.btStudyThursday)
    ToggleButton btStudyThursday;
    @BindView(R.id.btStudyFriday)
    ToggleButton btStudyFriday;
    @BindView(R.id.btStudySaturday)
    ToggleButton btStudySaturday;
    @BindView(R.id.btStudySunday)
    ToggleButton btStudySunday;
    @BindView(R.id.btWorkMonday)
    ToggleButton btWorkMonday;
    @BindView(R.id.btWorkTuesday)
    ToggleButton btWorkTuesday;
    @BindView(R.id.btWorkWednesday)
    ToggleButton btWorkWednesday;
    @BindView(R.id.btWorkThursday)
    ToggleButton btWorkThursday;
    @BindView(R.id.btWorkFriday)
    ToggleButton btWorkFriday;
    @BindView(R.id.btWorkSaturday)
    ToggleButton btWorkSaturday;
    @BindView(R.id.btWorkSunday)
    ToggleButton btWorkSunday;

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
//        TODO -> add dialog texts
        showDialog("Title", getString(R.string.cupcake_ipsum), null);
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
                else {
                    showError(getString(R.string.error_incomplete_form));
                }
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
                else {
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
                        etStudyForm.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etStudyTo:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etStudyTo.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWorkFrom:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etWorkFrom.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWorkTo:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etWorkTo.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etWakeUp:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etWakeUp.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etSleep:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etSleep.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etBreakfast:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etBreakfast.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etLunch:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etLunch.setText(Utils.transformTo24Hours(i, i1));
                    }, 12, 0, false).show();
                    ((EditText) view).setError(null);
                    break;
                case R.id.etDinner:
                    new TimePickerDialog(this, (timePicker, i, i1) -> {
                        etDinner.setText(Utils.transformTo24Hours(i, i1));
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
                    etStudyForm.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etStudyTo:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etStudyTo.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etWorkFrom:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etWorkFrom.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etWorkTo:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etWorkTo.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etWakeUp:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etWakeUp.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etSleep:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etSleep.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etBreakfast:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etBreakfast.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etLunch:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etLunch.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
            case R.id.etDinner:
                new TimePickerDialog(this, (timePicker, i, i1) -> {
                    etDinner.setText(Utils.transformTo24Hours(i, i1));
                }, 12, 0, false).show();
                break;
        }
    }

    private boolean validateData() {
        if (cbStudies.isChecked()) {
            if (etStudyForm.getText().toString().isEmpty()) {
                etStudyForm.setError(getString(R.string.error_empty_field));
                return false;
            } else if (etStudyTo.getText().toString().isEmpty()) {
                etStudyTo.setError(getString(R.string.error_empty_field));
                return false;
            }
        }

        if (cbWork.isChecked()) {
            if (etWorkFrom.getText().toString().isEmpty()) {
                etWorkFrom.setError(getString(R.string.error_empty_field));
                return false;
            } else if (etWorkTo.getText().toString().isEmpty()) {
                etStudyTo.setError(getString(R.string.error_empty_field));
                return false;
            }
        }

        if (etWakeUp.getText().toString().isEmpty()) {
            etWakeUp.setError(getString(R.string.error_empty_field));
            return false;
        } else if (etSleep.getText().toString().isEmpty()) {
            etSleep.setError(getString(R.string.error_empty_field));
            return false;
        } else if (etBreakfast.getText().toString().isEmpty()) {
            etBreakfast.setError(getString(R.string.error_empty_field));
            return false;
        } else if (etLunch.getText().toString().isEmpty()) {
            etLunch.setError(getString(R.string.error_empty_field));
            return false;
        } else if (etDinner.getText().toString().isEmpty()) {
            etDinner.setError(getString(R.string.error_empty_field));
            return false;
        } else
            return true;
    }

    private void showConfirmationDialog() {
        AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                .setTitle("Are your sure you want to save?")
                .setMessage("Once data is saved it will not be available for change.")
                .setPositiveButton("SAVE", (dialogInterface, i) -> {
                    HashMap<String, Object> postBundle = new HashMap<>();
                    if (cbStudies.isChecked()) {
                        postBundle.put("study", true);
                        postBundle.put("study_from", etStudyForm.getText().toString());
                        postBundle.put("study_to", etStudyTo.getText().toString());
                        postBundle.put("study_monday", btStudyMonday.isChecked());
                        postBundle.put("study_tuesday", btStudyTuesday.isChecked());
                        postBundle.put("study_wednesday", btStudyWednesday.isChecked());
                        postBundle.put("study_thursday", btStudyThursday.isChecked());
                        postBundle.put("study_friday", btStudyFriday.isChecked());
                        postBundle.put("study_saturday", btStudySaturday.isChecked());
                        postBundle.put("study_sunday", btStudySunday.isChecked());
                    } else
                        postBundle.put("study", false);

                    if (cbStudies.isChecked()) {
                        postBundle.put("work", true);
                        postBundle.put("work_from", etWorkFrom.getText().toString());
                        postBundle.put("work_to", etWorkTo.getText().toString());
                        postBundle.put("work_monday", btWorkMonday.isChecked());
                        postBundle.put("work_tuesday", btWorkTuesday.isChecked());
                        postBundle.put("work_wednesday", btWorkWednesday.isChecked());
                        postBundle.put("work_thursday", btWorkThursday.isChecked());
                        postBundle.put("work_friday", btWorkFriday.isChecked());
                        postBundle.put("work_saturday", btWorkSaturday.isChecked());
                        postBundle.put("work_sunday", btWorkSunday.isChecked());
                    } else
                        postBundle.put("work", false);

                    if (cbWorkout.isChecked())
                        postBundle.put("workout", true);
                    else
                        postBundle.put("workout", false);

                    postBundle.put("wake_up_time", etWakeUp.getText().toString());
                    Utils.createNotification(getApplicationContext(), etWakeUp.getText().toString(), WakeUpReceiver.class, WakeUpReceiver.id, AlarmManager.INTERVAL_DAY);
                    mSchedulePresenter.attemptSaveNotificationTime(DAY_NOTIFICATION_TIME, etWakeUp.getText().toString());
                    postBundle.put("sleep_time", etSleep.getText().toString());
                    Utils.createNotification(getApplicationContext(), etSleep.getText().toString(), SleepReceiver.class, SleepReceiver.id, AlarmManager.INTERVAL_DAY);
                    mSchedulePresenter.attemptSaveNotificationTime(NIGHT_NOTIFICATION_TIME, etSleep.getText().toString());
                    postBundle.put("breakfast_time", etBreakfast.getText().toString());
                    Utils.createNotification(getApplicationContext(), etBreakfast.getText().toString(), BreakfastReceiver.class, BreakfastReceiver.id, AlarmManager.INTERVAL_DAY);
                    mSchedulePresenter.attemptSaveNotificationTime(BREAKFAST_NOTIFICATION_TIME, etBreakfast.getText().toString());
                    postBundle.put("lunch_time", etLunch.getText().toString());
                    Utils.createNotification(getApplicationContext(), etLunch.getText().toString(), LunchReceiver.class, LunchReceiver.id, AlarmManager.INTERVAL_DAY);
                    mSchedulePresenter.attemptSaveNotificationTime(LUNCH_NOTIFICATION_TIME, etLunch.getText().toString());
                    postBundle.put("dinner_time", etDinner.getText().toString());
                    Utils.createNotification(getApplicationContext(), etDinner.getText().toString(), DinnerReceiver.class, DinnerReceiver.id, AlarmManager.INTERVAL_DAY);
                    mSchedulePresenter.attemptSaveNotificationTime(DINNER_NOTIFICATION_TIME, etDinner.getText().toString());
                    mSchedulePresenter.post(postBundle);
                })
                .setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        confirmationDialog.setCancelable(false);
        confirmationDialog.show();
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ScheduleActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


}
