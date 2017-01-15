package com.olgav.android.findyourpet;

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
    Ad myNewAdToSave;
    int adFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_registration);

        Intent intent = getIntent();
        if(intent.hasExtra("newAd")) {
            myNewAdToSave = intent.getParcelableExtra("newAd");
        }
        if(intent.hasExtra("adFlag")) {
            adFlag = intent.getIntExtra("adFlag", 0);
        }

        intent.putExtra("adFlag", 1);       //lost

        DisplayMetrics gm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(gm);

        int width = gm.widthPixels;
        int height = gm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.4));
    }

    public void yesRegPopupBtn(View view) {
        Intent intent = new Intent(RegistrationPopup.this, LoginDialogFragment.class);
        startActivity(intent);
        finish();

    }

    public void noRegPopupBtn(View view) {
        Intent intent = new Intent(RegistrationPopup.this, UserDetailsActivity.class);
        if (myNewAdToSave!=null) {
            intent.putExtra("newAd", myNewAdToSave);
            intent.putExtra("adFlag", adFlag);
            startActivity(intent);
            finish();
        }
    }
}
