/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment
{
    private IAddFragment mActivity;

    private EditText editName, editAmount;
    private Spinner spinnerCategory;
    private Button buttonAdd, buttonCancel;

    public AddFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setAllViews();
        setOnClicks();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof IAddFragment)
            mActivity = (IAddFragment)context;
        else
            throw new RuntimeException("IExpenseFragment not implemented in context!");
    }

    private void setAllViews()
    {
        editName = (EditText)getActivity().findViewById(R.id.editTextName);
        editAmount = (EditText) getActivity().findViewById(R.id.editTextAmount);
        spinnerCategory = (Spinner) getActivity().findViewById(R.id.spinnerCategory);
        buttonAdd = (Button) getActivity().findViewById(R.id.buttonAdd);
        buttonCancel = (Button) getActivity().findViewById(R.id.buttonCancel);
    }

    private void setOnClicks()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editName.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();
                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

                double amount = -1.0;

                try
                {
                    amount = Double.parseDouble(editAmount.getText().toString());
                }
                catch(Exception ex)
                {
                    Log.d("addfragment", "couldn't convert to double");
                }

                Expense expense = new Expense(name, category, date, amount);

                if(expense.isInvalid())
                {
                    Toast.makeText(getActivity(), getString(R.string.lbl_invalid_expense), Toast.LENGTH_SHORT).show();
                    return;
                }

                mActivity.addExpense(expense);
                mActivity.goBackToExpensesFragment();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mActivity.goBackToExpensesFragment();
            }
        });
    }

    public interface IAddFragment
    {
        void goBackToExpensesFragment();
        void addExpense(Expense expense);
    }
}
