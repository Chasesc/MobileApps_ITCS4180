package com.example.chases.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private Button buttonAdd, buttonEdit, buttonDelete, buttonShow, buttonFinish;

    public static final int ADD_REQ_CODE = 0;
    public static final int EDIT_REQ_CODE = 1;
    public static final int DELETE_REQ_CODE = 2;

    public static final String BUNDLE_KEY  = "BUNDLE";
    public static final String EXPENSE_KEY = "EXPENSE";
    public static final String INTEGER_KEY = "INTEGER";
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

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivityForResult(intent, EDIT_REQ_CODE);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivityForResult(intent, DELETE_REQ_CODE);
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, )
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
        else if(requestCode == EDIT_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle bundle = (Bundle)data.getExtras().getBundle(BUNDLE_KEY);

                Integer index = bundle.getInt(INTEGER_KEY);
                Expense expense = (Expense) bundle.getSerializable(EXPENSE_KEY);

                expenses.set(index, expense);
            }
        }
        else if(requestCode == DELETE_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle bundle = (Bundle)data.getExtras().getBundle(BUNDLE_KEY);

                Integer index = bundle.getInt(INTEGER_KEY);

                Log.d("asdf", "intttttt main: " + index + " " + expenses.size());

                //Cast to int so we call remove(int) instead of remove(Object)
                expenses.remove((int)index);

                Log.d("asdf", "intttttt mainsza: " + expenses.size());
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
