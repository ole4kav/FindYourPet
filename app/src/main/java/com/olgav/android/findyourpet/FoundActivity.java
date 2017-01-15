package com.olgav.android.findyourpet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FoundActivity extends AppCompatActivity
{
    AdAdapter foundCustomAdapter;
    ListView myListViewF;
    ArrayList<Ad> adListF = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        myListViewF = (ListView) findViewById(R.id.listViewFound);

        startDownloadFound();

        if (myListViewF!=null) {
            myListViewF.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Ad details = (Ad) foundCustomAdapter.getItem(position);
                    Intent intent = new Intent(FoundActivity.this, FoundAdPropertyActivity.class);
                    intent.putExtra("detailsf", details);
                    intent.putExtra("userf", details.adOwner);
                    startActivity(intent);
                }
            });
        }
    }
    private void startDownloadFound() {
        new AsyncTask<Void, Void, ArrayList<Ad>>(){
            @Override
            protected ArrayList<Ad> doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("https://findyourpet-9ca26.firebaseio.com/ads/lost.json");
                    connection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    Reader reader = new InputStreamReader(inputStream, "UTF-8");

                    Gson gson = new Gson();
                    Map<String, Ad> myAds = gson.fromJson(reader, new TypeToken<Map<String, Ad>>(){}.getType());
                    ArrayList<Ad> myAdNew = new ArrayList<>();
                    for (Map.Entry<String, Ad> entry : myAds.entrySet()) {
                        myAdNew.add(entry.getValue());
                    }
                    return myAdNew;
                }

                catch (MalformedURLException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}
                catch (Exception e){e.printStackTrace();}
                finally {connection.disconnect();}

                //return null;
                return adListF;
            }

            @Override
            protected void onPostExecute(ArrayList<Ad> ads) {
                Collections.reverse(ads);
                adListF = ads;
                if (adListF.size() != 0) {
                    foundCustomAdapter = new AdAdapter(adListF, FoundActivity.this);
                    myListViewF.setAdapter(foundCustomAdapter);
                }
                else {
                    DialogFragment newFragmentInternet = new NoInternetFragment();
                    getSupportFragmentManager().beginTransaction().add(newFragmentInternet, "internet").commitAllowingStateLoss();
                }
            }
        }.execute();
    }

    public void searchFoundBtnClick(View view) {}

    public void newFoundBtnClick(View view) {
        Intent intent = new Intent(FoundActivity.this, NewFoundAd.class);
        startActivity(intent);
    }

    public void rowImageViewBtn(View view) {
        int position = myListViewF.getPositionForView(view) ;
        Ad thisPicture = (Ad)foundCustomAdapter.getItem(position);
        Intent intent = new Intent(FoundActivity.this, PicturePopup.class);
        if (thisPicture.getImageURL()!=""){intent.putExtra("imageUrl", thisPicture.getImageURL());}
        startActivity(intent);
    }
}

