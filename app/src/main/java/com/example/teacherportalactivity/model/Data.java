package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("series_name")
    @Expose
    private String seriesName;
    @SerializedName("series_colour")
    @Expose
    private String seriesColour;
    @SerializedName("series_image")
    @Expose
    private String seriesImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("book_code")
    @Expose
    private String book_code;

    @SerializedName("is_payment_done")
    @Expose
    private Integer is_payment_done;

    @SerializedName("is_voucher_valid")
    @Expose
    private Integer is_voucher_valid;


    @SerializedName("is_voucher_valid_assessments")
    @Expose
    private Integer is_voucher_valid_assessments;


    @SerializedName("is_voucher_assessments")
    @Expose
    private Integer is_voucher_assessments;


    @SerializedName("is_voucher_both")
    @Expose
    private Integer is_voucher_both;


    @SerializedName("is_voucher_valid_both")
    @Expose
    private Integer is_voucher_valid_both;

    public Integer getIs_voucher_valid_assessments() {
        return is_voucher_valid_assessments;
    }

    public void setIs_voucher_valid_assessments(Integer is_voucher_valid_assessments) {
        this.is_voucher_valid_assessments = is_voucher_valid_assessments;
    }

    public Integer getIs_voucher_valid_both() {
        return is_voucher_valid_both;
    }

    public void setIs_voucher_valid_both(Integer is_voucher_valid_both) {
        this.is_voucher_valid_both = is_voucher_valid_both;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesColour() {
        return seriesColour;
    }

    public void setSeriesColour(String seriesColour) {
        this.seriesColour = seriesColour;
    }

    public String getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
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

    public String getBook_code() {
        return book_code;
    }

    public void setBook_code(String book_code) {
        this.book_code = book_code;
    }

    public Integer getIs_payment_done() {
        return is_payment_done;
    }

    public void setIs_payment_done(Integer is_payment_done) {
        this.is_payment_done = is_payment_done;
    }

    public Integer getIs_voucher_valid() {
        return is_voucher_valid;
    }

    public void setIs_voucher_valid(Integer is_voucher_valid) {
        this.is_voucher_valid = is_voucher_valid;
    }

    public Integer getIs_voucher_assessments() {
        return is_voucher_assessments;
    }

    public void setIs_voucher_assessments(Integer is_voucher_assessments) {
        this.is_voucher_assessments = is_voucher_assessments;
    }

    public Integer getIs_voucher_both() {
        return is_voucher_both;
    }

    public void setIs_voucher_both(Integer is_voucher_both) {
        this.is_voucher_both = is_voucher_both;
    }
}