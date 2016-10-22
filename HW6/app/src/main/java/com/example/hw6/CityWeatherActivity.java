package com.example.hw6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CityWeatherActivity extends AppCompatActivity implements GetJSONData.IJSONActivity
{
    private static final String CITY_DEBUG_TAG = "CityWeatherActivity";
    private static final String API_KEY = "a37a50b9efa023214652951dfd2bcee1";

    private WeatherAdapter adapterBasic;
    private WeatherAdapterDetailed adapterDetailed;

    private RecyclerView rvBasicWeather, rvDetailedWeather;
    private ProgressDialog progressLoading;
    private TextView textDailyForecast, textForecast;


    private String cityName, countryInitials;
    private ArrayList<Weather> weather, dailyWeatherSummary;
    private HashMap<Date, ArrayList<Weather>> dateToWeather;

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

        dailyWeatherSummary = WeatherUtil.getDailyWeatherSummary(weather);
        dateToWeather = WeatherUtil.getDailyWeatherFromHourly(weather);

        final ArrayList<Weather> dayWeatherItems = dateToWeather.get(getTodayWithoutTime());

        for(Date day : dateToWeather.keySet())
        {
            Log.d(CITY_DEBUG_TAG, "weather on day " + day + "\n" + dateToWeather.get(day));
        }

        //Log.d(CITY_DEBUG_TAG, dailyWeatherSummary.size() + " " + weather.size());

        adapterBasic = new WeatherAdapter(this, dailyWeatherSummary, R.layout.basic_weather_item);

        adapterBasic.setOnItemClickListener(new WeatherAdapter.ClickListener()
        {
            @Override
            public void onItemClick(int position, View v)
            {
                Date selectedDate = dailyWeatherSummary.get(position).getRealDate();
                Log.d(CITY_DEBUG_TAG, "onItemClickpos " + dateToWeather.get(selectedDate) + "\ndats day: " + selectedDate);
                dayWeatherItems.clear();
                dayWeatherItems.addAll(dateToWeather.get(selectedDate));
                adapterDetailed.notifyDataSetChanged();
                rvDetailedWeather.scrollToPosition(0);

                setThreeHourlyForecastText(selectedDate);
            }
        });

        rvBasicWeather.setAdapter(adapterBasic);
        rvBasicWeather.setHasFixedSize(true);
        rvBasicWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Log.d(CITY_DEBUG_TAG, "test\n" + dateToWeather.get(getTodayWithoutTime()));

        adapterDetailed = new WeatherAdapterDetailed(this, dayWeatherItems, R.layout.detailed_weather_item);
        rvDetailedWeather.setAdapter(adapterDetailed);
        rvDetailedWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        setThreeHourlyForecastText(new Date());
    }

    private void setThreeHourlyForecastText(Date date)
    {
        String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
        textForecast.setText(getString(R.string.lbl_three_hour_forecast) + " " + formattedDate);
    }

    private Date getTodayWithoutTime()
    {
        SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e)
        {
            return null;
        }
    }
}
