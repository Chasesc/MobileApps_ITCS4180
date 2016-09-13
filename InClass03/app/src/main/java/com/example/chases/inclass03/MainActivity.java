/*
InClass03
MainActivity.java
Chase Schelthoff and Phillip Hunter
 */

package com.example.chases.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    public static final String STUDENT_KEY = "STUDENT";

    private EditText editName, editEmail;
    private RadioGroup rgDepartment;
    private SeekBar seekMood;
    private Button buttonSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllViews();

        ((TextView)findViewById(R.id.textViewDepartment)).setText(getString(R.string.label_department) + ':');

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String department = getDepartmentString();
                int mood = seekMood.getProgress();

                //Alert the user to bad input.  Also return so we don't continue with bad information.
                if(alertToBadInput(name, email, department))
                    return;

                Student student = new Student(name, email, department, mood);

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(STUDENT_KEY, student);
                startActivity(intent);
            }
        });
    }

    private boolean alertToBadInput(String name, String email, String department)
    {
        boolean anyBadInput = false;
        if(name.equals(""))
        {
            Toast.makeText(MainActivity.this, "No name entered!", Toast.LENGTH_SHORT).show();
            anyBadInput = true;
        }
        if(email.equals(""))
        {
            Toast.makeText(MainActivity.this, "No email entered!", Toast.LENGTH_SHORT).show();
            anyBadInput = true;
        }
        if(department == null)
        {
            Toast.makeText(MainActivity.this, "No RadioButton is checked!", Toast.LENGTH_SHORT).show();
            anyBadInput = true;
        }

        return anyBadInput;
    }

    private String getDepartmentString()
    {
        switch(rgDepartment.getCheckedRadioButtonId())
        {
            case R.id.radioButtonSIS:   return getString(R.string.label_SIS);
            case R.id.radioButtonCS:    return getString(R.string.label_CS);
            case R.id.radioButtonBIO:   return getString(R.string.label_BIO);
            case R.id.radioButtonOther: return getString(R.string.label_others);
            default: return null;
        }
    }

    private void setAllViews()
    {
        editName     = (EditText)findViewById(R.id.editTextName);
        editEmail    = (EditText)findViewById(R.id.editTextEmail);
        rgDepartment = (RadioGroup)findViewById(R.id.radioGroup);
        seekMood     = (SeekBar)findViewById(R.id.seekBarMood);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
    }
}
