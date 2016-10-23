package com.example.hw6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    public static final String CITY_KEY  = "CITY";
    public static final String COUNTRY_KEY = "COUNTRY";

    private TextView textViewNothingSaved;
    private EditText editCity, editState;
    private Button   buttonSubmit;

    private DataManager dm;

    private ArrayList<City> cities;

    private RecyclerView rvSavedCities;
    private SavedCityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllViews();
        setRecyclerView();

        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                intent.putExtra(CITY_KEY,  editCity.getText().toString());
                intent.putExtra(COUNTRY_KEY, editState.getText().toString());

                startActivity(intent);
            }
        });

        Log.d(TAG, cities.toString());
    }

    private void setRecyclerView()
    {
        showRecyclerView(!cities.isEmpty());
        cityAdapter = new SavedCityAdapter(this, cities, R.layout.saved_city_item);
        rvSavedCities.setAdapter(cityAdapter);
        rvSavedCities.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAllViews()
    {
        editCity             = (EditText) findViewById(R.id.editTextCity);
        editState            = (EditText) findViewById(R.id.editTextState);
        buttonSubmit         = (Button) findViewById(R.id.buttonSubmit);
        textViewNothingSaved = (TextView)findViewById(R.id.textViewNoFav);
        rvSavedCities        = (RecyclerView) findViewById(R.id.recyclerSavedCities);

        dm = new DataManager(MainActivity.this);
        cities = dm.getAllCities();
    }

    private void showRecyclerView(boolean show)
    {
        if(show)
        {
            textViewNothingSaved.setVisibility(View.GONE);
            rvSavedCities.setVisibility(View.VISIBLE);
        }
        else
        {
            textViewNothingSaved.setVisibility(View.VISIBLE);
            rvSavedCities.setVisibility(View.GONE);
        }
    }
}
