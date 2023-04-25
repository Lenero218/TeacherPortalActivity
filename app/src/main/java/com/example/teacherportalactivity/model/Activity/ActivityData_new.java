package com.example.teacherportalactivity.model.Activity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityData_new {

    @SerializedName("activity_id")
    @Expose
    private Integer activityId;
    @SerializedName("period_id")
    @Expose
    private Integer periodId;
    @SerializedName("activity_title")
    @Expose
    private String activityTitle;
    @SerializedName("activity_subtitle")
    @Expose
    private String activitySubtitle;
    @SerializedName("activity_description")
    @Expose
    private String activityDescription;
    @SerializedName("activity_time")
    @Expose
    private String activityTime;
    @SerializedName("activity_status_list")
    @Expose
    private List<ActivityStatus> activityStatusList = null;
    @SerializedName("activity_status")
    @Expose
    private Integer activityStatus;
    @SerializedName("checkbox_appearance")
    @Expose
    private Integer checkboxAppearance;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_completed")
    @Expose
    private Integer isCompleted;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivitySubtitle() {
        return activitySubtitle;
    }

    public void setActivitySubtitle(String activitySubtitle) {
        this.activitySubtitle = activitySubtitle;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public List<ActivityStatus> getActivityStatusList() {
        return activityStatusList;
    }

    public void setActivityStatusList(List<ActivityStatus> activityStatusList) {
        this.activityStatusList = activityStatusList;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getCheckboxAppearance() {
        return checkboxAppearance;
    }

    public void setCheckboxAppearance(Integer checkboxAppearance) {
        this.checkboxAppearance = checkboxAppearance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

}