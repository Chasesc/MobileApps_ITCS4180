/*
HW5 - Mobile Application Development
Chase Schelthoff and Phillip Hunter
 */

package com.example.hw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherDetailsActivity extends AppCompatActivity
{
    private ImageView imageViewIcon;
    private TextView textTemp, textCondition, textMin, textMax, textFeelsLike, textHumidity;
    private TextView textLocation, textDewpoint, textPressure, textClouds, textWinds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        setAllViews();

        Intent intent = getIntent();

        if(intent == null)
            Log.d("weatherdetailsactivity", "intent is null :'(");

        Bundle bundle = intent.getExtras();
        Weather weather = (Weather) bundle.getSerializable(CityWeatherActivity.WEATHER_KEY);
        int maxTemp = bundle.getInt(CityWeatherActivity.MAX_KEY);
        int minTemp = bundle.getInt(CityWeatherActivity.MIN_KEY);

        new LoadImage(imageViewIcon).execute(weather.getIconURI());
        textLocation.setText(getString(R.string.lbl_current_location) + ": " + weather.getCityName()
                                + ", " + weather.getStateInitials());
        textTemp.setText(weather.getTemperature() + "°F");
        textCondition.setText(weather.getWeatherCondition());

        textMax.setText(maxTemp + " " + getString(R.string.lbl_fahrenheit));
        textMin.setText(minTemp + " " + getString(R.string.lbl_fahrenheit));

        textFeelsLike.setText(weather.getTempFeelsLike() + " " + getString(R.string.lbl_fahrenheit));
        textHumidity.setText(weather.getHumidity() + "%");
        textDewpoint.setText(weather.getDewpoint() + " " + getString(R.string.lbl_fahrenheit));
        textPressure.setText(weather.getPressure() + " hPa");
        textClouds.setText(weather.getWeatherCondition());
        textWinds.setText(weather.getWindSpeed() + " mph, " + weather.getWindDegrees() + "° " + weather.getWindDirection());
    }

    private void setAllViews()
    {
        imageViewIcon = (ImageView) findViewById(R.id.imageViewCondition);

        textLocation = (TextView) findViewById(R.id.textViewLocation);

        textTemp = (TextView) findViewById(R.id.textViewTemp);
        textCondition = (TextView) findViewById(R.id.textViewCondition);

        textMin = (TextView) findViewById(R.id.textViewMin);
        textMax = (TextView) findViewById(R.id.textViewMax);

        textFeelsLike = (TextView) findViewById(R.id.textViewFeels);
        textHumidity = (TextView) findViewById(R.id.textViewHumidity);
        textDewpoint = (TextView) findViewById(R.id.textViewDewpoint);
        textPressure = (TextView) findViewById(R.id.textViewPressure);
        textClouds = (TextView) findViewById(R.id.textViewClouds);
        textWinds = (TextView) findViewById(R.id.textViewWinds);
    }
}
