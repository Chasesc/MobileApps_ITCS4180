/*
Chase Schelthoff
 */

package com.example.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Chases on 10/24/2016.
 */

public class DataManager
{
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private AppDAO cityDAO;


    public DataManager(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        cityDAO = new AppDAO(db, context);
    }

    public void close()
    {
        db.close();
    }

    public long saveApp(App app)
    {
        return cityDAO.save(app);
    }

    public boolean updateApp(App app)
    {
        return cityDAO.update(app);
    }

    public boolean deleteApp(App app)
    {
        return cityDAO.delete(app);
    }

    public ArrayList<App> getAllApps()
    {
        return cityDAO.getAll();
    }


}
