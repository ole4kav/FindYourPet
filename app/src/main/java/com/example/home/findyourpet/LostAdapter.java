package com.example.home.findyourpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HOME on 28/04/2016.
 */
public class LostAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    Context context;
    ArrayList<String> list;

    public LostAdapter(ArrayList<String> lists, Context context){
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
        TextView descTextView = (TextView) row.findViewById(R.id.rowDataDescTextView);



        return row;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
