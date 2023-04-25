package com.example.teacherportalactivity.model;

public class CountData {

    private String subject;
    private String lessons;
    private String periods;
    private String mustdocount;
    private String coulddocount;
    private String shoulddocount;
    private String e5count;


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

    public String getMustdocount() {
        return mustdocount;
    }

    public void setMustdocount(String mustdocount) {
        this.mustdocount = mustdocount;
    }

    public String getShoulddocount() {
        return shoulddocount;
    }

    public void setShoulddocount(String shoulddocount) {
        this.shoulddocount = shoulddocount;
    }

    public String getCoulddocount() {
        return coulddocount;
    }

    public void setCoulddocount(String coulddocount) {
        this.coulddocount = coulddocount;
    }

    public String getE5count() {
        return e5count;
    }

    public void setE5count(String e5count) {
        this.e5count = e5count;
    }

    public CountData(String subject, String lessons, String periods, String mustdocount, String coulddocount, String shoulddocount, String e5count) {

        this.subject = subject;
        this.lessons = lessons;
        this.periods = periods;
        this.mustdocount = mustdocount;
        this.coulddocount = coulddocount;
        this.shoulddocount = shoulddocount;
        this.e5count = e5count;


    }
}