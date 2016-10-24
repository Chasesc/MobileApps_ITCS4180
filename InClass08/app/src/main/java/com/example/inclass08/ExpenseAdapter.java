/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chases on 10/17/2016.
 */
public class ExpenseAdapter extends ArrayAdapter<Expense>
{
    private Context context;
    private ArrayList<Expense> expenses;
    private int resource;


    public ExpenseAdapter(Context context, ArrayList<Expense> expenses, int resource)
    {
        super(context, resource, expenses);
        this.context = context;
        this.expenses = expenses;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(resource, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView textName = (TextView) convertView.findViewById(R.id.textExpenseName);
        TextView textAmount = (TextView) convertView.findViewById(R.id.textExpenseAmount);

        textName.setText(expense.getName());
        textAmount.setText("$" + expense.getAmount());

        return convertView;
    }
}
