/*
InClass03
DisplayActivity.java
Chase Schelthoff and Phillip Hunter
 */

package com.example.chases.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity
{
    public static final String TYPE_KEY = "TYPE";
    public static final String NAME_KEY = "NAME";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String DEPARTMENT_KEY = "DEPARTMENT";
    public static final String MOOD_KEY = "MOOD";

    public static final int NAME_CODE = 100;
    public static final int EMAIL_CODE = 101;
    public static final int DEPARTMENT_CODE = 102;
    public static final int MOOD_CODE = 103;

    private TextView tvName, tvEmail, tvDepartment, tvMood;
    private TextView tvDisplayName, tvDisplayEmail, tvDisplayDepart, tvDisplayMood;
    private ImageView imgName, imgEmail, imgDepartment, imgMood;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle(getString(R.string.display_app_name));

        setViews();
        fixNames();
        setStudent();
        displayStudentInfo();

        imgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.chases.inclass03.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(TYPE_KEY, NAME_KEY);
                startActivityForResult(intent, NAME_CODE);
            }
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.chases.inclass03.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(TYPE_KEY, EMAIL_KEY);
                startActivityForResult(intent, EMAIL_CODE);
            }
        });

        imgDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.chases.inclass03.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(TYPE_KEY, DEPARTMENT_KEY);
                startActivityForResult(intent, DEPARTMENT_CODE);
            }
        });

        imgMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.chases.inclass03.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(TYPE_KEY, MOOD_KEY);
                startActivityForResult(intent, MOOD_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NAME_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                String name = data.getExtras().getString(NAME_KEY);
                if(name != null)
                    tvDisplayName.setText(name);
            }
        }
        else if(requestCode == EMAIL_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                String email = data.getExtras().getString(EMAIL_KEY);
                if(email != null)
                    tvDisplayEmail.setText(email);
            }
        }
        else if(requestCode == DEPARTMENT_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                String department = data.getExtras().getString(DEPARTMENT_KEY);
                if(department != null)
                    tvDisplayDepart.setText(department);
            }
        }
        else if(requestCode == MOOD_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                int mood = data.getExtras().getInt(MOOD_KEY);
                tvDisplayMood.setText(mood + "");
            }
        }
    }

    private void displayStudentInfo()
    {
        if(student == null)
            return;

        tvDisplayName.setText(student.getName());
        tvDisplayEmail.setText(student.getEmail());
        tvDisplayDepart.setText(student.getDepartment());
        tvDisplayMood.setText(student.getMood() + "");
    }

    private void fixNames()
    {
        tvName.setText(getString(R.string.label_name) + ":");
        tvEmail.setText(getString(R.string.label_email) + ":");
        tvDepartment.setText(getString(R.string.label_department) + ":");
        tvMood.setText(getString(R.string.label_mood_short) + ":");
    }

    private void setViews()
    {
        tvName = (TextView)findViewById(R.id.textViewName);
        tvEmail = (TextView)findViewById(R.id.textViewEmail);
        tvDepartment = (TextView)findViewById(R.id.textViewDepart);
        tvMood = (TextView)findViewById(R.id.textViewMood);

        tvDisplayName = (TextView)findViewById(R.id.textViewNameDisplay);
        tvDisplayEmail = (TextView)findViewById(R.id.textViewEmailDisplay);
        tvDisplayDepart = (TextView)findViewById(R.id.textViewDepartDisplay);
        tvDisplayMood = (TextView)findViewById(R.id.textViewMoodDisplay);

        imgName = (ImageView)findViewById(R.id.imageViewName);
        imgEmail = (ImageView)findViewById(R.id.imageViewEmail);
        imgDepartment = (ImageView)findViewById(R.id.imageViewDepart);
        imgMood = (ImageView)findViewById(R.id.imageViewMood);
    }

    private void setStudent()
    {
        Intent receivedIntent = getIntent();
        if(receivedIntent == null)
            return;

        student = (Student)receivedIntent.getExtras().getSerializable(MainActivity.STUDENT_KEY);
    }
}
