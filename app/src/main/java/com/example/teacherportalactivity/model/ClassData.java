package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassData {

    @SerializedName("grade")
    @Expose
    private String sclass;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
