package com.example.home.findyourpet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity
{
    EditText userName;
    EditText userPhone;
    EditText userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        userName = (EditText) findViewById(R.id.nameEditText);
        userPhone = (EditText) findViewById(R.id.phoneEditText);
        userAddress = (EditText) findViewById(R.id.addressEditText);

        userName.setOnFocusChangeListener(UIhelper.myFocus);
        userPhone.setOnFocusChangeListener(UIhelper.myFocus);
        userAddress.setOnFocusChangeListener(UIhelper.myFocus);
    }

    public void saveBtnClick(View view) {
    }
}
