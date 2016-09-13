/*
InClass03
EditActivity.java
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

public class EditActivity extends AppCompatActivity
{
    private EditText editName, editEmail;
    private RadioGroup rgDepartment;
    private SeekBar seekMood;
    private Button buttonSubmit;

    private String whatToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(getString(R.string.edit_app_name));

        setAllViews();
        setAllVisable();

        Intent receivedIntent = getIntent();
        whatToEdit = receivedIntent.getExtras().getString(DisplayActivity.TYPE_KEY);
        switch (whatToEdit)
        {
            case DisplayActivity.NAME_KEY:
                hideAllNonNameViews();
                break;
            case DisplayActivity.EMAIL_KEY:
                hideAllNonEmailViews();
                break;
            case DisplayActivity.DEPARTMENT_KEY:
                hideAllNonDepartmentViews();
                break;
            case DisplayActivity.MOOD_KEY:
                hideAllNonMoodViews();
                break;
        }

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (whatToEdit)
                {
                    case DisplayActivity.NAME_KEY:
                        sendNameBack();
                        break;
                    case DisplayActivity.EMAIL_KEY:
                        sendEmailBack();
                        break;
                    case DisplayActivity.DEPARTMENT_KEY:
                        sendDepartmentBack();
                        break;
                    case DisplayActivity.MOOD_KEY:
                        sendMoodBack();
                        break;
                }

                finish();
            }
        });
    }

    private void setAllVisable()
    {
        editName.setVisibility(View.VISIBLE);
        editEmail.setVisibility(View.VISIBLE);
        rgDepartment.setVisibility(View.VISIBLE);
        seekMood.setVisibility(View.VISIBLE);
    }

    private void sendMoodBack()
    {
        int mood = seekMood.getProgress();
        Intent intent = new Intent();
        intent.putExtra(DisplayActivity.MOOD_KEY, mood);
        setResult(RESULT_OK, intent);
    }

    private void hideAllNonMoodViews()
    {
        editName.setVisibility(View.INVISIBLE);
        editEmail.setVisibility(View.INVISIBLE);
        rgDepartment.setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewDepartment).setVisibility(View.INVISIBLE);
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

    private void sendDepartmentBack()
    {
        String department = getDepartmentString();
        if(department == null || department.length() == 0)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra(DisplayActivity.DEPARTMENT_KEY, department);
            setResult(RESULT_OK, intent);
        }
    }

    private void hideAllNonDepartmentViews()
    {
        editName.setVisibility(View.INVISIBLE);
        editEmail.setVisibility(View.INVISIBLE);
        seekMood.setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewMood).setVisibility(View.INVISIBLE);
    }

    private void sendEmailBack()
    {
        String email = editEmail.getText().toString();
        if(email == null || email.length() == 0)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra(DisplayActivity.EMAIL_KEY, email);
            setResult(RESULT_OK, intent);
        }
    }

    private void hideAllNonEmailViews()
    {
        editName.setVisibility(View.INVISIBLE);
        rgDepartment.setVisibility(View.INVISIBLE);
        seekMood.setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewDepartment).setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewMood).setVisibility(View.INVISIBLE);
    }

    private void setAllViews()
    {
        editName     = (EditText)findViewById(R.id.editTextName);
        editEmail    = (EditText)findViewById(R.id.editTextEmail);
        rgDepartment = (RadioGroup)findViewById(R.id.radioGroup);
        seekMood     = (SeekBar)findViewById(R.id.seekBarMood);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
    }

    private void hideAllNonNameViews()
    {
        editEmail.setVisibility(View.INVISIBLE);
        rgDepartment.setVisibility(View.INVISIBLE);
        seekMood.setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewDepartment).setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewMood).setVisibility(View.INVISIBLE);
    }

    private void sendNameBack()
    {
        String name = editName.getText().toString();
        if(name == null || name.length() == 0)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra(DisplayActivity.NAME_KEY, name);
            setResult(RESULT_OK, intent);
        }
    }
}
