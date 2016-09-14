package com.example.chases.hw2;

import android.content.res.Resources;
import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable
{
    private String name, category;
    private double amount;
    private Date date;
    private String image;

    public Expense(String name, String category, double amount, Date date, String image)
    {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.image = image;
    }

    public String getCategory()
    {
        return category;
    }

    public double getAmount()
    {
        return amount;
    }

    public Date getDate()
    {
        return date;
    }

    public String getImage()
    {
        return image;
    }

    public String getName()
    {
        return name;

    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setImage(String image)
    {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", image=" + image +
                '}';
    }
}
