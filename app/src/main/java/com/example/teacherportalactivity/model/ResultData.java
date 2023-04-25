package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResultData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("activity_data")
    @Expose
    private String activityData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityData() {
        return activityData;
    }

    public void setActivityData(String activityData) {
        this.activityData = activityData;
    }

}