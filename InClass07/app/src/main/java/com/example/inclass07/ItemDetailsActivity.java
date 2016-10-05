/*
InClass07
Chase Schelthoff
 */

package com.example.inclass07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailsActivity extends AppCompatActivity
{
    private Podcast podcast;
    private ImageView iv;
    private TextView tvTitle, tvDate, tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent = getIntent();

        if(intent == null)
        {
            return;
        }

        setAllViews();

        Bundle bundle = intent.getExtras();
        podcast = (Podcast) bundle.getSerializable(MainActivity.PODCAST_KEY);

        tvTitle.setText(podcast.getTitle());
        tvDate.setText(getString(R.string.lbl_date) + " " + podcast.getReleaseDate());
        tvSummary.setText(podcast.getSummary());

        new LoadImage(iv).execute(podcast.getLargeImage());


    }

    private void setAllViews()
    {
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvDate = (TextView) findViewById(R.id.textViewUpdateDate);
        tvSummary = (TextView) findViewById(R.id.textViewSummary);

        iv = (ImageView) findViewById(R.id.imageViewLg);
    }
}
