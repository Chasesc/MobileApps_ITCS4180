/*
InClass03
Student.java
Chase Schelthoff and Phillip Hunter
 */

package com.example.chases.inclass03;

import java.io.Serializable;

public class Student implements Serializable
{
    private String name, email, department;
    private int mood;

    public Student(String name, String email, String department, int mood)
    {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    public String getEmail()
    {
        return email;
    }

    public String getDepartment()
    {
        return department;
    }

    public int getMood()
    {
        return mood;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood=" + mood +
                '}';
    }
}
