package com.example.teacherportalactivity.model;

public class StarryKnight {


    private String semester;
    private String subject;
    private String theme;



    private String lessons;
    private String starryknighttext;
    private String starryknight;

    public String getStarryknighttext() {
        return starryknighttext;
    }

    public void setStarryknighttext(String starryknighttext) {
        this.starryknighttext = starryknighttext;
    }
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLessons() {
        return lessons;
    }

    public void setLessons(String lessons) {
        this.lessons = lessons;
    }

    public String getStarryknight() {
        return starryknight;
    }

    public void setStarryknight(String starryknight) {
        this.starryknight = starryknight;
    }

    public StarryKnight(String semester, String subject, String theme, String lessons, String starryknighttext,String starryknight) {

        this.semester = semester;
        this.subject = subject;
        this.theme = theme;
        this.lessons = lessons;
        this.starryknighttext= starryknighttext;
        this.starryknight = starryknight;


    }


}