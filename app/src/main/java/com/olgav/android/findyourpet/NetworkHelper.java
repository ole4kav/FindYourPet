package com.olgav.android.findyourpet;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HOME on 31/05/2016.
 */
public class NetworkHelper
{
    public static void postToServer(final String s, final URL url) {
            new Thread() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        //URL url = new URL("https://findyourpet-9ca26.firebaseio.com/ads.json");
                        HttpURLConnection.setFollowRedirects(false);    //-------
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        //connection.setRequestProperty("Content-Type", "application/json");

                        connection.setConnectTimeout(5000); //set timeout to 5 seconds
                        connection.setReadTimeout(5000);

                        OutputStream outputStream = connection.getOutputStream();   //write
                        outputStream.write(s.getBytes("UTF-8"));
                        connection.connect();

                        outputStream.close();
                        InputStream inputStream = new BufferedInputStream(connection.getInputStream());  //read

                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            inputStream = connection.getInputStream();
                        }
                        else { inputStream = connection.getErrorStream(); }
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                            StringBuilder stringBuilder = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) { stringBuilder.append(line + "\n"); }

                            inputStream.close();
                            reader.close();

                            String response = stringBuilder.toString();
                        }
                        catch (Exception e) { e.printStackTrace(); }
                        //return response ;
                    }

                    catch (java.net.SocketTimeoutException e) {e.printStackTrace();}
                    catch (java.io.IOException e) {e.printStackTrace();}

                    finally {
                        if(connection!=null) { connection.disconnect();}
                    }
                }
            }.start();
    }

    /*
    public static void uploadImageToServerLost(Bitmap image, final String uniqueID, final NewLostAd ad){
        UploadTask uploadTask = CreateImage(image,uniqueID);
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads          //////////////////////////////////////////
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ad.SaveAdAndMoveToNextPage(uniqueID,downloadUrl.toString());
            }
        });
    }
    */


    public static UploadTask CreateImage(Bitmap image, final String uniqueID) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRefPar = storage.getReferenceFromUrl("gs://findyourpet-9ca26.appspot.com"); // Create a storage reference from our app
        StorageReference storageRefCh = storageRefPar.child(uniqueID + ".jpg"); // Create a reference to "___.jpg"
        StorageReference storageRefChImageRef = storageRefPar.child("images/" + uniqueID + ".jpg"); // Create a reference to 'images/___.jpg'

        // While the file names are the same, the references point to different files
        storageRefCh.getName().equals(storageRefChImageRef.getName());    // true
        storageRefCh.getPath().equals(storageRefChImageRef.getPath());    // false

        UploadTask uploadTask = storageRefCh.putBytes(data);

        return uploadTask;
    }


   public static void uploadImageToServer(Bitmap image, String uniqueID, final SaveAddLister listener){
       UploadTask uploadTask = CreateImage(image, uniqueID);
       uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
               Uri downloadUrl = taskSnapshot.getDownloadUrl();
               listener.onUrlUploaded(downloadUrl.toString());
               UrlSaverHelper.getInstance().saveWorkingUrl(downloadUrl.toString());
           }
       });
       uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads          //////////////////////////////////////////

            }
        });
    }
}
