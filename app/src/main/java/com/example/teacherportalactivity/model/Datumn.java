package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saurabh Nanda on 23-01-2023.
 * Ratna Sagar LTD
 * snanda@ratnasagar.com
 */


public class Datumn {

    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("period_id")
    @Expose
    private Integer periodId;
    @SerializedName("total_completed_activity")
    @Expose
    private Integer totalCompletedActivity;
    @SerializedName("activity_id_list")
    @Expose
    private String activityIdList;
    @SerializedName("total_activity")
    @Expose
    private Integer totalActivity;
    @SerializedName("no_of_e5s")
    @Expose
    private Integer noOfE5s;
    @SerializedName("no_of_could_do")
    @Expose
    private Integer noOfCouldDo;
    @SerializedName("no_of_should_do")
    @Expose
    private Integer noOfShouldDo;
    @SerializedName("no_of_must_do")
    @Expose
    private Integer noOfMustDo;
    @SerializedName("period_name")
    @Expose
    private String periodName;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getTotalCompletedActivity() {
        return totalCompletedActivity;
    }

    public void setTotalCompletedActivity(Integer totalCompletedActivity) {
        this.totalCompletedActivity = totalCompletedActivity;
    }

    public String getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(String activityIdList) {
        this.activityIdList = activityIdList;
    }

    public Integer getTotalActivity() {
        return totalActivity;
    }

    public void setTotalActivity(Integer totalActivity) {
        this.totalActivity = totalActivity;
    }

    public Integer getNoOfE5s() {
        return noOfE5s;
    }

    public void setNoOfE5s(Integer noOfE5s) {
        this.noOfE5s = noOfE5s;
    }

    public Integer getNoOfCouldDo() {
        return noOfCouldDo;
    }

    public void setNoOfCouldDo(Integer noOfCouldDo) {
        this.noOfCouldDo = noOfCouldDo;
    }

    public Integer getNoOfShouldDo() {
        return noOfShouldDo;
    }

    public void setNoOfShouldDo(Integer noOfShouldDo) {
        this.noOfShouldDo = noOfShouldDo;
    }

    public Integer getNoOfMustDo() {
        return noOfMustDo;
    }

    public void setNoOfMustDo(Integer noOfMustDo) {
        this.noOfMustDo = noOfMustDo;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
