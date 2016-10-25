/*
Chase Schelthoff
 */

package com.example.midterm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class SavedAppAdapter extends RecyclerView.Adapter<SavedAppViewHolder>
{
    private static final String TAG = "SavedAppAdapter";
    private IUpdateViews activity;
    private ArrayList<App> apps;
    private Context context;

    public SavedAppAdapter(ArrayList<App> apps, Context context, IUpdateViews activity)
    {
        this.apps = apps;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public SavedAppViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context currContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(currContext);

        View appView = inflater.inflate(R.layout.saved_app_item, parent, false);

        return new SavedAppViewHolder(appView);
    }

    @Override
    public void onBindViewHolder(SavedAppViewHolder holder, final int position)
    {
        final App currApp = apps.get(position);

        TextView textName = holder.textName;
        TextView textPrice = holder.textPrice;
        ImageView imageIcon = holder.imageIcon;
        ImageView imagePrice = holder.imagePrice;
        ImageView imageDelete = holder.iconDelete;

        imageDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, currApp.toString());
                MainActivity.savedApps.remove(position);
                MainActivity.dataManager.deleteApp(currApp);
                notifyDataSetChanged();
                activity.updateViews();
            }
        });

        textName.setText(currApp.getName());
        textPrice.setText(new StringBuilder()
                .append(context.getString(R.string.lbl_price))
                .append(" ")
                .append(currApp.getCurrency())
                .append(" ")
                .append(currApp.getPrice()));

        Picasso.with(context).load(currApp.getThumbnailURI()).into(imageIcon);
        Picasso.with(context).load(currApp.getPriceResourceId()).into(imagePrice);
    }

    @Override
    public int getItemCount()
    {
        return apps.size();
    }

    public interface IUpdateViews
    {
        void updateViews();
    }
}
