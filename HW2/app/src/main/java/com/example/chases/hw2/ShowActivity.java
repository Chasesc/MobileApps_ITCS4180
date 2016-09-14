package com.example.chases.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setTitle(getString(R.string.app_name_show));
    }
}
