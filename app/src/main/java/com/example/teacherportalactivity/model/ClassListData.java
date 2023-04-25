package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassListData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_type")
    @Expose
    private String appType;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("sections")
    @Expose
    private String sections;
    @SerializedName("mcm_code")
    @Expose
    private String mcmCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getMcmCode() {
        return mcmCode;
    }

    public void setMcmCode(String mcmCode) {
        this.mcmCode = mcmCode;
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
