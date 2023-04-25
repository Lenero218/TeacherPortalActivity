package com.example.teacherportalactivity.model.Theme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThemeData {

    @SerializedName("theme_id")
    @Expose
    private Integer themeId;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("theme_name")
    @Expose
    private String themeName;
    @SerializedName("theme_title")
    @Expose
    private String themeTitle;
    @SerializedName("section_status")
    @Expose
    private Integer sectionStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeTitle() {
        return themeTitle;
    }

    public void setThemeTitle(String themeTitle) {
        this.themeTitle = themeTitle;
    }

    public Integer getSectionStatus() {
        return sectionStatus;
    }

    public void setSectionStatus(Integer sectionStatus) {
        this.sectionStatus = sectionStatus;
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