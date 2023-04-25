package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectionSectionData {

    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("section_icon")
    @Expose
    private String sectionIcon;
    @SerializedName("section_url")
    @Expose
    private String sectionUrl;
    @SerializedName("sections_icon")
    @Expose
    private String sectionsIcon;
    @SerializedName("section_status")
    @Expose
    private Integer sectionStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionIcon() {
        return sectionIcon;
    }

    public void setSectionIcon(String sectionIcon) {
        this.sectionIcon = sectionIcon;
    }

    public String getSectionUrl() {
        return sectionUrl;
    }

    public void setSectionUrl(String sectionUrl) {
        this.sectionUrl = sectionUrl;
    }

    public String getSectionsIcon() {
        return sectionsIcon;
    }

    public void setSectionsIcon(String sectionsIcon) {
        this.sectionsIcon = sectionsIcon;
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