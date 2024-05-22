package com.csc340.restapidemo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

public class Student {
    @JsonView(Views.Public.class)
    private int id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String major;
    @JsonView(Views.Public.class)
    private double gpa;

    public Student(int id, String name, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String out = "ID: " + this.id + ", Name: " + this.name + ", Major: " + this.major + ", GPA: " + this.gpa;
        return out;
    }
}

