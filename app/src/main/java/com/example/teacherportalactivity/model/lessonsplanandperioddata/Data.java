package com.example.teacherportalactivity.model.lessonsplanandperioddata;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Data {

    @SerializedName("plan_details")
    @Expose
    private List<PlanDetail> planDetails = null;
    @SerializedName("period_list")
    @Expose
    private List<PeriodData> periodList = null;

    public List<PlanDetail> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(List<PlanDetail> planDetails) {
        this.planDetails = planDetails;
    }

    public List<PeriodData> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<PeriodData> periodList) {
        this.periodList = periodList;
    }

}