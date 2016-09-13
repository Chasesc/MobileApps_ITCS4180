package com.example.chases.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button buttonAdd, buttonEdit, buttonDelete, buttonShow, buttonFinish;

    public static final int ADD_REQ_CODE = 0;

    public static final String EXPENSE_KEY = "EXPENSE";
    private ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllViews();
        expenses = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQ_CODE);
            }
        });


        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == ADD_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Expense expense = (Expense) data.getExtras().getSerializable(EXPENSE_KEY);


                if(expense != null)
                    expenses.add(expense);

                Log.d("asdfasdf", expense.toString() + "\n" + expenses.size());
            }
        }
    }

    private void setAllViews()
    {
        buttonAdd    = (Button)findViewById(R.id.buttonAdd);
        buttonEdit   = (Button)findViewById(R.id.buttonEdit);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        buttonShow   = (Button)findViewById(R.id.buttonShow);
        buttonFinish = (Button)findViewById(R.id.buttonFinish);
    }
}
