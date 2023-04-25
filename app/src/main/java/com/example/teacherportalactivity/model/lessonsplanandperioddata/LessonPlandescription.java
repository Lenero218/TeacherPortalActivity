package com.example.teacherportalactivity.model.lessonsplanandperioddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonPlandescription {


    @SerializedName("no_of_teaching_periods")
    @Expose
    private String noOfTeachingPeriods;
    @SerializedName("t_aid")
    @Expose
    private String tAid;
    @SerializedName("digital_asset")
    @Expose
    private String digitalAsset;
    @SerializedName("i_affirm")
    @Expose
    private String iAffirm;

    public String getNoOfTeachingPeriods() {
        return noOfTeachingPeriods;
    }

    public void setNoOfTeachingPeriods(String noOfTeachingPeriods) {
        this.noOfTeachingPeriods = noOfTeachingPeriods;
    }

    public String gettAid() {
        return tAid;
    }

    public void settAid(String tAid) {
        this.tAid = tAid;
    }

    public String getDigitalAsset() {
        return digitalAsset;
    }

    public void setDigitalAsset(String digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    public String getiAffirm() {
        return iAffirm;
    }

    public void setiAffirm(String iAffirm) {
        this.iAffirm = iAffirm;
    }

}