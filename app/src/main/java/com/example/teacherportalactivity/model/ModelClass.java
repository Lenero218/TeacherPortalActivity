package com.example.teacherportalactivity.model;

import java.util.ArrayList;

public class ModelClass {
    String title;
    String subtitle;
    String description;
    String time;
    String status1;
    String status;
    String color_code;
    boolean isvisible;
    boolean checkbox;




    public ModelClass(String title, String subtitle, String description, String time, String status1, String status, String color_code, boolean isvisible, boolean checkbox) {
        this.description = description;
        this.subtitle= subtitle;
        this.title = title;
        this.time = time;
        this.status1 = status1;
        this.status = status;
        this.color_code= color_code;
        this.isvisible = isvisible;
        this.checkbox = checkbox;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus1() {
        return status1;
    }
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    public boolean getcheckbox() {
        return checkbox;
    }

    public void setcheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }


}
