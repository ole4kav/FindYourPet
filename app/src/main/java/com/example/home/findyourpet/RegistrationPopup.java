package com.example.home.findyourpet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by HOME on 23/04/2016.
 */
public class RegistrationPopup extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_registration);

        DisplayMetrics gm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(gm);

        int width = gm.widthPixels;
        int height = gm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.4));
    }

    public void yesRegPopupBtn(View view) {
        Intent intent = new Intent(RegistrationPopup.this,LoginDialogFragment.class);
        startActivity(intent);
        finish();

    }

    public void noRegPopupBtn(View view) {
        Intent intent = new Intent(RegistrationPopup.this, ContactActivity.class);
        startActivity(intent);
        finish();
    }
}
