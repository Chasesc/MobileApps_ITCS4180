package com.example.hw5;

import java.io.Serializable;

/**
 * Created by Computer on 10/11/2016.
 */

public class Weather implements Serializable
{
    private String iconURI, weatherCondition, hour;
    private int temperature, tempFeelsLike, humidity, dewpoint, pressure, windSpeed, windDegrees;
    private String windDirection, cityName, stateInitials;

    public int getTempFeelsLike()
    {
        return tempFeelsLike;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getStateInitials()
    {
        return stateInitials;
    }

    public void setStateInitials(String stateInitials)
    {
        this.stateInitials = stateInitials;
    }

    public void setTempFeelsLike(int tempFeelsLike)
    {
        this.tempFeelsLike = tempFeelsLike;
    }

    public int getHumidity()
    {
        return humidity;
    }

    public void setHumidity(int humidity)
    {
        this.humidity = humidity;
    }

    public int getDewpoint()
    {
        return dewpoint;
    }

    public void setDewpoint(int dewpoint)
    {
        this.dewpoint = dewpoint;
    }

    public int getPressure()
    {
        return pressure;
    }

    public void setPressure(int pressure)
    {
        this.pressure = pressure;
    }

    public int getWindSpeed()
    {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed)
    {
        this.windSpeed = windSpeed;
    }

    public int getWindDegrees()
    {
        return windDegrees;
    }

    public void setWindDegrees(int windDegrees)
    {
        this.windDegrees = windDegrees;
    }

    public String getWindDirection()
    {
        return windDirection;
    }

    public void setWindDirection(String windDirection)
    {
        this.windDirection = windDirection;
    }

    public String getIconURI()
    {
        return iconURI;
    }

    public void setIconURI(String iconURI)
    {
        this.iconURI = iconURI;
    }

    public String getWeatherCondition()
    {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition)
    {
        this.weatherCondition = weatherCondition;
    }

    public String getHour()
    {
        return hour;
    }

    public void setHour(String hour)
    {
        this.hour = hour;
    }

    public int getTemperature()
    {
        return temperature;
    }

    public void setTemperature(int temperature)
    {
        this.temperature = temperature;
    }

    @Override
    public String toString()
    {
        return "Weather{" +
                "iconURI='" + iconURI + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", hour='" + hour + '\'' +
                ", temperature=" + temperature +
                ", tempFeelsLike=" + tempFeelsLike +
                ", humidity=" + humidity +
                ", dewpoint=" + dewpoint +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                ", windDirection='" + windDirection + '\'' +
                '}' + '\n';
    }
}
