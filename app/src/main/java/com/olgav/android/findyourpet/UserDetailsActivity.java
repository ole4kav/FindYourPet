package com.olgav.android.findyourpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UserDetailsActivity extends AppCompatActivity
{
    EditText userName;
    EditText userPhone;
    EditText userAddress;
    EditText userEmail;
    EditText userPassword;
    int flagSave = 0;
    LinearLayout regLinearLayout;

    static final String NAME = "userNameText";
    static final String PHONE = "userPhoneText";
    static final String ADDRESS = "userAddressText";
    static final String EMAIL = "userEmailText";
    static final String PASSWORD = "userPasswordText";
    static final String FLAG = "flagSave";
    static final String REGISTRATION = "ifRegistrationVisible";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Ad myNewAdToSave;
    int adFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Intent intent = getIntent();
        if(intent.hasExtra("newAd")) {myNewAdToSave = intent.getParcelableExtra("newAd");}
        if(intent.hasExtra("adFlag")) {adFlag = intent.getIntExtra("adFlag", 0);}

        userName = (EditText) findViewById(R.id.nameEditText);
        userPhone = (EditText) findViewById(R.id.phoneEditText);
        userAddress = (EditText) findViewById(R.id.addressEditText);
        userEmail = (EditText) findViewById(R.id.emailEditText);
        userPassword = (EditText) findViewById(R.id.passwordEditText);
        regLinearLayout = (LinearLayout) findViewById(R.id.regLayout);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(NAME, userName.getText().toString());
        savedInstanceState.putString(PHONE, userPhone.getText().toString());
        savedInstanceState.putString(ADDRESS, userAddress.getText().toString());
        savedInstanceState.putString(EMAIL, userEmail.getText().toString());
        savedInstanceState.putString(PASSWORD, userPassword.getText().toString());
        savedInstanceState.putInt(FLAG, flagSave);
        savedInstanceState.putInt(REGISTRATION, regLinearLayout.getVisibility());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        userName.setText(savedInstanceState.getString(NAME));
        userPhone.setText(savedInstanceState.getString(PHONE));
        userAddress.setText(savedInstanceState.getString(ADDRESS));
        userEmail.setText(savedInstanceState.getString(EMAIL));
        userPassword.setText(savedInstanceState.getString(PASSWORD));
        flagSave = savedInstanceState.getInt(FLAG);

        regLinearLayout.setVisibility(savedInstanceState.getInt(REGISTRATION));
    }

    public void saveBtnClick(View view) {
        if (flagSave != 1) {
            flagSave = 1;
            DialogFragment newFragmentUser = new SaveUserFragment();
            newFragmentUser.show(getSupportFragmentManager(), "user");
        }
        else if (flagSave == 1) {
            if (userEmail.getText().toString().trim().equals("")) {
                userEmail.setError("Email is required!");
                userEmail.setHint("please enter a e-mail");
            }
            if (userPassword.getText().toString().trim().equals("")) {
                userPassword.setError("Password is required!");
                userPassword.setHint("please enter a password");
            }
            saveToData();
            Gson gson = new Gson();
            String s = gson.toJson(myNewAdToSave);
            URL url = null;
            if (adFlag==2){
                try {url = new URL("https://findyourpet-9ca26.firebaseio.com/ads/found.json");}
                catch (MalformedURLException e) {e.printStackTrace();}
            }
            else if (adFlag==1){
                try {url = new URL("https://findyourpet-9ca26.firebaseio.com/ads/lost.json");}
                catch (MalformedURLException e) {e.printStackTrace();}
            }
            NetworkHelper.postToServer(s, url);

            Intent homeIntent= new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    private void saveToData() {
        User userSave = saveNewUser();
        myNewAdToSave.setAdOwner(userSave);
    }

    private User saveNewUser() {
        User userToSave = new User();
        String  uniqueID = UUID.randomUUID().toString();
        userToSave.setName(userName.getText().toString());
        userToSave.setPhone(userPhone.getText().toString());
        userToSave.setAddress(userAddress.getText().toString());
        userToSave.setEmail(userEmail.getText().toString());
        userToSave.setPassword(userPassword.getText().toString());
        userToSave.setId(uniqueID);

        return userToSave;
    }
}
