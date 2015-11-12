package com.example.todd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todd.emojis.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by john on 11/11/15.
 * BaseAdapter to handle hashmap and inflate the listview
 */
public class EmojisAdapter extends BaseAdapter {

    //set up objects and variables
    private HashMap<String, String> data = new HashMap<>();
    private String[] keys;
    private Activity activity;
    private ViewHolder viewHolder;


    //I like to use a viewholder to organize components
    private static class ViewHolder {
        private TextView name;
        private ImageView icon;
    }

    // constructor sets up the hashmap to retrieve items by position
    public EmojisAdapter(Activity activity, HashMap<String, String> data){
        this.data  = data;
        keys = data.keySet().toArray(new String[data.size()]);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(keys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    //getView to inflate layout and set test and icon
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.emojis_name);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.emojis_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(keys[pos]);
        Picasso.with(activity.getApplicationContext())
                .load(getItem(pos).toString())
                .placeholder(R.mipmap.ic_smiley)
                .into(viewHolder.icon);

        return convertView;
    }

}
