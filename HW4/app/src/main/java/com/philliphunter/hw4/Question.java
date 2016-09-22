package com.philliphunter.hw4;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable
{
    private String text;
    private String image;
    private String[] choices;
    private int answer;

    public Question() {}

    public Question(String text, String image, String[] choices, int answer)
    {
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.answer = answer;
    }

    public String getText()
    {
        return text;
    }

    public String getImage()
    {
        return image;
    }

    public String[] getChoices()
    {
        return choices;
    }

    public int getAnswer()
    {
        return answer;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setChoices(String[] choices)
    {
        this.choices = choices;
    }

    public void setAnswer(int answer)
    {
        this.answer = answer;
    }

    @Override
    public String toString()
    {
        return "Question{" +
                "text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices=" + Arrays.toString(choices) +
                ", answer=" + answer +
                '}';
    }
}
