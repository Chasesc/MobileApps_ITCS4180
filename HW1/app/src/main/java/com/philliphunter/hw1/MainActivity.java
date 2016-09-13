/*
    Assignment:   Homework 1
    File:         MainActivity.java
    Participants: Phillip Hunter, Chase Schelthoff
 */
package com.philliphunter.hw1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    enum BACStatus { SAFE, CAREFUL, OVER_LIMIT }

    private TextView statusIndicator;
    private EditText txtWeight;
    private SeekBar seekAlcoholPercent;
    private TextView BACIndicator;
    private TextView lblAlcoholPercentIndicator;
    private View btnSave;
    private ProgressBar progBAC;

    private float weight;
    private boolean male;
    private int drinkSize = 1;
    private float alcoholPercent = 0.05f;
    private float bloodAlcoholLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusIndicator = (TextView)(findViewById(R.id.lblStatusIndicator));
        txtWeight = ((EditText)findViewById(R.id.txtWeight));
        seekAlcoholPercent = ((SeekBar)findViewById(R.id.seekAlcoholPercent));
        BACIndicator = ((TextView)(findViewById(R.id.lblBAC)));
        BACIndicator.setText(getString(R.string.label_bac_level) + " 0.00");
        lblAlcoholPercentIndicator = ((TextView)(findViewById(R.id.lblAlcoholPercentIndicator)));
        lblAlcoholPercentIndicator.setText((alcoholPercent * 100) + "%");
        btnSave = findViewById(R.id.btnSave);
        progBAC = (ProgressBar)findViewById(R.id.progBAC);

        setStatus(BACStatus.SAFE);

        seekAlcoholPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                alcoholPercent = (progress * 5) / 100.0f;
                lblAlcoholPercentIndicator.setText((progress * 5) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    weight = Float.parseFloat(txtWeight.getText().toString());
                    male = ((Switch) findViewById(R.id.switchSex)).isChecked();
                } catch (Exception e)
                {
                    txtWeight.setError(getString(R.string.weight_invalid));
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnSave.getWindowToken(), 0);
            }
        });

        ((RadioGroup)findViewById(R.id.radGroupSize)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.rad1oz:
                        drinkSize = 1;
                        break;
                    case R.id.rad5oz:
                        drinkSize = 5;
                        break;
                    case R.id.rad12oz:
                        drinkSize = 12;
                        break;
                }
            }
        });

        findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtWeight.setText("");
                ((RadioButton)findViewById(R.id.rad1oz)).setChecked(true);
                ((Switch)findViewById(R.id.switchSex)).setChecked(false);
                seekAlcoholPercent.setProgress(1);
                weight = 0;
                male = false;
                bloodAlcoholLevel = 0.0f;
                BACIndicator.setText(getString(R.string.label_bac_level) + " 0.00");
                setStatus(BACStatus.SAFE);
                setEverythingEnabled(true);
                progBAC.setProgress(0);
            }
        });

        findViewById(R.id.btnAddDrink).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(weight == 0)
                {
                    txtWeight.setError(getString(R.string.weight_invalid));
                }
                else
                {
                    bloodAlcoholLevel += calculateBAC();
                    BACIndicator.setText(getString(R.string.label_bac_level) + " " + String.format("%.2f", bloodAlcoholLevel));
                    progBAC.setProgress((int)Math.ceil(bloodAlcoholLevel * 100.0f));

                    if (bloodAlcoholLevel <= 0.08f)
                    {
                        setStatus(BACStatus.SAFE);
                    } else if (bloodAlcoholLevel <= 0.20f)
                    {
                        setStatus(BACStatus.CAREFUL);
                    } else
                    {
                        setStatus(BACStatus.OVER_LIMIT);
                    }

                    if(bloodAlcoholLevel >= 0.25f)
                    {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_more_drinks), Toast.LENGTH_LONG).show();
                        setEverythingEnabled(false);
                    }
                }
            }
        });
    }

    private void setEverythingEnabled(boolean enabled)
    {
        btnSave.setEnabled(enabled);
        findViewById(R.id.btnAddDrink).setEnabled(enabled);
        findViewById(R.id.switchSex).setEnabled(enabled);
        txtWeight.setEnabled(enabled);
        findViewById(R.id.rad1oz).setEnabled(enabled);
        findViewById(R.id.rad5oz).setEnabled(enabled);
        findViewById(R.id.rad12oz).setEnabled(enabled);
        seekAlcoholPercent.setEnabled(enabled);
    }


    private float calculateBAC()
    {
        return (((drinkSize * alcoholPercent) * 6.24f) / (weight * (male ? 0.68f : 0.55f)));
    }

    private void setStatus(BACStatus status)
    {
        switch(status)
        {
            case SAFE:
                statusIndicator.setBackgroundColor(Color.rgb(0,255,0));
                statusIndicator.setText(R.string.label_status_safe);
                break;
            case CAREFUL:
                statusIndicator.setBackgroundColor(Color.rgb(255, 255, 0));
                statusIndicator.setText(R.string.label_status_careful);
                break;
            case OVER_LIMIT:
                statusIndicator.setBackgroundColor(Color.rgb(255, 0, 0));
                statusIndicator.setText(R.string.label_status_over_limit);
                break;
        }
    }
}
