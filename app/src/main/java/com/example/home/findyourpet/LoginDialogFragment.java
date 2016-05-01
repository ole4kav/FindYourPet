package com.example.home.findyourpet;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginDialogFragment extends Activity
{
    Button btnSignIn;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);

        // create the instance of Databse
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        }
        catch (SQLException e) {e.printStackTrace();}

        // Get The Refference Of Buttons
        btnSignIn = (Button)findViewById(R.id.buttonSignIn);
    }


    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(LoginDialogFragment.this);

        dialog.setContentView(R.layout.dialog_login);
        dialog.setTitle("Login");

        // get the Refferences of views
        final EditText editTextUserName = (EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final  EditText editTextPassword = (EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get The User name and Password
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword)) {
                    Toast.makeText(LoginDialogFragment.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(LoginDialogFragment.this, "User Name and Does Not Matches", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}