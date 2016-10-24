/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment
{
    private ArrayList<Expense> expenses;
    private IExpenseFragment mActivity;

    private TextView textNoExpenses;
    private ListView listViewExpenses;

    public ExpenseFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        Log.d("expensefragment", "onCreateView!!!!!");
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof IExpenseFragment)
            mActivity = (IExpenseFragment)context;
        else
            throw new RuntimeException("IExpenseFragment not implemented in context!");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(expenses == null)
            expenses = MainActivity.expenses;

        Log.d("expensefragment", "onActivityCreated22!!!!!");

        getActivity().findViewById(R.id.imageViewAddExpense).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mActivity.gotoAddExpense();
            }
        });

        textNoExpenses = (TextView) getActivity().findViewById(R.id.textViewNoExpenses);
        listViewExpenses = (ListView)getActivity().findViewById(R.id.listViewExpenses);

        listViewExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mActivity.gotoShowExpense(expenses.get(position));
            }
        });

        listViewExpenses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                expenses.remove(position);
                updateExpensesList();
                Toast.makeText(getActivity(), getString(R.string.lbl_deleted), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        updateExpensesList();
    }

    private void updateExpensesList()
    {
        if(this.expenses == null || this.expenses.isEmpty())
        {
            textNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
            Log.d("expensefragment", "empty!!!!!");
            return;
        }

        Log.d("expensefragment", "updateExpensesList!!!!!");



        ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), this.expenses, R.layout.expense_layout_item);
        listViewExpenses.setAdapter(adapter);

        textNoExpenses.setVisibility(View.GONE);
        listViewExpenses.setVisibility(View.VISIBLE);


    }

    public void setExpenses(ArrayList<Expense> expenses)
    {
        this.expenses = expenses;
        Log.d("expensefragment", "in setExpenses!!!!@@@@$$$");
    }

    public interface IExpenseFragment
    {
        void gotoAddExpense();
        void gotoShowExpense(Expense expense);
    }
}
