/*
In Class Assignment 2b
MainActivity.java
Chase Schelthoff and Phillip Hunter
 */

package com.example.inclass2b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int currentSelectedID;
    private TextView outputText;
    private EditText firstLength, secondLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstLength  = (EditText)findViewById(R.id.numberLength1);
        secondLength = (EditText)findViewById(R.id.numberLength2);
        outputText   = (TextView)findViewById(R.id.textViewOutput);


        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double first = getDoubleFromEditText(firstLength);
                double second = getDoubleFromEditText(secondLength);
                boolean clear = false, useSecond = false;

                double area = 0.0;

                switch(currentSelectedID)
                {
                    case R.id.radioTriangle:
                        area = 0.5 * first * second;
                        useSecond = true;
                        break;
                    case R.id.radioSquare:
                        area = first * first;
                        secondLength.setText("");
                        break;
                    case R.id.radioCircle:
                        area = Math.PI * first * first;
                        secondLength.setText("");
                        break;
                    case R.id.radioRectangle:
                        area = first * second;
                        useSecond = true;
                        break;
                    case R.id.radioClear:
                        clear = true;
                }

                outputText.setText(String.format("%.2f", area));

                reportIfEmpty(firstLength);

                if(useSecond)
                    reportIfEmpty(secondLength);

                if(clear)
                {
                    firstLength.setText("");
                    secondLength.setText("");
                    outputText.setText("");
                }

            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentSelectedID = checkedId;
            }
        });
    }

    private double getDoubleFromEditText(EditText editText)
    {
        double value = 0.0;
        try
        {
            value = Double.parseDouble(editText.getText().toString());
        }
        catch(Exception ex)
        {
            //This can't happen because of the Number (Decimal) text field will not allow any
            //non real number.
        }

        return value;
    }

    private void reportIfEmpty(EditText editText)
    {
        boolean isEmpty = editText.getText().toString().isEmpty();
        String errorMessage = new String();

        if(editText.getId() ==  R.id.numberLength1)
            errorMessage = getText(R.string.errorEmpty1).toString();
        else if(editText.getId() == R.id.numberLength2)
            errorMessage = getText(R.string.errorEmpty2).toString();

        if(isEmpty)
        {
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            outputText.setText("");
        }

    }


}
