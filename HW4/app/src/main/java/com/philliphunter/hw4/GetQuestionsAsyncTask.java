package com.philliphunter.hw4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetQuestionsAsyncTask extends AsyncTask<String, Void, ArrayList<Question>>
{
    IData activity;

    public GetQuestionsAsyncTask(IData activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        activity.setTriviaReady(false);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params)
    {
        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection _HttpURLConnection = (HttpURLConnection) url.openConnection();
            _HttpURLConnection.setRequestMethod("GET");
            _HttpURLConnection.connect();

            int statusCode = _HttpURLConnection.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader _BufferedReader = new BufferedReader(new InputStreamReader(_HttpURLConnection.getInputStream()));
                StringBuilder _StringBuilder = new StringBuilder();
                String currLine = _BufferedReader.readLine();

                while(currLine != null)
                {
                    _StringBuilder.append(currLine);
                    currLine = _BufferedReader.readLine();
                }

                return parseQuestions(_StringBuilder.toString());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    public static ArrayList<Question> parseQuestions(String jsonString) throws JSONException
    {
        ArrayList<Question> result = new ArrayList<Question>();

        JSONObject root = new JSONObject(jsonString);
        JSONArray questionsJson = root.getJSONArray("questions");

        for(int i = 0; i < questionsJson.length(); i++)
        {
            JSONObject currQuestionJSON = questionsJson.getJSONObject(i);
            Question currQuestion = new Question();

            currQuestion.setText(currQuestionJSON.getString("text"));
            currQuestion.setAnswer(currQuestionJSON.getJSONObject("choices").getInt("answer") - 1);

            if(currQuestionJSON.has("image"))
            {
                currQuestion.setImage(currQuestionJSON.getString("image"));
            }


            JSONArray currQuestionChoicesJSON = currQuestionJSON.getJSONObject("choices").getJSONArray("choice");
            String[] currQuestionChoices = new String[currQuestionChoicesJSON.length()];

            for(int j = 0; j < currQuestionChoices.length; j++)
            {
                currQuestionChoices[j] = currQuestionChoicesJSON.getString(j);
            }

            currQuestion.setChoices(currQuestionChoices);

            result.add(currQuestion);
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions)
    {
        super.onPostExecute(questions);
        activity.setTriviaReady(true);

        if(questions != null)
        {
            Log.d("hw4", questions.toString());
        }

        activity.setQuestions(questions);
    }

    public static interface IData
    {
        public void setTriviaReady(boolean ready);
        public void setQuestions(ArrayList<Question> questions);
    }
}
