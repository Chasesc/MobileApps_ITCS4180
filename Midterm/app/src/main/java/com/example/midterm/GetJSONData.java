/*
Chase Schelthoff
 */

package com.example.midterm;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class GetJSONData extends AsyncTask<String, Void, ArrayList<App>>
{
    private IJSONActivity activity;

    public GetJSONData(IJSONActivity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps)
    {
        super.onPostExecute(apps);
        activity.setJSONList(apps);
    }

    @Override
    protected ArrayList<App> doInBackground(String... params)
    {
        URL url = null;
        try
        {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line = reader.readLine();
                while (line != null)
                {
                    sb.append(line);
                    line = reader.readLine();
                }

                return AppUtil.JSONParser.parse(sb.toString());
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (ProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public interface IJSONActivity
    {
        void setJSONList(ArrayList<App> apps);
    }
}
