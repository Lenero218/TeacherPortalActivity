package com.example.teacherportalactivity.model;

/**
 * Created by Saurabh Nanda on 12-01-2023.
 * Ratna Sagar LTD
 * snanda@ratnasagar.com
 */
public class PostResult {
    private String activityId;
    private String isCompleted;

    public PostResult(String activityId, String isCompleted) {
        this.activityId = activityId;
        this.isCompleted = isCompleted;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }
}
