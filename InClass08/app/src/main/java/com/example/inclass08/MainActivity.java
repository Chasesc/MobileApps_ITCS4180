/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseFragment.IExpenseFragment, AddFragment.IAddFragment, ShowFragment.IShowFragment
{
    private static final String EXPENSE_FRAGMENT_TAG = "EXPENSE_FRAGMENT";
    private static final String ADD_FRAGMENT_TAG = "ADD_FRAGMENT";
    private static final String SHOW_FRAGMENT_TAG = "SHOW_FRAGMENT";
    public static ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenses = new ArrayList<>();

        setFragments();
    }

    private void setFragments()
    {
        getFragmentManager().beginTransaction()
                .add(R.id.container, new ExpenseFragment(), EXPENSE_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void gotoAddExpense()
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new AddFragment(), ADD_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoShowExpense(Expense expense)
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ShowFragment(expense), SHOW_FRAGMENT_TAG)
                .commit();

        // This isn't working.  Why?

        //ShowFragment showFragment = (ShowFragment) getFragmentManager().findFragmentByTag(SHOW_FRAGMENT_TAG);
        //showFragment.setExpenseData(expense);
    }

    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void goBackToExpensesFragment()
    {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, new ExpenseFragment(), EXPENSE_FRAGMENT_TAG)
                .commit();

        ExpenseFragment expenseFragment = (ExpenseFragment) fm.findFragmentByTag(EXPENSE_FRAGMENT_TAG);
        expenseFragment.setExpenses(this.expenses);
    }

    @Override
    public void addExpense(Expense expense)
    {
        expenses.add(expense);
    }

    @Override
    public void goBack()
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ExpenseFragment(), EXPENSE_FRAGMENT_TAG)
                .commit();
    }
}
