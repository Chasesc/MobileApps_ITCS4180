package com.example.hw6;

public class City
{
    private int id;
    private String name, country;
    private float temperature;
    private int favorite;

    public City()
    {

    }

    public City(int id, String name, String country, float temperature, int favorite)
    {
        this.id = id;
        this.name = name;
        this.country = country;
        this.temperature = temperature;
        this.favorite = favorite;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public float getTemperature()
    {
        return temperature;
    }

    public void setTemperature(float temperature)
    {
        this.temperature = temperature;
    }

    public boolean isFavorite()
    {
        return favorite == 1;
    }

    public void setFavorite(int favorite)
    {
        this.favorite = favorite;
    }

    @Override
    public String toString()
    {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", temperature=" + temperature +
                ", favorite=" + favorite +
                '}' + '\n';
    }
}
