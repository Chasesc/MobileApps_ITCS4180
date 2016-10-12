/*
HW5 - Mobile Application Development
Chase Schelthoff and Phillip Hunter
 */

package com.example.hw5;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadImage extends AsyncTask<String, Void, Bitmap>
{
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        iv.setImageBitmap(null);
    }

    private ImageView iv;

    public LoadImage(ImageView imageView)
    {
        this.iv = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null)
            iv.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        try
        {
            URL url=new URL(params[0]);
            HttpURLConnection connection= null;
            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode =connection.getResponseCode();
                if(statusCode==HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStream=connection.getInputStream();
                    return BitmapFactory.decodeStream(inputStream);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
