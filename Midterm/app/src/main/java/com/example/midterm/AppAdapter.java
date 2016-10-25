/*
Chase Schelthoff
 */

package com.example.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class AppAdapter extends ArrayAdapter<App>
{
    private Context context;
    private ArrayList<App> apps;
    private int resource;

    public AppAdapter(Context context, ArrayList<App> apps, int resource)
    {
        super(context, resource, apps);

        this.context = context;
        this.apps = apps;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        App currApp = apps.get(position);

        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        ImageView imagePrice = (ImageView) convertView.findViewById(R.id.imageViewPrice);

        TextView textName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView textPrice = (TextView) convertView.findViewById(R.id.textViewPrice);

        Picasso.with(context).load(currApp.getThumbnailURI()).into(imageIcon);
        Picasso.with(context).load(currApp.getPriceResourceId()).into(imagePrice);

        textName.setText(currApp.getName());
        textPrice.setText(new StringBuilder()
                .append(context.getString(R.string.lbl_price))
                .append(" ")
                .append(currApp.getCurrency())
                .append(" ")
                .append(currApp.getPrice()));

        return convertView;
    }
}
