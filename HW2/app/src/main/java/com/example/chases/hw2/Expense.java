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
