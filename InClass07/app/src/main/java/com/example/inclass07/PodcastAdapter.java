/*
InClass07
Chase Schelthoff
 */

package com.example.inclass07;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chases on 10/3/2016.
 */
public class PodcastAdapter extends ArrayAdapter<Podcast>
{
    private Context context;
    private ArrayList<Podcast> podcasts;
    private int resource;

    public PodcastAdapter(Context context, int resource, ArrayList<Podcast> podcasts)
    {
        super(context, R.layout.podcast_item, podcasts);
        this.context = context;
        this.podcasts = podcasts;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.podcast_item, parent, false);

        ImageView sqImage = (ImageView) convertView.findViewById(R.id.imageView);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView);

        if(podcasts.get(position).isSearchedFor())
            convertView.setBackgroundColor(Color.GREEN);
        else
            convertView.setBackgroundColor(Color.TRANSPARENT);

        new LoadImage(sqImage).execute(podcasts.get(position).getSmallSquareImage());
        tvTitle.setText(podcasts.get(position).getTitle());

        return convertView;
    }
}
