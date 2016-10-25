/*
Chase Schelthoff
 */

package com.example.midterm;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Chases on 10/24/2016.
 */

public class AppCompare implements Comparator<App>
{
    public static final String TAG = "AppCompare";

    @Override
    public int compare(App o1, App o2)
    {
        double firstPrice = o1.getPriceDouble();
        double secondPrice = o2.getPriceDouble();

        // Could do this better if I have time.
        int greaterVal = MainActivity.isAscending? 1 : -1;
        int lesserVal = MainActivity.isAscending? -1 : 1;

        if(firstPrice == secondPrice) return 0;
        if(firstPrice > secondPrice)  return greaterVal;

        return lesserVal;
    }
}
