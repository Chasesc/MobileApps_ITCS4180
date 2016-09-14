package com.example.chases.hw2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddActivity extends AppCompatActivity
{
    private static final int SELECT_IMAGE = 100;

    private EditText editName, editAmount, editDate;
    private Spinner spinnerCategory;
    private ImageView imageReceipt, imageCalendar;
    private Button buttonAdd;
    private boolean anyError = false;

    int dateMonth, dateDay, dateYear;

    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle(getString(R.string.app_name_add));

        setAllViews();

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                dateYear = selectedYear;
                dateMonth = selectedMonth;
                dateDay = selectedDay;
                editDate.setText((dateMonth + 1) + "/" + dateDay + "/" + dateYear);
            }
        };

        imageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
                        R.style.AppTheme,
                        datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setCancelable(false);
                datePickerDialog.setTitle(getString(R.string.label_select_date));
                datePickerDialog.show();
            }
        });

        imageReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_image)),
                        SELECT_IMAGE);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyError = false;

                String name = getName();
                double amount = getAmount();
                String category = spinnerCategory.getSelectedItem().toString();
                Date date = getDate();

                //We don't want to continue if the user had any bad input.
                if(anyError)
                    return;

                Expense expense = new Expense(name, category, amount, date,
                        imgUri != null ? imgUri.toString() : null);

                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXPENSE_KEY, expense);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK && requestCode == SELECT_IMAGE)
        {
            imgUri = data.getData();
            if(imgUri != null)
                imageReceipt.setImageURI(imgUri);
        }
    }

    private Date getDate()
    {
        if(dateMonth == 0 || dateDay == 0 || dateYear == 0)
        {
            anyError = true;
            editDate.setError(getString(R.string.label_date_error));
            return null;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, dateYear);
        calendar.set(Calendar.MONTH, dateMonth);
        calendar.set(Calendar.DAY_OF_MONTH, dateDay);

        return calendar.getTime();
    }

    private String getName()
    {
        String name = editName.getText().toString();

        if(name == null || name.isEmpty())
        {
            editName.setError(getString(R.string.label_name_error));
            anyError = true;
        }

        return name;
    }

    private double getAmount()
    {
        double amount = 0.0;

        try
        {
            amount = Double.parseDouble(editAmount.getText().toString());
        }
        catch(Exception ex)
        {
            editAmount.setError(getString(R.string.label_amount_error));
            anyError = true;
        }

        return amount;
    }

    private void setAllViews()
    {
        editName        = (EditText)findViewById(R.id.editTextName);
        editAmount      = (EditText)findViewById(R.id.editTextAmount);
        editDate        = (EditText)findViewById(R.id.editTextDate);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        imageReceipt    = (ImageView)findViewById(R.id.imageViewBrowse);
        imageCalendar   = (ImageView)findViewById(R.id.imageViewDate);
        buttonAdd       = (Button)findViewById(R.id.buttonAddExpense);

    }
}
