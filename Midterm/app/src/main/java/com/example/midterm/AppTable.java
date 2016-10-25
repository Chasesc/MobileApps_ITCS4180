/*
Chase Schelthoff
 */

package com.example.midterm;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chases on 10/24/2016.
 */

public class AppTable
{
    public static final String TABLE_NAME = "apps";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String CURRENCY = "currency";
    public static final String THUMB_URL = "thumb_url";

    public static void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(TABLE_NAME).append(" (")
                .append(ID).append(" text primary key, ")
                .append(NAME).append(" text not null, ")
                .append(PRICE).append(" float not null, ")
                .append(CURRENCY).append(" text not null, ")
                .append(THUMB_URL).append(" text not null);");

        try
        {
            db.execSQL(sb.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        AppTable.onCreate(db);
    }
}
