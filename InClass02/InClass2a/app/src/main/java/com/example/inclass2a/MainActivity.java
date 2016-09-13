/*
In class 02 a
MainActivity.java
Chase Schelthoff and Phillip Hunter
*/

package com.example.inclass2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstLength, secondLength;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstLength = (EditText)findViewById(R.id.numberLength1);
        secondLength = (EditText)findViewById(R.id.numberLength2);
        outputText = (TextView)findViewById(R.id.textViewOutput);

        findViewById(R.id.buttonTriangle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double first = getDoubleFromEditText(firstLength);
                double second = getDoubleFromEditText(secondLength);

                double area = 0.5 * first * second;

                outputText.setText(String.format("%.2f", area));

                reportIfEmpty(firstLength);
                reportIfEmpty(secondLength);
            }
        });

        findViewById(R.id.buttonSquare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double first = getDoubleFromEditText(firstLength);
                secondLength.setText("");

                double area =  first * first;
                outputText.setText(String.format("%.2f", area));

                reportIfEmpty(firstLength);
            }
        });

        findViewById(R.id.buttonCircle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double first = getDoubleFromEditText(firstLength);
                secondLength.setText("");

                double area = Math.PI * first * first;
                outputText.setText(String.format("%.2f", area));

                reportIfEmpty(firstLength);
            }
        });

        findViewById(R.id.buttonRectangle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double first = getDoubleFromEditText(firstLength);
                double second = getDoubleFromEditText(secondLength);

                double area = first * second;

                outputText.setText(String.format("%.2f", area));

                reportIfEmpty(firstLength);
                reportIfEmpty(secondLength);
            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLength.setText("");
                secondLength.setText("");
                outputText.setText("");
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
