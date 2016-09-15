package com.example.chases.hw2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ShowActivity extends AppCompatActivity
{
    private TextView textName, textAmount, textCategory, textDate;
    private ImageView imageReceipt, imageFirst, imageBack, imageForward, imageLast;
    private Button buttonFinish;

    private String imgString;
    private int currentIndex = 0;

    private ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setTitle(getString(R.string.app_name_show));

        setAllViews();

        Intent intent = getIntent();
        expenses = (ArrayList<Expense>) intent.getExtras().getSerializable(MainActivity.EXPENSE_KEY);
        if(expenses == null)
        {
            Log.e("showactivity", "expenses is null :(");
            return;
        }

        if(!expenses.isEmpty())
            updateViewsFromExpense(expenses.get(currentIndex));

        imageFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = 0;
                updateViewsFromExpense(expenses.get(currentIndex));
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(--currentIndex >= 0)
                    updateViewsFromExpense(expenses.get(currentIndex));
                else
                    currentIndex = 0;
            }
        });

        imageForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(++currentIndex < expenses.size())
                    updateViewsFromExpense(expenses.get(currentIndex));
                else
                    currentIndex = expenses.size() - 1;
            }
        });

        imageLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = expenses.size() - 1;
                updateViewsFromExpense(expenses.get(currentIndex));
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAllViews()
    {
        textName        = (TextView)findViewById(R.id.textViewName);
        textAmount      = (TextView)findViewById(R.id.textViewAmount);
        textCategory    = (TextView)findViewById(R.id.textViewCategory);
        textDate        = (TextView)findViewById(R.id.textViewDate);
        imageReceipt    = (ImageView)findViewById(R.id.imageViewReceipt);
        imageFirst      = (ImageView)findViewById(R.id.imageViewFirst);
        imageBack       = (ImageView)findViewById(R.id.imageViewBack);
        imageForward    = (ImageView)findViewById(R.id.imageViewForward);
        imageLast       = (ImageView)findViewById(R.id.imageViewLast);
        buttonFinish    = (Button)findViewById(R.id.buttonFinish);
    }

    private void updateViewsFromExpense(Expense expense)
    {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(expense.getDate());
        textName.setText(expense.getName());
        textAmount.setText(expense.getAmount() + "");
        textDate.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
                cal.get(Calendar.YEAR));
        textCategory.setText(expense.getCategory());

        /*

        int permessionCheck = ContextCompat.checkSelfPermission(ShowActivity.this,
                android.Manifest.permission.MANAGE_DOCUMENTS);

        if(permessionCheck != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("asdf", "no per: " + permessionCheck);

            if(ActivityCompat.shouldShowRequestPermissionRationale(ShowActivity.this,
                    android.Manifest.permission.MANAGE_DOCUMENTS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(ShowActivity.this,
                        new String[] {android.Manifest.permission.MANAGE_DOCUMENTS},
                        PERMISSION_REQUEST_MANAGE_DOCUMENTS);
            }
        }*/

        imgString = expense.getImage();

    }
}
