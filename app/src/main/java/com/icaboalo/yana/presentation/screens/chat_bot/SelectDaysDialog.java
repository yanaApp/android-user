package com.icaboalo.yana.presentation.screens.chat_bot;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ToggleButton;

import com.icaboalo.yana.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 29/01/17.
 */

public class SelectDaysDialog extends DialogFragment {

    @BindViews({R.id.btMonday, R.id.btTuesday, R.id.btWednesday, R.id.btThursday, R.id.btFriday, R.id.btSaturday, R.id.btSunday})
    List<ToggleButton> toggleButtons;
    private static OnDaysSelected mOnDaysSelected;

    public static SelectDaysDialog newInstance(OnDaysSelected onDaysSelected) {
        SelectDaysDialog fragment = new SelectDaysDialog();
        mOnDaysSelected = onDaysSelected;

        return fragment;
    }

    public interface OnDaysSelected {
        void selectedDays(ArrayList<String> days);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_days, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            ArrayList<String> days = new ArrayList<>();
            for (ToggleButton toggleButton: toggleButtons) {
                if (toggleButton.isChecked())
                    days.add((String) toggleButton.getTag());
            }
            mOnDaysSelected.selectedDays(days);
        });
        return builder.create();
    }
}
