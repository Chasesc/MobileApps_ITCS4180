/*
HW5 - Mobile Application Development
Chase Schelthoff and Phillip Hunter
 */

package com.example.hw5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Computer on 10/11/2016.
 */

public class WeatherAdapter extends ArrayAdapter<Weather>
{
    private Context context;
    private ArrayList<Weather> weather;
    private int resource;

    public WeatherAdapter(Context context, ArrayList<Weather> weather, int resource)
    {
        super(context, resource, weather);
        this.context = context;
        this.weather = weather;
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

        Weather currWeather = weather.get(position);

        ImageView imageViewCondition = (ImageView) convertView.findViewById(R.id.imageViewCondition);
        TextView textViewTime        = (TextView)  convertView.findViewById(R.id.textViewTime);
        TextView textViewCondition   = (TextView)  convertView.findViewById(R.id.textViewCondition);
        TextView textViewTemp        = (TextView)  convertView.findViewById(R.id.textViewTemp);

        new LoadImage(imageViewCondition).execute(currWeather.getIconURI());
        textViewTime.setText(currWeather.getHour());
        textViewCondition.setText(currWeather.getWeatherCondition());
        textViewTemp.setText(currWeather.getTemperature() + "°F");

        return convertView;
    }
}
