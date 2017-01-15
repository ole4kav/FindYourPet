package com.olgav.android.findyourpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/* Created by HOME on 28/04/2016. */

public class AdAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    Context context;
    ArrayList<Ad> list;

    public AdAdapter(ArrayList<Ad> lists, Context context){
        this.list = lists;
        this.context = context;
    }

    @Override
    public int getCount() {return list.size();}
    @Override
    public Object getItem(int position) {return list.get(position);}
    @Override
    public long getItemId(int position) {return 0;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null){
            row = LayoutInflater.from(context).inflate(R.layout.row_lost, parent, false);
        }
        else {
            row = convertView;
        }
        ImageView imageView = (ImageView) row.findViewById(R.id.rowImageView);
        TextView dateTextView = (TextView) row.findViewById(R.id.rowDataDateTextView);
        TextView areaTextView = (TextView) row.findViewById(R.id.rowDataAreaTextView);
        TextView sexRadioBtn = (TextView) row.findViewById(R.id.rowDataSexTextView);

        //TextView descTextView = (TextView) row.findViewById(Rסז.id.rowDataDescTextView);
        final Ad current = (Ad) getItem(position);
        //DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        //String reportDate = df.format(current.getCreationDate());
        String imageUrl = current.getImageURL();
        if (imageUrl!=""){
            Picasso.with(context).load(imageUrl).centerInside().resize(500,500).into(imageView);   //resize(500, 500).centerCrop().into(imageView);
        }
        else {
            imageView.setImageResource(R.drawable.dog_profile);
        }
        //dateTextView.setText(reportDate);
        dateTextView.setText(current.creationDate1);
        areaTextView.setText(current.getArea());
        sexRadioBtn.setText(current.getSex());
        //descTextView.setText(current.getDescription());

        return row;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }
}