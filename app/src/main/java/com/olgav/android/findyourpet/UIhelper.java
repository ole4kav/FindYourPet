package com.olgav.android.findyourpet;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

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

/**
 * Created by HOME on 23/04/2016.
 */
public class UIhelper {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    static Location globalLocation = null;

    public static String getAddress(Location location, Context context) {
        Geocoder geocoder= new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        String country="";String city="";String address="";

        globalLocation = location;

        Double latitude = globalLocation.getLatitude();
        Double longitude = globalLocation.getLongitude();

        try {
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

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void selectImage(final Context context1, final Activity activity) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);   //(NewFoundAd.this);
        builder.setTitle("Add Pet Photo");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(context1, Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                        return;
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(intent, REQUEST_CAMERA);
                    //startActivityForResult(Activity activity, Intent intent, int requestCode,
                }
                else if (items[item].equals("Choose from Library")) {
                    if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(context1, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static Bitmap CameraCode(Intent data){
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

        return thumbnail;
    }

    public static Bitmap SelectPhoto(Intent data, Context context){
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, selectedImageUri, projection, null, null, null);
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
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) {scale *= 2;}
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        return bm;
    }

    public static void dateEdit(FragmentManager manager){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(manager, "datePicker");
    }

    public static void timeEdit(FragmentManager manager){
        DialogFragment newFragmentT = new TimePickerFragment();
        newFragmentT.show(manager, "timePicker");
    }

    public static Boolean textCheck(String text) {
        if (text.length() == 0) {return false;}
        return true;
    }





}
