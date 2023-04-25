package com.example.teacherportalactivity.model;

public class ActivityData {

    private String subject;
    private String lessons;
    private String periods;
    private String title;
    private String subtitle;
    private String description;
    private String category;
    private String category1;
    private String category2;
    private String time;
    private String status;
    private Boolean isvisible;


    public ActivityData(String subject, String lessons, String periods, String title, String subtitle, String description, String category, String category1, String category2, String time, String status) {

        this.subject = subject;
        this.lessons = lessons;
        this.periods = periods;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.category1 = category1;
        this.category2 = category2;
        this.time = time;
        this.status = status;

    }

    public Boolean getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Boolean isvisible) {
        this.isvisible = isvisible;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory1() {
        return category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}