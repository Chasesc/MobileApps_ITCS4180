/*
InClass07
Chase Schelthoff
 */


package com.example.inclass07;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chases on 10/3/2016.
 */
public class PodcastUtil
{
    public static class JSONParser
    {
        public static ArrayList<Podcast> parse(String jsonString) throws JSONException
        {
            ArrayList<Podcast> podcasts = new ArrayList<>();

            Log.d("testasdf", jsonString);

            JSONObject root = new JSONObject(jsonString).getJSONObject("feed");

            Log.d("testasdfroot", root.toString());

            JSONArray podcastJSONArray = root.getJSONArray("entry");

            for(int i = 0; i < podcastJSONArray.length(); i++)
            {
                JSONObject podcastJSONObject = podcastJSONArray.getJSONObject(i);
                Podcast currPodcast = new Podcast();

                currPodcast.setTitle(podcastJSONObject.getJSONObject("title").getString("label"));

                int smallest = Integer.MAX_VALUE, largest = 0;
                JSONArray images = podcastJSONObject.getJSONArray("im:image");
                for(int j = 0; j < images.length(); j++)
                {
                    JSONObject imagesJSONObject = images.getJSONObject(j);

                    String URI = imagesJSONObject.getString("label");
                    int height = imagesJSONObject.getJSONObject("attributes").getInt("height");

                    if(height > largest)
                    {
                        largest = height;
                        currPodcast.setLargeImage(URI);
                    }

                    if(height < smallest)
                    {
                        smallest = height;
                        currPodcast.setSmallSquareImage(URI);
                    }
                }

                currPodcast.setSummary(podcastJSONObject.getJSONObject("summary").getString("label"));
                currPodcast.setReleaseDate(podcastJSONObject.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));



                podcasts.add(currPodcast);
            }

            return podcasts;
        }
    }
}
