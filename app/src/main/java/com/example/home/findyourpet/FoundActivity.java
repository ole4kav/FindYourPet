package com.example.home.findyourpet;

import android.Manifest;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoundActivity extends AppCompatActivity
{
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    Location globalLocation = null;
    TextView locationTextView;
    TextView dateTextView;
    ImageView petImageView;
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        /*View backgroundimage = findViewById(R.id.background);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(30);*/

        petImageView = (ImageView) findViewById(R.id.dogImageView);
        locationTextView = (TextView) findViewById(R.id.locationEditText);
        dateTextView = (TextView) findViewById(R.id.dateEditText);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                globalLocation = location;

                Double latitude = globalLocation.getLatitude();
                Double longitude = globalLocation.getLongitude();

                locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
                locationTextView.setText(getAddress(latitude, longitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

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
    }

    public void gpsBtnClick(View view) {
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)||
                PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
            }

        if ((globalLocation == null) ){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
        }
        else {
            globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if ((globalLocation == null) ) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
            }
            else {
                Double latitude = globalLocation.getLatitude();
                Double longitude = globalLocation.getLongitude();

                locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
                locationTextView.setText(getAddress(latitude, longitude));
            }
        }
    }

    private String getAddress(Double latitude, Double longitude) {
        Geocoder geocoder= new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String country="";String city="";String address="";

        try {
           // Log.e("test", Boolean.toString(geocoder.isPresent()));
            if (geocoder.isPresent()) {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            }
        }
        catch (Exception e){e.printStackTrace();}

        if ((addresses!=null)&&(addresses.size()>0)) {
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            country = addresses.get(0).getCountryName();
        }
        String fullAddress = address + ", " + city + ", " + country;
        return fullAddress;
    }

    public void dateBtnClick(View view) {
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textsize));
        dateTextView.setText(getDateTime());
    }

    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void photoBtnClick(View view) {
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FoundActivity.this);
        builder.setTitle("Add Pet Photo");

        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(FoundActivity.this, Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(FoundActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        return;
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[item].equals("Choose from Library")) {
                    if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(FoundActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(FoundActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                }
                catch (FileNotFoundException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}

                petImageView.setBackgroundResource(0);
                petImageView.setImageBitmap(thumbnail);
                petImageView.setAlpha(1.0f);
            }
            else if (requestCode == SELECT_FILE) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2;
                }
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                petImageView.setBackgroundResource(0);
                petImageView.setImageBitmap(bm);
                petImageView.setAlpha(1.0f);
            }
        }
    }

    public void dateEditClick(View view) {
        timeEdit();
        dateEdit();
    }

    public void dateEdit(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void timeEdit(){
        DialogFragment newFragmentT = new TimePickerFragment();
        newFragmentT.show(getSupportFragmentManager(), "timePicker");
    }

    public void nextBtnClick(View view) {
        Intent intent = new Intent(FoundActivity.this, RegistrationPopup.class);
        startActivity(intent);
    }
}


