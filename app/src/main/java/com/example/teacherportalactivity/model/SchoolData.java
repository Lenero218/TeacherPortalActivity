 package com.example.teacherportalactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class SchoolData {

     @SerializedName("name_prefix")
     @Expose
     private String namePrefix;
     @SerializedName("name")
     @Expose
     private String name;
     @SerializedName("mcm_no")
     @Expose
     private String mcmNo;
     @SerializedName("city")
     @Expose
     private String city;
     @SerializedName("state_code")
     @Expose
     private String stateCode;
     @SerializedName("address")
     @Expose
     private String address;

     public String getNamePrefix() {
         return namePrefix;
     }

     public void setNamePrefix(String namePrefix) {
         this.namePrefix = namePrefix;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getMcmNo() {
         return mcmNo;
     }

     public void setMcmNo(String mcmNo) {
         this.mcmNo = mcmNo;
     }

     public String getCity() {
         return city;
     }

     public void setCity(String city) {
         this.city = city;
     }

     public String getStateCode() {
         return stateCode;
     }

     public void setStateCode(String stateCode) {
         this.stateCode = stateCode;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

 }
