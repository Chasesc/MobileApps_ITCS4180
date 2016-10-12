package com.example.hw5;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WeatherUtil
{
    public static class JSONParser
    {
        public static ArrayList<Weather> parse(String jsonString) throws JSONException
        {
            ArrayList<Weather> weather = new ArrayList<>();

            JSONArray weatherJSONArray = new JSONObject(jsonString).getJSONArray("hourly_forecast");

            for(int i = 0; i < weatherJSONArray.length(); i++)
            {
                JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(i);
                Weather currWeather = new Weather();

                currWeather.setHour(weatherJSONObject.getJSONObject("FCTTIME").getString("civil"));
                currWeather.setTemperature(weatherJSONObject.getJSONObject("temp").getInt("english"));
                currWeather.setWeatherCondition(weatherJSONObject.getString("condition"));
                currWeather.setIconURI(weatherJSONObject.getString("icon_url"));
                currWeather.setTempFeelsLike(weatherJSONObject.getJSONObject("feelslike").getInt("english"));
                currWeather.setHumidity(weatherJSONObject.getInt("humidity"));
                currWeather.setDewpoint(weatherJSONObject.getJSONObject("dewpoint").getInt("english"));
                currWeather.setPressure(weatherJSONObject.getJSONObject("mslp").getInt("metric"));
                currWeather.setWindSpeed(weatherJSONObject.getJSONObject("wspd").getInt("english"));

                JSONObject windDirection = weatherJSONObject.getJSONObject("wdir");
                currWeather.setWindDirection(windDirection.getString("dir"));
                currWeather.setWindDegrees(windDirection.getInt("degrees"));


                weather.add(currWeather);
            }

            return weather;
        }
    }
}
