package com.example.hw6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class CityWeatherActivity extends AppCompatActivity implements GetJSONData.IJSONActivity
{
    private static final String CITY_DEBUG_TAG = "CityWeatherActivity";
    private static final String API_KEY = "a37a50b9efa023214652951dfd2bcee1";

    private WeatherAdapter adapter;

    private RecyclerView rvBasicWeather, rvDetailedWeather;
    private ProgressDialog progressLoading;
    private TextView textDailyForecast, textForecast;


    private String cityName, countryInitials;
    private ArrayList<Weather> weather, dailyWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        setAllViews();

        Intent intent = getIntent();
        if(intent == null)
            Log.d(CITY_DEBUG_TAG, "intent is null :'(");


        Bundle bundle = intent.getExtras();

        cityName = bundle.getString(MainActivity.CITY_KEY);
        countryInitials = bundle.getString(MainActivity.COUNTRY_KEY);

        textDailyForecast.setText(getString(R.string.lbl_daily_forecast) + " " + cityName + ", " + countryInitials);
        progressLoading.show();

        Log.d(CITY_DEBUG_TAG, getJSONURI());
        new GetJSONData(this).execute("http://api.openweathermap.org/data/2.5/forecast?q=Charlotte,US&APPID=a37a50b9efa023214652951dfd2bcee1");
        //new GetJSONData(this).execute(getJSONURI());
    }

    private String getJSONURI()
    {
        return new StringBuilder().append("http://api.openweathermap.org/data/2.5/forecast?q=")
                .append(cityName.replace(' ', '_'))
                .append(',')
                .append(countryInitials)
                .append("&APPID=")
                .append(API_KEY)
                .toString();
    }

    private void setAllViews()
    {
        progressLoading = new ProgressDialog(this);
        progressLoading.setTitle(getString(R.string.lbl_loading));
        progressLoading.setCancelable(false);

        textDailyForecast = (TextView)     findViewById(R.id.textViewWeatherTitle);
        textForecast      = (TextView)     findViewById(R.id.textViewForecast);

        rvBasicWeather    = (RecyclerView) findViewById(R.id.recyclerBasicWeather);
        rvDetailedWeather = (RecyclerView) findViewById(R.id.recyclerDetailedWeather);
    }

    @Override
    public void setJSONList(ArrayList<Weather> weather)
    {
        this.weather = weather;
        progressLoading.dismiss();

        dailyWeather = WeatherUtil.getDailyWeatherFromHourly(weather);

        Log.d(CITY_DEBUG_TAG, dailyWeather.size() + " " + weather.size());

        adapter = new WeatherAdapter(this, dailyWeather, R.layout.basic_weather_item);
        rvBasicWeather.setAdapter(adapter);
        rvBasicWeather.setHasFixedSize(true);
        rvBasicWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
