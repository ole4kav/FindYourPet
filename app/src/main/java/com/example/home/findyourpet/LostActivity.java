package com.example.home.findyourpet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class LostActivity extends AppCompatActivity
{
    LostAdapter lostCustomAdapter;
    ListView myListView;
    ArrayList<String> petList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);





        lostCustomAdapter = new LostAdapter(petList, this);
        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(lostCustomAdapter);

    }

    public void searchBtnClick(View view) {
    }

    public void newLostBtnClick(View view) {
    }
}
