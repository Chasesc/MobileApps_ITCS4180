/*
Chase Schelthoff
 */

package com.example.midterm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class AppDAO
{
    private SQLiteDatabase db;
    private Context context;

    public AppDAO(SQLiteDatabase db, Context context)
    {
        this.db = db;
        this.context = context;
    }

    public long save(App app)
    {
        ContentValues values = new ContentValues();
        values.put(AppTable.ID, app.getName());
        values.put(AppTable.NAME, app.getName());
        values.put(AppTable.PRICE, app.getPriceDouble());
        values.put(AppTable.THUMB_URL, app.getBiggerThumbnailURI());
        values.put(AppTable.CURRENCY, app.getCurrency());

        return db.insert(AppTable.TABLE_NAME, null, values);
    }

    public boolean update(App app)
    {
        ContentValues values = new ContentValues();
        values.put(AppTable.ID, app.getName());
        values.put(AppTable.NAME, app.getName());
        values.put(AppTable.PRICE, app.getPriceDouble());
        values.put(AppTable.THUMB_URL, app.getBiggerThumbnailURI());
        values.put(AppTable.CURRENCY, app.getCurrency());

        return db.update(AppTable.TABLE_NAME, values, AppTable.ID + "= \"" + app.getName() + "\"", null) > 0;
    }

    public boolean delete(App app)
    {
        return db.delete(AppTable.TABLE_NAME, AppTable.ID + "= \"" + app.getName() + "\"", null) > 0;
    }

    public ArrayList<App> getAll()
    {
        ArrayList<App> apps = new ArrayList<>();

        Cursor c = db.query(AppTable.TABLE_NAME,
                new String[]{AppTable.ID, AppTable.NAME, AppTable.PRICE, AppTable.THUMB_URL, AppTable.CURRENCY},
                null, null, null, null, null);

        if(c != null && c.getCount() > 0)
        {
            c.moveToFirst();

            do
            {
                apps.add(buildAppFromCursor(c));
            } while(c.moveToNext());

            if(!c.isClosed())
                c.close();
        }

        return apps;
    }

    private App buildAppFromCursor(Cursor c)
    {
        App app = null;

        if(c != null)
        {
            app = new App();
            app.setId(c.getString(0));
            app.setName(c.getString(1));
            app.setPrice(c.getDouble(2) + "");
            app.setThumbnailURI(c.getString(3));
            app.setCurrency(c.getString(4));
        }

         return app;
    }
}
