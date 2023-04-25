package com.example.teacherportalactivity.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponseData {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("is_voucher")
    @Expose
    private Boolean isVoucher;

    @Expose
    private List<ClassListData> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<ClassListData> getData() {
        return data;
    }

    public void setData(List<ClassListData> data) {
        this.data = data;
    }

    public Boolean getVoucher() {
        return isVoucher;
    }

    public void setVoucher(Boolean voucher) {
        isVoucher = voucher;
    }
}
