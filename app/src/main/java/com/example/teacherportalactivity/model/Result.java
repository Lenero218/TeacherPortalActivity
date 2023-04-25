package com.example.teacherportalactivity.model;

public class Result {

    private String grade;
    private String subject;
    private String lessons;
    private String periods;
    private String activity;
    private String e5;
private String dodata;

    public String getE5() {
        return e5;
    }

    public void setE5(String e5) {
        this.e5 = e5;
    }

    public String getDodata() {
        return dodata;
    }

    public void setDodata(String dodata) {
        this.dodata = dodata;
    }

    public Result(String grade, String subject, String lessons, String periods, String e5, String dodata, String activity) {
        this.grade = grade;
        this.subject = subject;
        this.lessons = lessons;
        this.periods = periods;
        this.e5= e5;
        this.dodata = dodata;
        this.activity = activity;

    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLessons() {
        return lessons;
    }

    public void setLessons(String lessons) {
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