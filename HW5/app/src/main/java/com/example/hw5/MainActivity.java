package com.example.hw5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    public static final String CITY_KEY  = "CITY";
    public static final String STATE_KEY = "STATE";
    public static final String PREFERENCE_FILE_KEY = "4180WeatherPrefs";

    public static SharedPreferences _SharedPreferences;

    private static final Gson _Gson = new Gson();

    private TextView textViewNoFavorites;
    private EditText editCity, editState;
    private Button   buttonSubmit;
    private ListView listFavorites;

    FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _SharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, MODE_PRIVATE);

        setAllViews();

        updateFavoritesList();

        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textViewNoFavorites.setVisibility(View.GONE);
                listFavorites.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                intent.putExtra(CITY_KEY,  editCity.getText().toString());
                intent.putExtra(STATE_KEY, editState.getText().toString());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        updateFavoritesList();
    }

    private void updateFavoritesList()
    {
        HashMap<String, FavoriteData> favorites = getFavorites();

        if(favorites == null || favorites.isEmpty())
        {
            Log.d("PIE", "No favorites saved.");
            textViewNoFavorites.setVisibility(View.VISIBLE);
            listFavorites.setVisibility(View.GONE);
            adapter.clear();
            adapter.notifyDataSetChanged();
            return;
        }

        adapter = new FavoriteAdapter(this, new ArrayList<FavoriteData>(favorites.values()), R.layout.favorite_layout_item);
        //adapter.setNotifyOnChange(true); // This is not needed for this implementation.
        listFavorites.setAdapter(adapter);

        textViewNoFavorites.setVisibility(View.GONE);
        listFavorites.setVisibility(View.VISIBLE);
    }

    public static void saveFavorites(HashMap<String, FavoriteData> favorites)
    {
        SharedPreferences.Editor _Editor = _SharedPreferences.edit();
        String json = _Gson.toJson(favorites);

        _Editor.putString("FAVORITES", _Gson.toJson(favorites));
        _Editor.commit();
    }

    public static HashMap<String, FavoriteData> getFavorites()
    {
        String json = _SharedPreferences.getString("FAVORITES", "");

        Type collectionType = new TypeToken<HashMap<String, FavoriteData>>(){}.getType();
        return _Gson.fromJson(json, collectionType);
    }

    private void setAllViews()
    {
        editCity            = (EditText) findViewById(R.id.editTextCity);
        editState           = (EditText) findViewById(R.id.editTextState);
        buttonSubmit        = (Button) findViewById(R.id.buttonSubmit);
        listFavorites       = (ListView) findViewById(R.id.listViewFavorites);
        textViewNoFavorites = (TextView)findViewById(R.id.textViewNoFav);

        updateFavoritesList();

        listFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                HashMap<String, FavoriteData> favorites = getFavorites();
                FavoriteData curr = new ArrayList<FavoriteData>(favorites.values()).get(position);
                String key = curr.getCity().toLowerCase() + "_" + curr.getState().toLowerCase();

                if(favorites.containsKey(key))
                {
                    favorites.remove(key);
                    saveFavorites(favorites);
                    updateFavoritesList();
                    Toast.makeText(MainActivity.this, getString(R.string.toast_city_deleted), Toast.LENGTH_LONG).show();
                    return true;
                }

                return false; // Something bad has happened...
            }
        });
    }
}
