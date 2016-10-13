package com.icaboalo.yana.presentation.screens.schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.icaboalo.yana.presentation.screens.schedule.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import static android.content.Context.ALARM_SERVICE;

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
                    createNotification(etWakeUp.getText().toString(), WakeUpReceiver.class, WakeUpReceiver.id);
                    mSchedulePresenter.attemptSaveNotificationTime(SchedulePresenter.wakeUpnotification, etWakeUp.getText().toString());
                    postBundle.put("sleep", etSleep.getText().toString());
                    createNotification(etSleep.getText().toString(), SleepReceiver.class, SleepReceiver.id);
                    mSchedulePresenter.attemptSaveNotificationTime(SchedulePresenter.sleepNotification, etSleep.getText().toString());
                    postBundle.put("breakfast", etBreakfast.getText().toString());
                    createNotification(etBreakfast.getText().toString(), BreakfastReceiver.class, BreakfastReceiver.id);
                    mSchedulePresenter.attemptSaveNotificationTime(SchedulePresenter.sleepNotification, etBreakfast.getText().toString());
                    postBundle.put("lunch", etLunch.getText().toString());
                    createNotification(etLunch.getText().toString(), LunchReceiver.class, LunchReceiver.id);
                    mSchedulePresenter.attemptSaveNotificationTime(SchedulePresenter.lunchNotification, etLunch.getText().toString());
                    postBundle.put("dinner", etDinner.getText().toString());
                    createNotification(etDinner.getText().toString(), DinnerReceiver.class, DinnerReceiver.id);
                    mSchedulePresenter.attemptSaveNotificationTime(SchedulePresenter.dinnerNotification, etDinner.getText().toString());
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

    private void createNotification(String time, Class broadcastReceiverClass, int id) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

        try {
            Log.d("TIME", date24Format.format(date12Format.parse(time)));
            String hour = date24Format.format(date12Format.parse(time));

            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour.substring(0, 2)));
            calendar.set(Calendar.MINUTE, Integer.valueOf(hour.substring(3, 5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("CALENDAR", calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes());

        Intent intent = new Intent(getApplicationContext(), broadcastReceiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
