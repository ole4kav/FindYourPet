package com.olgav.android.findyourpet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class LostActivity extends AppCompatActivity
{
    AdAdapter lostCustomAdapter;
    ListView myListView;
    ArrayList<Ad> adList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        myListView = (ListView) findViewById(R.id.listViewLost);

        startDownload();

        if (myListView!=null) {
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Ad details = (Ad) lostCustomAdapter.getItem(position);
                    Intent intent = new Intent(LostActivity.this, LostAdPropertyActivity.class);
                    intent.putExtra("details", details);
                    intent.putExtra("user", details.adOwner);
                    startActivity(intent);
                }
            });
        }
    }
    private void startDownload() {
        new AsyncTask<Void, Void, ArrayList<Ad>>(){
            @Override
            protected ArrayList<Ad> doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("https://findyourpet-9ca26.firebaseio.com/ads/found.json");
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Reader reader = new InputStreamReader(inputStream, "UTF-8");
                    Gson gson = new Gson();
                    Map<String, Ad> myAdsL = gson.fromJson(reader, new TypeToken<Map<String, Ad>>(){}.getType());
                    ArrayList<Ad> myAdNewL = new ArrayList<>();
                    for (Map.Entry<String, Ad> entry : myAdsL.entrySet()) {
                        myAdNewL.add(entry.getValue());
                    }
                    return myAdNewL;
                }
                catch (MalformedURLException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}
                catch (Exception e){e.printStackTrace();}
                finally {connection.disconnect();}

                //return null;
                return adList;
            }

            @Override
            protected void onPostExecute(ArrayList<Ad> ads) {
                Collections.reverse(ads);
                adList = ads;
                if (adList.size() != 0) {
                    lostCustomAdapter = new AdAdapter(adList, LostActivity.this);
                    myListView.setAdapter(lostCustomAdapter);
                }
                else {
                    DialogFragment newFragmentInternet = new NoInternetFragment();
                    getSupportFragmentManager().beginTransaction().add(newFragmentInternet, "internet").commitAllowingStateLoss();
                }

            }
        }.execute();
    }

    public void searchLostBtnClick(View view) {
    }

    public void newLostBtnClick(View view) {
        Intent intent = new Intent(LostActivity.this, NewLostAd.class);
        startActivity(intent);
    }


    public void rowImageViewBtn(View view) {
        int position = myListView.getPositionForView(view) ;

        Ad thisPicture = (Ad)lostCustomAdapter.getItem(position);
        Intent intent = new Intent(LostActivity.this, PicturePopup.class);
        if (thisPicture.getImageURL()!=""){
            intent.putExtra("imageUrl", thisPicture.getImageURL());
        }
        startActivity(intent);
    }
}
