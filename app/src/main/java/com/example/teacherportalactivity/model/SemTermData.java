package com.example.teacherportalactivity.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SemTermData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("series_id")
    @Expose
    private Integer seriesId;
    @SerializedName("term_sem")
    @Expose
    private String termSem;



    @SerializedName("images")
    @Expose
    private String images;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("status")
    @Expose
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getTermSem() {
        return termSem;
    }

    public void setTermSem(String termSem) {
        this.termSem = termSem;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

