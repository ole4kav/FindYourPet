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
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.model.people.Person;

import java.util.UUID;

public class NewFoundAd extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    static final String DATE = "dateText";
    static final String PHOTO = "imageView";
    static final String LOCATION = "locationText";
    static final String DESCRIPTION = "descriptionText";
    static final String VISIBILITY ="visibility";

    Bitmap image;

    TextView locationTextView;
    TextView dateTextView;
    TextView descTextView;
    ImageView petImageView;
    RadioButton sexF;
    RadioButton sexM;
    RadioButton tookY;
    RadioButton tookN;
    Button saveBtn;


    Location globalLocation = null;
    private LocationManager locationManager;
    //private LocationListener locationListener;

    private GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_found);

        /*View backgroundimage = findViewById(R.id.background);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(30);*/

        petImageView = (ImageView) findViewById(R.id.dogImageView);
        locationTextView = (TextView) findViewById(R.id.locationEditText);
        dateTextView = (TextView) findViewById(R.id.dateEditText);
        descTextView = (TextView) findViewById(R.id.editText);
        sexM = (RadioButton) findViewById(R.id.sexMRadioBtnF);
        sexF = (RadioButton) findViewById(R.id.sexFRadioBtnF);
        tookN = (RadioButton) findViewById(R.id.tookNRadioBtnF);
        tookY = (RadioButton) findViewById(R.id.tookYRadioBtnF);

        saveBtn = (Button) findViewById(R.id.saveButton);

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

                SharedPreferences.Editor editor = getSharedPreferences("MyLocation", Context.MODE_PRIVATE).edit();
                editor.putFloat("latitude", lat);
                editor.putFloat("longitude", lon);
                editor.commit();

                ImageButton gps = (ImageButton) findViewById(R.id.locationImageButton);
                gps.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                globalLocation = new Location(LocationManager.GPS_PROVIDER);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        getNowLocation();
    }


    public void getNowLocation() {
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        }
        if ((globalLocation == null)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
        }
        else {
            globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if ((globalLocation == null)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
            }
        }
        */
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(DATE, dateTextView.getText().toString());     //putStringArrayList(DATE,dateTextView.getText());
        savedInstanceState.putString(LOCATION, locationTextView.getText().toString());     //putSerializable(LOCATION, thisDirectory);
        savedInstanceState.putString(DESCRIPTION, descTextView.getText().toString());     //putBoolean(DESCRIPTION, customAdapter.checkBoxVisibility);
        savedInstanceState.putParcelable(PHOTO, image);     //putString(DESCRIPTION, descTextView.getText().toString());;
        savedInstanceState.putInt(VISIBILITY,saveBtn.getVisibility());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        dateTextView.setText(savedInstanceState.getString(DATE));
        locationTextView.setText(savedInstanceState.getString(LOCATION));
        descTextView.setText(savedInstanceState.getString(DESCRIPTION));
        image = savedInstanceState.getParcelable(PHOTO);

        int visibility =  savedInstanceState.getInt(VISIBILITY, 0);
        saveBtn.setVisibility(visibility);

        if (image != null) {
            petImageView.setBackgroundResource(0);
            petImageView.setImageBitmap(image);
            petImageView.setAlpha(1.0f);
        }
    }

    public void gpsBtnClick(View view) {
        /*
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        }
        if ((globalLocation == null)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
        } else {
            globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if ((globalLocation == null)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
            } else {
                Intent intent1 = new Intent(NewFoundAd.this, MapsActivity.class);
                intent1.putExtra("coordnates", globalLocation);
                startActivity(intent1);
                locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
                locationTextView.setText(UIhelper.getAddress(globalLocation, this));
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

            Intent intent1 = new Intent(NewFoundAd.this, MapsActivity.class);
            startActivity(intent1);

            locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
            locationTextView.setText(UIhelper.getAddress(globalLocation, context));
        }
    }

    public void dateBtnClick(View view) {
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
        dateTextView.setText(UIhelper.getDateTime());
    }

    public void photoBtnClick(View view) {
        UIhelper.selectImage(NewFoundAd.this, NewFoundAd.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = UIhelper.CameraCode(data);
                petImageView.setBackgroundResource(0);
                petImageView.setImageBitmap(thumbnail);
                petImageView.setAlpha(1.0f);
                image = thumbnail;
            } else if (requestCode == SELECT_FILE) {
                Bitmap bm = UIhelper.SelectPhoto(data, this);
                petImageView.setBackgroundResource(0);
                petImageView.setImageBitmap(bm);
                petImageView.setAlpha(1.0f);
                image = bm;
            }
        }
    }

    public void dateEditClick(View view) {
        UIhelper.timeEdit(getSupportFragmentManager());
        UIhelper.dateEdit(getSupportFragmentManager());
    }


    public void nextBtnClick(View view) {


        if (isAdValidation()) {

            saveBtn.setVisibility(View.INVISIBLE);

            CreateAd(new AdCreatedListener() {
                @Override
                public void onAddCreationFinished(Ad saveNewAd) {
                    Intent intent = new Intent(NewFoundAd.this, UserDetailsActivity.class);
                    if (saveNewAd != null) {
                        intent.putExtra("newAd", saveNewAd);
                    }
                    ///

                    intent.putExtra("adFlag", 2);       //found
                    startActivity(intent);
                }
            });
        }
        //===========================================================================//
        //Intent intent = new Intent(NewFoundAd.this, RegistrationPopup.class);
        //intent.putExtra("newAd",saveNewAd);
        //===========================================================================//

        ///

    }

    private void CreateAd(final AdCreatedListener listener) {


        if (image==null){
            String  uniqueID = UUID.randomUUID().toString();
            String url = "https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0";
            Ad newAd = adToSave(uniqueID, url);

            listener.onAddCreationFinished(newAd);
            saveBtn.setVisibility(View.VISIBLE);
        }


        if (image!=null) {
            final String  uniqueID = UUID.randomUUID().toString();
            NetworkHelper.uploadImageToServer(image, uniqueID, new SaveAddLister() {
                @Override
                public void onUrlUploaded(String url) {
                    Ad newAd = adToSave(uniqueID, url);

                    //Ad saveAd = new Ad();
                    //saveAd.id = uniqueID;
                    //saveAd.creationDate1 = dateTextView.getText().toString();
                    //saveAd.setArea(locationTextView.getText().toString());
                    //saveAd.setDescription(descTextView.getText().toString());
                    //saveAd.setImageURL(url);

                    listener.onAddCreationFinished(newAd);

                    saveBtn.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    private Ad adToSave(String uniqueID,String url) {
        Ad newAdToSave = new Ad();
        newAdToSave.id = uniqueID;

        if (sexF.getText() != null){
            newAdToSave.setSex("Female");
        }
        else if (sexM.getText() != null){
            newAdToSave.setSex("Male");
        }
        else {
            newAdToSave.setSex("");
        }

        if (tookN.getText() != null){
            newAdToSave.setYesno("NO");
        }
        else if (tookY.getText() != null){
            newAdToSave.setYesno("YES");
        }
        else {
            newAdToSave.setYesno("");
        }

        newAdToSave.creationDate1 = dateTextView.getText().toString();
        newAdToSave.setArea(locationTextView.getText().toString());
        newAdToSave.setDescription(descTextView.getText().toString());

        //saveAd.setImageURL(url);
        if (url == null) {
            newAdToSave.setImageURL("https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0");
        }
        else {
            newAdToSave.setImageURL(url);
        }

        return newAdToSave;
    }


    private boolean isAdValidation() {
        Boolean isOk = true;

        if (!UIhelper.textCheck(dateTextView.getText().toString())) {
            dateTextView.setHint("please enter a Date");
            dateTextView.setHintTextColor(Color.RED);
            isOk = false;
        }

        if (!UIhelper.textCheck(locationTextView.getText().toString())) {
            locationTextView.setHint("please enter at Address");
            locationTextView.setHintTextColor(Color.RED);
            isOk = false;
        }

        if (!UIhelper.textCheck(descTextView.getText().toString())) {
            descTextView.setHint("please enter a Description");
            descTextView.setHintTextColor(Color.RED);
            isOk = false;
        }

        return isOk;
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

        ImageButton gps = (ImageButton) findViewById(R.id.locationImageButton);
        gps.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {

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
}

