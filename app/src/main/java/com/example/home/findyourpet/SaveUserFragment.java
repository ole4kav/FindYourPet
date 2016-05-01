package com.example.home.findyourpet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by HOME on 25/04/2016.
 */
public class SaveUserFragment extends DialogFragment    //implements DatePickerDialog.OnDateSetListener
{
    LinearLayout regLinearLayout;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_text).setPositiveButton
                (R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        regLinearLayout = (LinearLayout) getActivity().findViewById(R.id.regLayout);
                        regLinearLayout.setVisibility(View.VISIBLE);
                    }
                }).setNegativeButton
                (R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        ///SAVE TO DATA!!!!!!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}


