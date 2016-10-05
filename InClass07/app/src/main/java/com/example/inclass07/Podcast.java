/*
InClass07
Chase Schelthoff
 */

package com.example.inclass07;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chases on 10/3/2016.
 */
public class Podcast implements Comparable, Serializable
{
    private String title, summary, releaseDate, smallSquareImage, largeImage;
    private boolean isSearchedFor;


    public boolean isSearchedFor()
    {
        return isSearchedFor;
    }

    public void setSearchedFor(boolean searchedFor)
    {
        isSearchedFor = searchedFor;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getSmallSquareImage()
    {
        return smallSquareImage;
    }

    public void setSmallSquareImage(String smallSquareImage)
    {
        this.smallSquareImage = smallSquareImage;
    }

    public String getLargeImage()
    {
        return largeImage;
    }

    public void setLargeImage(String largeImage)
    {
        this.largeImage = largeImage;
    }


    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", smallSquareImage='" + smallSquareImage + '\'' +
                ", largeImage='" + largeImage + '\'' +
                '}' + '\n';
    }

    @Override
    public int compareTo(Object another)
    {
        if(another instanceof Podcast)
        {
            Podcast other = (Podcast)another;


            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

            try {
                Date myDate = sdf.parse(releaseDate);
                Date otherDate = sdf.parse(other.releaseDate);

                return myDate.compareTo(otherDate);

            } catch (ParseException e) {
                return 0;
            }

        }
        return 0;
    }
}
