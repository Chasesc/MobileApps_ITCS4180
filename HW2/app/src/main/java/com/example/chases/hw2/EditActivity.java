package com.example.chases.hw2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.jar.Manifest;

public class EditActivity extends AppCompatActivity
{
    private AlertDialog.Builder alertDialog;
    private EditText editName, editAmount, editDate;
    private Spinner spinnerCategory;
    private ImageView imageReceipt, imageCalendar;
    private Button buttonSelect, buttonSave, buttonCancel;
    private Expense editingExpense;

    private Integer index;
    int dateMonth, dateDay, dateYear;
    private Uri imgUri;
    private String imgString;

    private boolean anyError;
    private boolean dateChanged = false;
    private ArrayList<Expense> expenses;

    private static final int PERMISSION_REQUEST_MANAGE_DOCUMENTS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(getString(R.string.app_name_edit));

        setAllViews();

        Intent intent = getIntent();
        expenses = (ArrayList<Expense>) intent.getExtras().getSerializable(MainActivity.EXPENSE_KEY);

        if(expenses == null)
        {
            Log.e("editactivity", "expenses is null! :(");
            return;
        }

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                dateChanged = true;
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,
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

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.label_alert));

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setItems(getCharArray(expenses), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editingExpense = expenses.get(which);

                        if(editingExpense != null)
                            updateViewsFromExpense(editingExpense);

                        index = which;

                    }
                }).show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyError = false;

                String name = getName();
                double amount = getAmount();
                String category = spinnerCategory.getSelectedItem().toString();

                Date date;
                if(dateChanged)
                    date = getDate();
                else
                    date = editingExpense.getDate();

                //We don't want to continue if the user had any bad input.
                if(anyError)
                    return;

                Expense expense = new Expense(name, category, amount, date,
                        imgUri != null ? imgUri.toString() : null);

                Bundle bundle = new Bundle();

                bundle.putInt(MainActivity.INTEGER_KEY, index);
                bundle.putSerializable(MainActivity.EXPENSE_KEY, expense);

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_MANAGE_DOCUMENTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("editactivity", "GRANTED!!!!!!");
                    if(imgString != null)
                    {
                        Log.d("editactivity", imgString);
                        imageReceipt.setImageURI(Uri.parse(imgString));
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
        
        int permessionCheck = ContextCompat.checkSelfPermission(EditActivity.this,
                android.Manifest.permission.MANAGE_DOCUMENTS);

        if(permessionCheck != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("asdf", "no per: " + permessionCheck);

            if(ActivityCompat.shouldShowRequestPermissionRationale(EditActivity.this,
                    android.Manifest.permission.MANAGE_DOCUMENTS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(EditActivity.this,
                        new String[] {android.Manifest.permission.MANAGE_DOCUMENTS},
                        PERMISSION_REQUEST_MANAGE_DOCUMENTS);
            }
        }

        imgString = expense.getImage();

    }

    private void setImageReceipt(Uri uri)
    {
        Drawable receiptImage;
        try
        {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            receiptImage = Drawable.createFromStream(inputStream, uri.toString());
        }
        catch (FileNotFoundException e)
        {
            receiptImage = getResources().getDrawable(R.drawable.browse_64);
        }
        imageReceipt.setImageDrawable(receiptImage);
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
        buttonSave      = (Button)findViewById(R.id.buttonSave);
        buttonCancel    = (Button)findViewById(R.id.buttonCancel);
    }
}
