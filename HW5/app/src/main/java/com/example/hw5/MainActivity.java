package com.example.hw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    public static final String CITY_KEY  = "CITY";
    public static final String STATE_KEY = "STATE";

    private TextView textViewNoFavorites;
    private EditText editCity, editState;
    private Button   buttonSubmit;
    private ListView listFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllViews();

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

    private void setAllViews()
    {
        editCity            = (EditText) findViewById(R.id.editTextCity);
        editState           = (EditText) findViewById(R.id.editTextState);
        buttonSubmit        = (Button) findViewById(R.id.buttonSubmit);
        listFavorites       = (ListView) findViewById(R.id.listViewFavorites);
        textViewNoFavorites = (TextView)findViewById(R.id.textViewNoFav);
    }
}
