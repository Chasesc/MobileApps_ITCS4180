package com.philliphunter.hw4;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetQuestionsAsyncTask.IData
{
    private final String TRIVIA_URL = "http://dev.theappsdr.com/apis/trivia_json/index.php";
    private ArrayList<Question> questions = new ArrayList<Question>();

    ProgressDialog _ProgressDialog;

    private ImageView imageTrivia;
    private TextView lblTriviaLoadStatus;
    private Button btnStartTrivia;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupListeners();

        new GetQuestionsAsyncTask(this).execute(TRIVIA_URL);
    }

    private void setupViews()
    {
        imageTrivia = (ImageView)findViewById(R.id.imageTrivia);
        lblTriviaLoadStatus = (TextView)findViewById(R.id.lblTriviaLoadStatus);
        btnStartTrivia = (Button)findViewById(R.id.btnStartTrivia);
        btnExit = (Button)findViewById(R.id.btnExit);
    }

    private void setupListeners()
    {
        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void setTriviaReady(boolean ready)
    {
        if(ready)
        {
            _ProgressDialog.dismiss();
            imageTrivia.setVisibility(View.VISIBLE);
            lblTriviaLoadStatus.setText(getString(R.string.label_trivia_ready));
            btnStartTrivia.setEnabled(true);
        }
        else
        {
            _ProgressDialog = new ProgressDialog(this);
            _ProgressDialog.setCancelable(false);
            _ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            _ProgressDialog.show();
            imageTrivia.setVisibility(View.INVISIBLE);
            lblTriviaLoadStatus.setText(getString(R.string.label_loading_trivia));
            btnStartTrivia.setEnabled(false);
        }
    }

    @Override
    public void setQuestions(ArrayList<Question> questions)
    {
        this.questions = questions;
    }
}
