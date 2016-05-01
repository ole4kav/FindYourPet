package com.example.home.findyourpet;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity
{
    EditText userName;
    EditText userPhone;
    EditText userAddress;
    EditText userEmail;
    EditText userPassword;
    int flagSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        userName = (EditText) findViewById(R.id.nameEditText);
        userPhone = (EditText) findViewById(R.id.phoneEditText);
        userAddress = (EditText) findViewById(R.id.addressEditText);
        userEmail = (EditText) findViewById(R.id.emailEditText);
        userPassword = (EditText) findViewById(R.id.passwordEditText);
    }

    public void saveBtnClick(View view) {
        if (flagSave!=1) {
            flagSave = 1;
            DialogFragment newFragmentUser = new SaveUserFragment();
            newFragmentUser.show(getSupportFragmentManager(), "user");
        }
        if (flagSave == 1) {
            if( userEmail.getText().toString().trim().equals("")) {
                userEmail.setError( "Email is required!" );
                userEmail.setHint("please enter a e-mail");
            }
            if( userPassword.getText().toString().trim().equals("")) {
                userPassword.setError( "Password is required!" );
                userPassword.setHint("please enter a password");
            }
            else { }//.. go on to other activity }


        ///SAVE TO DATA!!!!!!
        }
    }
}
