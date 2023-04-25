package com.example.teacherportalactivity.model.lessonsplanandperioddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeriodData {


    @SerializedName("period_id")
    @Expose
    private Integer periodId;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("period_name")
    @Expose
    private String periodName;
    @SerializedName("period_status")
    @Expose
    private Integer periodStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Integer getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(Integer periodStatus) {
        this.periodStatus = periodStatus;
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

}