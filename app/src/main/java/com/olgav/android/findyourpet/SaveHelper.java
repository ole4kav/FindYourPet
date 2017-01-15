package com.olgav.android.findyourpet;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by olga on 12/29/16.
 */

public class SaveHelper {

    public static void CreateAd(final AdCreatedListener listener, Bitmap image, Integer activityType) {


        if (image==null){
            String  uniqueID = UUID.randomUUID().toString();
            String url = "https://firebasestorage.googleapis.com/v0/b/findyourpet-9ca26.appspot.com/o/dog_profile.jpg?alt=media&token=29ee6a98-00c3-4cdd-b569-c6f47369a2b0";
            //Ad newAd = adToSave(uniqueID, url);

            Ad newAdLost = null;
            if (activityType==1) {
                newAdLost = new NewLostAd().adToSave(uniqueID, url);
            }
            if (activityType==2) {
                newAdLost = new NewLostAd().adToSave(uniqueID, url);
            }

            if (newAdLost!=null) {
                listener.onAddCreationFinished(newAdLost);
            }
            //saveBtn.setVisibility(View.VISIBLE);
        }


        if (image!=null) {
            final String  uniqueID = UUID.randomUUID().toString();
            NetworkHelper.uploadImageToServer(image, uniqueID, new SaveAddLister() {
                @Override
                public void onUrlUploaded(String url) {
                    Ad newAdLost = new NewLostAd().adToSave(uniqueID, url);
                    listener.onAddCreationFinished(newAdLost);
                    //saveBtn.setVisibility(View.VISIBLE);
                }
            });
        }

    }


}
