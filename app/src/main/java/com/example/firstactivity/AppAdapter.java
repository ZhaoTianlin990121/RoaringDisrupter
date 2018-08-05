package com.example.firstactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends ArrayAdapter<AppInfo>{
    private int resourceId;
    public AppAdapter(Context context, int textViewResourceId, List<AppInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        AppInfo appInfo = getItem(position);
        View view;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        } else {
            view = convertView;
        }
        ImageView appImage = (ImageView) view.findViewById(R.id.app_image);
        TextView appName = (TextView) view.findViewById(R.id.app_name);
        appImage.setImageDrawable(appInfo.getDrawable());
        appName.setText(appInfo.getAppName());
        return view;
    }
}
