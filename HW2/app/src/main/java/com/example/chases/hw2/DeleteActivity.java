package com.example.chases.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;

public class DeleteActivity extends AppCompatActivity
{

    private AlertDialog.Builder alertDialog;
    private EditText editName, editAmount, editDate;
    private Spinner spinnerCategory;
    private ImageView imageReceipt, imageCalendar;
    private Button buttonSelect, buttonDelete, buttonCancel;

    private Integer index;

    private ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        setTitle(getString(R.string.app_name_delete));

        setAllViews();

        Intent intent = getIntent();
        expenses = (ArrayList<Expense>) intent.getExtras().getSerializable(MainActivity.EXPENSE_KEY);

        if(expenses == null)
        {
            Log.e("editactivity", "expenses is null! :(");
            return;
        }

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.label_alert));

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setItems(getCharArray(expenses), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Expense toBeDeletedExpense = expenses.get(which);

                        if(toBeDeletedExpense != null)
                            updateViewsFromExpense(toBeDeletedExpense);

                        index = which;

                    }
                }).show();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == null)
                    return;

                Bundle bundle = new Bundle();

                Log.d("asdf", "inttt " + index);

                bundle.putInt(MainActivity.INTEGER_KEY, index);

                Intent intent = new Intent();
                intent.putExtra(MainActivity.BUNDLE_KEY, bundle);
                setResult(RESULT_OK, intent);

                finish();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void updateViewsFromExpense(Expense expense)
    {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(expense.getDate());
        editName.setText(expense.getName());
        editAmount.setText(expense.getAmount() + "");
        editDate.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
                cal.get(Calendar.YEAR));

        setSpinnerCategory(expense.getCategory());

        if(expense.getImage() != null)
        {
            Log.d("asdf", "image not null");
            imageReceipt.setImageURI(Uri.parse(expense.getImage()));
        }


        /*

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.MANAGE_DOCUMENTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.MANAGE_DOCUMENTS},
                    PERMISSION_REQUEST_MANAGE_DOCUMENTS);

            Log.d("asdf", "YEAHHHBABY");
        }
        else
        {
            Log.d("asdf", "NOPEEEE");
        }

        Uri uri = null;
        if(expense.getImage() != null)
            uri = Uri.parse(expense.getImage());

        setImageReceipt(uri);*/

    }

    private void setSpinnerCategory(String category)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);
        if(category != null)
        {
            int pos = adapter.getPosition(category);
            spinnerCategory.setSelection(pos);
        }
    }

    private CharSequence[] getCharArray(ArrayList<Expense> expenses)
    {
        CharSequence[] charArray = new CharSequence[expenses.size()];
        for(int i = 0; i < expenses.size(); i++)
        {
            charArray[i] = expenses.get(i).getName();
        }

        return charArray;
    }

    private void setAllViews()
    {
        editName        = (EditText)findViewById(R.id.editTextName);
        editAmount      = (EditText)findViewById(R.id.editTextAmount);
        editDate        = (EditText)findViewById(R.id.editTextDate);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        imageReceipt    = (ImageView)findViewById(R.id.imageViewBrowse);
        imageCalendar   = (ImageView)findViewById(R.id.imageViewDate);
        buttonSelect    = (Button)findViewById(R.id.buttonSelect);
        buttonDelete    = (Button)findViewById(R.id.buttonDelete);
        buttonCancel    = (Button)findViewById(R.id.buttonCancel);
    }
}
