package com.olgav.android.findyourpet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import java.util.UUID;


public class NewLostAd extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    static final String DATE1 = "dateText1";
    static final String PHOTO1 = "imageView1";
    static final String LOCATION1 = "locationText1";
    static final String DESCRIPTION1 = "descriptionText1";
    static final String VISIBILITY1 ="visibility1";

    TextView locationTextViewLost;
    TextView dateTextViewLost;
    TextView descTextViewLost;
    ImageView petImageViewLost;
    RadioButton sexF;
    RadioButton sexM;
    RadioButton collarY;
    RadioButton collarN;

    Button nextBtnLost;

    Location globalLocation = null;
    private LocationManager locationManager;
    //private LocationListener locationListener;

    private Context context;

    private GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    Bitmap image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_lost);

        petImageViewLost = (ImageView) findViewById(R.id.dogImageViewLostAd);
        locationTextViewLost = (TextView) findViewById(R.id.locationEditTextLostAd);
        dateTextViewLost = (TextView) findViewById(R.id.dateEditText);
        descTextViewLost = (TextView) findViewById(R.id.editTextLostAd);
        sexM = (RadioButton) findViewById(R.id.sexMRadioBtnL);
        sexF = (RadioButton) findViewById(R.id.sexFRadioBtnL);
        collarN = (RadioButton) findViewById(R.id.collarNRadioBtnL);
        collarY = (RadioButton) findViewById(R.id.collarYRadioBtnL);

        nextBtnLost = (Button) findViewById(R.id.saveButtonLostAd);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        context = this;


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                    globalLocation = location;
                    float lat = (float) globalLocation.getLatitude();
                    float lon = (float) globalLocation.getLongitude();
                    SharedPreferences.Editor editor = getSharedPreferences("MyLocation", MODE_PRIVATE).edit();
                    editor.putFloat("latitude", lat);
                    editor.putFloat("longitude", lon);
                    editor.commit();
                ImageButton gpsBtn = (ImageButton) findViewById(R.id.locationImageButtonLostAd);
                gpsBtn.setVisibility(View.VISIBLE);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {globalLocation = new Location(LocationManager.GPS_PROVIDER);}
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        getNowLocation();
    }
    /*
    public void getNowLocation() {
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        }
        globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if ((globalLocation == null)) {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);}
        else {
            globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if ((globalLocation == null)) {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);}
        }
        */
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(DATE1, dateTextViewLost.getText().toString());
        savedInstanceState.putString(LOCATION1, locationTextViewLost.getText().toString());
        savedInstanceState.putString(DESCRIPTION1, descTextViewLost.getText().toString());
        savedInstanceState.putParcelable(PHOTO1, image1);
        savedInstanceState.putInt(VISIBILITY1, nextBtnLost.getVisibility());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dateTextViewLost.setText(savedInstanceState.getString(DATE1));
        locationTextViewLost.setText(savedInstanceState.getString(LOCATION1));
        descTextViewLost.setText(savedInstanceState.getString(DESCRIPTION1));
        image1 = savedInstanceState.getParcelable(PHOTO1);
        int visibility =  savedInstanceState.getInt(VISIBILITY1, 0);
        nextBtnLost.setVisibility(visibility);
        if (image1!=null) {
            petImageViewLost.setBackgroundResource(0);
            petImageViewLost.setImageBitmap(image1);
            petImageViewLost.setAlpha(1.0f);
        }
    }

    public void gpsBtnClickLost(View view) {
        /*
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        }
                if ((globalLocation == null)) {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener)}
        else {
            globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if ((globalLocation == null)) {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);}
            else {
                locationTextViewLost.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
                locationTextViewLost.setText(UIhelper.getAddress(globalLocation, this));
                //=======
                Intent intent1 = new Intent(NewLostAd.this, MapsActivity.class);
                intent1.putExtra("coordnates", globalLocation);
                startActivity(intent1);
                //=======
            }
        }
        */
        if ((globalLocation == null)) {
            getNowLocationN();
        }
        else {

            float lat = (float) globalLocation.getLatitude();
            float lon = (float) globalLocation.getLongitude();

            SharedPreferences.Editor editor = getSharedPreferences("MyLocation", MODE_PRIVATE).edit();
            editor.putFloat("latitude", lat);
            editor.putFloat("longitude", lon);
            editor.commit();

            locationTextViewLost.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
            locationTextViewLost.setText(UIhelper.getAddress(globalLocation, this));
            //=======
            Intent intent1 = new Intent(NewLostAd.this, MapsActivity.class);
            //intent1.putExtra("coordnates", globalLocation);
            startActivity(intent1);
            //=======
        }
    }

    public void dateBtnClickLost(View view) {
        dateTextViewLost.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
        dateTextViewLost.setText(UIhelper.getDateTime());
    }

    public void photoBtnClickLost(View view) {UIhelper.selectImage(NewLostAd.this, NewLostAd.this);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = UIhelper.CameraCode(data);
                petImageViewLost.setBackgroundResource(0);
                petImageViewLost.setImageBitmap(thumbnail);
                petImageViewLost.setAlpha(1.0f);
                image1 = thumbnail;
            }
            else if (requestCode == SELECT_FILE) {
                Bitmap bm = UIhelper.SelectPhoto(data, this);
                petImageViewLost.setBackgroundResource(0);
                petImageViewLost.setImageBitmap(bm);
                petImageViewLost.setAlpha(1.0f);
                image1 = bm;
            }
        }
    }

    public void dateEditClickLost(View view) {
        UIhelper.timeEdit(getSupportFragmentManager());
        UIhelper.dateEdit(getSupportFragmentManager());
    }

    public void nextLostAdBtnClick(View view){
        if (isAdValidation()) {
            nextBtnLost.setVisibility(View.INVISIBLE);

            /*
            SaveHelper.CreateAd(new AdCreatedListener() {
                @Override
                public void onAddCreationFinished(Ad saveNewAd) {
                    Intent intent = new Intent(NewLostAd.this, UserDetailsActivity.class);
                    if (saveNewAd != null) {intent.putExtra("newAd", saveNewAd);}
                    intent.putExtra("adFlag", 1);       //lost
                    startActivity(intent);
                }
            } ,image1, 1);
            */

            CreateAdLost(new AdCreatedListener() {
                @Override
                public void onAddCreationFinished(Ad saveNewAd) {
                    Intent intent = new Intent(NewLostAd.this, UserDetailsActivity.class);
                    if (saveNewAd != null) {intent.putExtra("newAd", saveNewAd);}
                    intent.putExtra("adFlag", 1);       //lost
                    startActivity(intent);
                }
            });
        }
    }

    public Ad adToSave(String uniqueID,String url) {
        Ad newAdToSave = new Ad();
        newAdToSave.id = uniqueID;

        if (sexF.getText() != null){ newAdToSave.setSex("Female"); }
        else if (sexM.getText() != null){ newAdToSave.setSex("Male"); }
        else { newAdToSave.setSex(""); }

        if (collarN.getText() != null){ newAdToSave.setYesno("NO"); }
        else if (collarY.getText() != null){ newAdToSave.setYesno("YES"); }
        else { newAdToSave.setYesno(""); }

        newAdToSave.creationDate1 = dateTextViewLost.getText().toString();
        newAdToSave.setArea(locationTextViewLost.getText().toString());
        newAdToSave.setDescription(descTextViewLost.getText().toString());
        if (url == null) {
            newAdToSave.setImageURL("https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0");
        }
        else { newAdToSave.setImageURL(url); }

        return newAdToSave;
    }

    private boolean isAdValidation() {
        Boolean isOk = true;

        if (!UIhelper.textCheck(dateTextViewLost.getText().toString())) {
            dateTextViewLost.setHint("please enter a Date");
            dateTextViewLost.setHintTextColor(Color.RED);
            isOk = false;
        }

        if (!UIhelper.textCheck(locationTextViewLost.getText().toString())) {
            locationTextViewLost.setHint("please enter at Address");
            locationTextViewLost.setHintTextColor(Color.RED);
            isOk = false;
        }

        if (!UIhelper.textCheck(descTextViewLost.getText().toString())) {
            descTextViewLost.setHint("please enter a Description");
            descTextViewLost.setHintTextColor(Color.RED);
            isOk = false;
        }

        return isOk;
    }

    private void CreateAdLost(final AdCreatedListener listener) {
        if (image1==null){
            String  uniqueID = UUID.randomUUID().toString();
            String url = "https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0";
            Ad newAd = adToSave(uniqueID, url);

            listener.onAddCreationFinished(newAd);
            nextBtnLost.setVisibility(View.VISIBLE);
        }

        if (image1!=null) {
            final String  uniqueID = UUID.randomUUID().toString();
            NetworkHelper.uploadImageToServer(image1, uniqueID, new SaveAddLister() {
                @Override
                public void onUrlUploaded(String url) {
                    Ad newAd = adToSave(uniqueID, url);
                    listener.onAddCreationFinished(newAd);
                    nextBtnLost.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    public void getNowLocationN() {
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        }

        globalLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if ((globalLocation == null)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            globalLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if ((globalLocation == null)) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getNowLocationN();

        ImageButton gpsBtn = (ImageButton) findViewById(R.id.locationImageButtonLostAd);
        gpsBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
       ///////////////////
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
        //////////////////
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        globalLocation = location;
        if (globalLocation==null){getNowLocationN();}

        float lat = (float) globalLocation.getLatitude();
        float lon = (float) globalLocation.getLongitude();

        SharedPreferences.Editor editor = getSharedPreferences("MyLocation", MODE_PRIVATE).edit();
        editor.putFloat("latitude", lat);
        editor.putFloat("longitude", lon);
        editor.commit();

        //ImageButton gpsBtn = (ImageButton) findViewById(R.id.locationImageButtonLostAd);
        //gpsBtn.setVisibility(View.VISIBLE);
    }
    /*
    public void nextLostAdBtnClick(View view) {
        String  uniqueID = UUID.randomUUID().toString();
        if(image1 != null) {UploadPic(uniqueID );}
        else {SaveAdAndMoveToNextPage(uniqueID, null);}

       //-------/* Ad saveNewAd = saveAd();
        ////////////===========================================================================//
        ////////////Intent intent = new Intent(NewLostAd.this, RegistrationPopup.class);
        ///////////intent.putExtra("newAd", saveNewAd);
        //////////===========================================================================//

        ///
        //Intent intent = new Intent(NewLostAd.this, UserDetailsActivity.class);
        //if (saveNewAd!=null) {
        //    intent.putExtra("newAd", saveNewAd);
       // }
        ///

       // intent.putExtra("adFlag", 1);       //lost
       // startActivity(intent);
        //--------
    }

    public void SaveAdAndMoveToNextPage(String uniqueID, String imageURI) {
        Ad saveNewAd = saveAd(uniqueID,imageURI);
        Intent intent = new Intent(NewLostAd.this, UserDetailsActivity.class);
        if (saveNewAd!=null) {
            intent.putExtra("newAd", saveNewAd);
            intent.putExtra("adFlag", 1);       //lost
            startActivity(intent);
        }
    }





    private void UploadPic(String uniqueID) {
        NetworkHelper.uploadImageToServerLost(image1,uniqueID,this);
    }

    public Ad saveAd(String uniqueID, String imageURI) {
        //private Ad saveAd() {
        // String  uniqueID = UUID.randomUUID().toString();
        Ad saveAd = new Ad();
        saveAd.id = uniqueID;

        if (sexF.getText() != null){
            saveAd.setSex("Female");
        }
        else if (sexM.getText() != null){
            saveAd.setSex("Male");
        }
        else {
            saveAd.setSex("");
        }

        if (collarN.getText() != null){
            saveAd.setYesno("NO");
        }
        else if (collarY.getText() != null){
            saveAd.setYesno("YES");
        }
        else {
            saveAd.setYesno("");
        }



        if (UIhelper.textCheck(dateTextViewLost.getText().toString())) {
            saveAd.creationDate1 = dateTextViewLost.getText().toString();
        } else {
            dateTextViewLost.setHint("please enter a Date");
            dateTextViewLost.setHintTextColor(Color.RED);
            return null;
        }

        if (UIhelper.textCheck(locationTextViewLost.getText().toString())) {
            saveAd.setArea(locationTextViewLost.getText().toString());
        } else {
            locationTextViewLost.setHint("please enter at Address");
            locationTextViewLost.setHintTextColor(Color.RED);
            return null;
        }

        if (UIhelper.textCheck(descTextViewLost.getText().toString())) {
            saveAd.setDescription(descTextViewLost.getText().toString());
        } else {
            descTextViewLost.setHint("please enter a Description");
            descTextViewLost.setHintTextColor(Color.RED);
            return null;
        }
        //saveAd.creationDate1 = dateTextViewLost.getText().toString();
        //saveAd.setArea(locationTextViewLost.getText().toString());
        //saveAd.setDescription(descTextViewLost.getText().toString());
        //if (image1!=null) {
        //    NetworkHelper.uploadImageToServer(image1,saveAd,uniqueID,this);
        // }
        //if (saveAd.getImageURL() == null) {
        if (imageURI == null) {saveAd.setImageURL("https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0");}
        else {saveAd.setImageURL(imageURI);}
        return saveAd;
    }
    */
}
