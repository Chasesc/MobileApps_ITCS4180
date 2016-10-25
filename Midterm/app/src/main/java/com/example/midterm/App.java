/*
Chase Schelthoff
 */

package com.example.midterm;

import android.util.Log;

/**
 * Created by Chases on 10/24/2016.
 */

public class App
{
    private static final String TAG = "AppClass";
    private String id, name, price, currency, thumbnailURI, biggerThumbnailURI;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price.indexOf(".") < 0 ? price : price.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public String getThumbnailURI()
    {
        return thumbnailURI;
    }

    public void setThumbnailURI(String thumbnailURI)
    {
        this.thumbnailURI = thumbnailURI;
    }

    public String getBiggerThumbnailURI()
    {
        return biggerThumbnailURI;
    }

    public void setBiggerThumbnailURI(String biggerThumbnailURI)
    {
        this.biggerThumbnailURI = biggerThumbnailURI;
    }

    public double getPriceDouble()
    {
        double priceVal = 0.0;

        try
        {
            priceVal = Double.parseDouble(price);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Unable to parse value.");
        }

        return priceVal;
    }

    public int getPriceResourceId()
    {
        double priceVal = getPriceDouble();

        if(priceVal <= 1.99) return R.drawable.price_low;
        else if(priceVal <= 5.99) return R.drawable.price_medium;
        else return R.drawable.price_high;
    }

    @Override
    public boolean equals(Object other)
    {
        if(other instanceof App)
            return equals((App)other);

        return false;
    }

    public boolean equals(App other)
    {
        return this.name.equals(other.name);
    }

    @Override
    public String toString()
    {
        return "App{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", thumbnailURI='" + thumbnailURI + '\'' +
                ", biggerThumbnailURI='" + biggerThumbnailURI + '\'' +
                '}' + '\n';
    }
}
