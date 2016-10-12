/*
HW5 - Mobile Application Development
Chase Schelthoff and Phillip Hunter
 */

package com.example.hw5;

import java.io.Serializable;
import java.util.Date;

public class FavoriteData implements Serializable
{
    private String city;
    private String state;
    private Date dateStored;
    private int tempStored;

    public FavoriteData(String city, String state, Date dateStored, int tempStored)
    {
        this.city = city;
        this.state = state;
        this.dateStored = dateStored;
        this.tempStored = tempStored;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Date getDateStored()
    {
        return dateStored;
    }

    public void setDateStored(Date dateStored)
    {
        this.dateStored = dateStored;
    }

    public int getTempStored()
    {
        return tempStored;
    }

    public void setTempStored(int tempStored)
    {
        this.tempStored = tempStored;
    }

    @Override
    public String toString()
    {
        return "FavoriteData{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", dateStored=" + dateStored +
                ", tempStored=" + tempStored +
                '}';
    }
}
