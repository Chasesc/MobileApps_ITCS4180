/*
InClass08
Chase Schelthoff
 */

package com.example.inclass08;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment
{
    private IShowFragment mActivity;
    private TextView textName, textAmount, textCategory, textDate;
    private Button buttonClose;

    private Expense expense;

    public ShowFragment()
    {
        // Required empty public constructor
    }

    // This is a workaround for a bug.
    public ShowFragment(Expense expense)
    {
        this.expense = expense;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof IShowFragment)
            mActivity = (IShowFragment)context;
        else
            throw new RuntimeException("IShowFragment not implemented in context!");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setAllViews();

        setExpenseData(this.expense);

        buttonClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mActivity.goBack();
            }
        });
    }

    public void setExpenseData(Expense expense)
    {
        textName.setText(expense.getName());
        textCategory.setText(expense.getCategory());
        textAmount.setText("$" + expense.getAmount());
        textDate.setText(expense.getDate());
    }

    private void setAllViews()
    {
        textName = (TextView) getActivity().findViewById(R.id.textShowName);
        textAmount = (TextView) getActivity().findViewById(R.id.textShowAmount);
        textCategory = (TextView) getActivity().findViewById(R.id.textShowCategory);
        textDate = (TextView) getActivity().findViewById(R.id.textShowDate);

        buttonClose = (Button) getActivity().findViewById(R.id.buttonClose);
    }

    public interface IShowFragment
    {
        void goBack();
    }
}
