package com.olgav.android.findyourpet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicturePopup extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_picture);

        DisplayMetrics gm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(gm);

        int width = gm.widthPixels;
        int height = gm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

        ImageView imageView = (ImageView) findViewById(R.id.lostBigImageView);

        Intent intent = getIntent();

        if(intent.hasExtra("imageUrl")) {
            String imageUrl = intent.getStringExtra("imageUrl");
            Picasso.with(this).load(imageUrl).into(imageView);
        }
        else {
            imageView.setImageResource(R.drawable.dog_profile);
        }
    }
}
