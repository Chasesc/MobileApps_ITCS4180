/*
Chase Schelthoff
 */

package com.example.midterm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetJSONData.IJSONActivity, SavedAppAdapter.IUpdateViews
{
    private static final String TAG = "MainActivity";
    private static final String JSON_URI = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";

    public static Boolean isAscending;
    public static DataManager dataManager;
    public static ArrayList<App> savedApps;

    private TextView textNoFiltered;
    private Switch switchOrder;
    private ListView listViewApps;
    private RecyclerView recyclerViewFiltered;
    private SavedAppAdapter savedAppAdapter;
    private ImageView imageRefresh;

    private ArrayList<App> apps;
    private ProgressDialog progress;
    private AppAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isAscending = false;
        dataManager = new DataManager(MainActivity.this);
        savedApps = dataManager.getAllApps();

        Log.d(TAG, "Saved Size: " + savedApps.size());

        setAllViews();
        setOnClickListeners();

        progress.show();

        new GetJSONData(this).execute(JSON_URI);
    }

    private void setAllViews()
    {
        textNoFiltered = (TextView) findViewById(R.id.textViewNoFiltered);
        switchOrder = (Switch) findViewById(R.id.switchAscending);
        listViewApps = (ListView) findViewById(R.id.listApps);
        recyclerViewFiltered = (RecyclerView) findViewById(R.id.recyclerViewFiltered);
        imageRefresh = (ImageView) findViewById(R.id.imageViewRefresh);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle(getText(R.string.lbl_loading));
    }

    private void setOnClickListeners()
    {
        imageRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "Refreshing!");
                progress.show();
                new GetJSONData(MainActivity.this).execute(JSON_URI);
            }
        });

        switchOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isAscending = !isAscending;
                String switchText = isAscending? getString(R.string.lbl_ascending) :
                        getString(R.string.lbl_descending);
                switchOrder.setText(switchText);
                imageRefresh.callOnClick();
            }
        });
    }

    private void showRecyclerView(boolean show)
    {
        if(show)
        {
            textNoFiltered.setVisibility(View.GONE);
            recyclerViewFiltered.setVisibility(View.VISIBLE);
        }
        else
        {
            textNoFiltered.setVisibility(View.VISIBLE);
            recyclerViewFiltered.setVisibility(View.GONE);
        }
    }

    private void initRecyclerView()
    {
        showRecyclerView(!savedApps.isEmpty());
        savedAppAdapter = new SavedAppAdapter(savedApps, MainActivity.this, MainActivity.this);
        recyclerViewFiltered.setAdapter(savedAppAdapter);
        recyclerViewFiltered.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setJSONList(final ArrayList<App> apps)
    {
        this.apps = apps;

        progress.dismiss();

        Collections.sort(this.apps, new AppCompare());

        if(apps == null)
        {
            Log.d(TAG, "Apps in setJSONList is null!");
            return;
        }

        //Log.d(TAG, apps.toString() + "\n" + apps.size());
        listAdapter = new AppAdapter(this, apps, R.layout.app_item);
        listViewApps.setAdapter(listAdapter);
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                App currApp = apps.get(position);
                Log.d(TAG, "onItemClick " + position + ":" + currApp);
                savedApps.add(currApp);
                apps.remove(currApp);
                listAdapter.notifyDataSetChanged();
                dataManager.saveApp(currApp);

                savedAppAdapter.notifyDataSetChanged();

                // Needed to show the first item, but not others.. not sure why...
                if(savedApps.size() == 1)
                    imageRefresh.callOnClick();
            }
        });

        initRecyclerView();
    }

    @Override
    public void updateViews()
    {
        showRecyclerView(!savedApps.isEmpty());
    }
}
