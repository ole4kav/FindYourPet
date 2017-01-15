package com.olgav.android.findyourpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FoundAdPropertyActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_property);

        TextView descTextView = (TextView) findViewById(R.id.propertyDataDescFTextView);
        TextView collarTextView = (TextView) findViewById(R.id.propertyDataCollarFTextView);

        TextView userNameTextView = (TextView) findViewById(R.id.propertyDataNameFTextView);
        TextView userPhoneTextView = (TextView) findViewById(R.id.propertyDataPhoneFTextView);
        TextView userAddressTextView = (TextView) findViewById(R.id.propertyDataAddressFTextView);

        View userLayout = findViewById(R.id.propertyUserFLayout);
        View userTitleLayout = findViewById(R.id.propertyTitleUserFLayout);

        Intent intent = getIntent();
        User currentUser = null;

        if(intent.hasExtra("detailsf")) {
            Ad current = intent.getParcelableExtra("detailsf");
            descTextView.setText(current.getDescription());
            collarTextView.setText(current.getYesno());
        }

        if(intent.hasExtra("userf")) {
            currentUser = intent.getParcelableExtra("userf");
        }

        if ((currentUser == null)||(currentUser.toString().trim()=="")) {
            userLayout.setVisibility(View.GONE);
            userTitleLayout.setVisibility(View.GONE);
        }
        else {
            if (currentUser.getName() != null) {
                userNameTextView.setText(currentUser.getName());
            }
            if (currentUser.getPhone() != "") {
                userPhoneTextView.setText(currentUser.getPhone());
            }
            if (currentUser.getAddress() != "") {
                userAddressTextView.setText(currentUser.getAddress());
            }
        }
    }
}

