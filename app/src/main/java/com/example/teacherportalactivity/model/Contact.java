package com.example.teacherportalactivity.model;

public class Contact {
    String grade;
    String subject;
    String lessons;
    String periods;
    String activity;


    public Contact(String grade, String subject, String lessons,String periods, String activity){

        this.grade = grade;
        this.subject = subject;
        this.lessons = lessons;
        this.periods= periods;
        this.activity=activity;
    }

    public Contact() {

    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getSubject(){
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getLessons(){
        return this.lessons;
    }

    public void setLessons(String lessons){
        this.lessons = lessons;
    }
    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }



}