package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harish on 4/17/2017.
 */

public class ListEdu {
    private String responsecode;
    private String dataDS;

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getDataDS() {
        return dataDS;
    }

    public void setDataDS(String dataDS) {
        this.dataDS = dataDS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    private CareerData careerData;

    private String message;

    public CareerData getCareerData() {
        return careerData;
    }

    public void setCareerData(CareerData careerData) {
        this.careerData = careerData;
    }

}

