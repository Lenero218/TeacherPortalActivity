package com.example.teacherportalactivity.model;


public class SchoolDetails {

    private String schoolCode;
    private String schoolName;
    private String schoolCity;
    private String schoolStateCode;
    private String schoolAddress;

    public SchoolDetails(String schoolCode, String schoolName, String schoolCity , String schoolStateCode, String schoolAddress) {
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
        this.schoolCity = schoolCity;
        this.schoolStateCode = schoolStateCode;
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolStateCode() {
        return schoolStateCode;
    }

    public void setSchoolStateCode(String schoolStateCode) {
        this.schoolStateCode = schoolStateCode;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }
}

