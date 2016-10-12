/*
HW5 - Mobile Application Development
Chase Schelthoff and Phillip Hunter
 */

package com.example.hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class FavoriteAdapter extends ArrayAdapter<FavoriteData>
{
    private Context context;
    ArrayList<FavoriteData> favorites;
    private int resource;

    public FavoriteAdapter(Context context, ArrayList<FavoriteData> favorites, int resource)
    {
        super(context, resource, favorites);
        this.context = context;
        this.favorites = favorites;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        FavoriteData currFavorite = favorites.get(position);

        TextView lblCityState = (TextView)convertView.findViewById(R.id.lblCityState);
        TextView lblTemperature = (TextView)convertView.findViewById(R.id.lblTemperature);
        TextView lblUpdatedDate = (TextView)convertView.findViewById(R.id.lblUpdatedDate);

        lblCityState.setText(currFavorite.getCity() + ", " + currFavorite.getState());
        lblTemperature.setText(currFavorite.getTempStored() + "°F");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        lblUpdatedDate.setText(convertView.getContext().getString(R.string.lbl_updated_on) +  dateFormat.format(currFavorite.getDateStored()));

        return convertView;
    }
}
