/*
Chase Schelthoff
 */

package com.example.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class AppUtil
{
    public static class JSONParser
    {
        public static ArrayList<App> parse(String jsonString) throws JSONException
        {
            ArrayList<App> apps = new ArrayList<>();

            JSONArray appsJSONArray = new JSONObject(jsonString).getJSONObject("feed").getJSONArray("entry");

            for(int i = 0; i < appsJSONArray.length(); i++)
            {
                JSONObject appJSONObject = appsJSONArray.getJSONObject(i);
                App app = new App();

                app.setName(appJSONObject.getJSONObject("im:name").getString("label"));

                JSONArray images = appJSONObject.getJSONArray("im:image");
                app.setThumbnailURI(images.getJSONObject(0).getString("label"));
                app.setBiggerThumbnailURI(images.getJSONObject(images.length() - 1).getString("label"));

                JSONObject price = appJSONObject.getJSONObject("im:price").getJSONObject("attributes");
                app.setPrice(price.getString("amount"));
                app.setCurrency(price.getString("currency"));

                // Only add non-filtered items
                if(!MainActivity.savedApps.contains(app))
                    apps.add(app);
            }

            return apps;
        }
    }
}
