/*
InClass07
Chase Schelthoff
 */

package com.example.inclass07;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetJSONData.IJSONActivity
{
    private static final String DEBUG_TAG = "INCLASS07";
    private static final String JSON_URL = "https://itunes.apple.com/us/rss/toppodcasts/limit=30/json";
    public static final String PODCAST_KEY = "PODCAST";

    private ArrayList<Podcast> podcasts;

    private ProgressDialog progressDialog;

    private EditText editSearch;
    private Button buttonGo, buttonClear;
    private ListView listViewContent;
    private PodcastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllViews();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.lbl_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();


        new GetJSONData(MainActivity.this).execute(JSON_URL);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(podcasts.size() == 0)
                    return;

                int amount_matches = 0;

                String text = editSearch.getText().toString().toLowerCase();

                for(int i = 0; i < podcasts.size(); i++)
                {
                    String podcastTitle = podcasts.get(i).getTitle().toLowerCase();
                    if(podcastTitle.contains(text))
                    {
                        podcasts.get(i).setSearchedFor(true);
                        Collections.swap(podcasts, amount_matches++, i);
                    }
                    else
                    {
                        podcasts.get(i).setSearchedFor(false);
                    }

                }

                if(amount_matches > 0)
                    adapter.notifyDataSetChanged();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < podcasts.size(); i++)
                {
                    podcasts.get(i).setSearchedFor(false);
                }

                Collections.sort(podcasts);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setAllViews()
    {
        editSearch = (EditText) findViewById(R.id.editTextSearch);
        buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        listViewContent = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void setJSONList(final ArrayList<Podcast> podcasts)
    {
        this.podcasts = podcasts;

        Collections.sort(podcasts);

        for(Podcast podcast : podcasts)
            Log.d(DEBUG_TAG, podcast.toString());


        adapter = new PodcastAdapter(this, R.layout.podcast_item, podcasts);
        listViewContent.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listViewContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(DEBUG_TAG, "pos" + position);
                Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                intent.putExtra(PODCAST_KEY, podcasts.get(position));
                startActivity(intent);
            }
        });

        progressDialog.dismiss();
    }
}
