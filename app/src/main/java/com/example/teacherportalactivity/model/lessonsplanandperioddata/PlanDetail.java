package com.example.teacherportalactivity.model.lessonsplanandperioddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanDetail {

    @SerializedName("lesson_plan_id")
    @Expose
    private Integer lessonPlanId;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("lesson_plan_name")
    @Expose
    private String lessonPlanName;
    @SerializedName("lesson_plan_title")
    @Expose
    private String lessonPlanTitle;
    @SerializedName("lesson_plan_description")
    @Expose
    private String lessonPlanDescription;
    @SerializedName("lesson_status")
    @Expose
    private Integer lessonStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

/*    public String getTeachingperiod() {
        return teachingperiod;
    }

    public void setTeachingperiod(String teachingperiod) {
        this.teachingperiod = teachingperiod;
    }*/

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

  /*  private String teachingperiod;*/

    public Integer getLessonPlanId() {
        return lessonPlanId;
    }

    public void setLessonPlanId(Integer lessonPlanId) {
        this.lessonPlanId = lessonPlanId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonPlanName() {
        return lessonPlanName;
    }

    public String getLessonPlanDescription() {
        return lessonPlanDescription;
    }

    public void setLessonPlanDescription(String lessonPlanDescription) {
        this.lessonPlanDescription = lessonPlanDescription;
    }

    public void setLessonPlanName(String lessonPlanName) {
        this.lessonPlanName = lessonPlanName;
    }

    public String getLessonPlanTitle() {
        return lessonPlanTitle;
    }

    public void setLessonPlanTitle(String lessonPlanTitle) {
        this.lessonPlanTitle = lessonPlanTitle;
    }



    public Integer getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(Integer lessonStatus) {
        this.lessonStatus = lessonStatus;
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