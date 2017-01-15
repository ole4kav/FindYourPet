package com.olgav.android.findyourpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LostAdPropertyActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_property);

        TextView descTextView = (TextView) findViewById(R.id.propertyDataDescTextView);
        TextView ifTookTextView = (TextView) findViewById(R.id.propertyDataIfTookTextView);

        TextView userNameTextView = (TextView) findViewById(R.id.propertyDataNameTextView);
        TextView userPhoneTextView = (TextView) findViewById(R.id.propertyDataPhoneTextView);
        TextView userAddressTextView = (TextView) findViewById(R.id.propertyDataAddressTextView);

        View userLayout = findViewById(R.id.propertyUserLayout);
        View userTitleLayout = findViewById(R.id.propertyTitleUserLayout);

        Intent intent = getIntent();
        User currentUser = null;

        if(intent.hasExtra("details")) {
            Ad current = intent.getParcelableExtra("details");
            descTextView.setText(current.getDescription());
            ifTookTextView.setText(current.getYesno());
        }

        if(intent.hasExtra("user")) {
            currentUser = intent.getParcelableExtra("user");
        }

        if ((currentUser == null)||(currentUser.toString().trim()=="")) {
            userLayout.setVisibility(View.INVISIBLE);
            userTitleLayout.setVisibility(View.INVISIBLE);
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
