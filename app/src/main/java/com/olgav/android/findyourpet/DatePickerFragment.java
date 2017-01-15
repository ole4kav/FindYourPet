package com.olgav.android.findyourpet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by HOME on 20/04/2016.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView dateTextView = (TextView) getActivity().findViewById(R.id.dateEditText);

        /*
        TextView dateTextView = null;
        if (getActivity().getLocalClassName().equals("NewFoundAd")){
            dateTextView = (TextView) getActivity().findViewById(R.id.dateEditText);
        }
        else if (getActivity().getLocalClassName().equals("NewLostAd")) {
            dateTextView = (TextView) getActivity().findViewById(R.id.dateEditTextLostAd);
        }
    */
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
        dateTextView.setText(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
    }
}
