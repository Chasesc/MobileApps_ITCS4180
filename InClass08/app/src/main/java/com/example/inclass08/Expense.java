/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;

/**
 * Created by Chases on 10/17/2016.
 */
public class Expense
{
    private String name, category, date;
    private double amount;

    public Expense(String name, String category, String date, double amount)
    {
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public boolean isInvalid()
    {
        return name.isEmpty() || category.isEmpty() || date.isEmpty() || amount < 0.0;
    }
}
