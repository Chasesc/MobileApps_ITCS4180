package com.example.hw6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    public static final String CITY_KEY  = "CITY";
    public static final String COUNTRY_KEY = "COUNTRY";

    private TextView textViewNothingSaved;
    private EditText editCity, editState;
    private Button   buttonSubmit;


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

                Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                intent.putExtra(CITY_KEY,  editCity.getText().toString());
                intent.putExtra(COUNTRY_KEY, editState.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void setAllViews()
    {
        editCity             = (EditText) findViewById(R.id.editTextCity);
        editState            = (EditText) findViewById(R.id.editTextState);
        buttonSubmit         = (Button) findViewById(R.id.buttonSubmit);
        textViewNothingSaved = (TextView)findViewById(R.id.textViewNoFav);
    }
}
