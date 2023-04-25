package com.example.teacherportalactivity.model.LessonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonListData {

    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("theme_id")
    @Expose
    private Integer themeId;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("lesson_chapter_name")
    @Expose
    private String lessonChapterName;
    @SerializedName("lesson_status")
    @Expose
    private Integer lessonStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("percentage")
    @Expose
    private Float percentage;

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonChapterName() {
        return lessonChapterName;
    }

    public void setLessonChapterName(String lessonChapterName) {
        this.lessonChapterName = lessonChapterName;
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

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}