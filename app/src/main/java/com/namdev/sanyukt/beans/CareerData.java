package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harish on 4/17/2017.
 */


public  class CareerData {
    public List<NameModel> getCareerBasicList() {
        return careerBasicList;
    }

    public void setCareerBasicList(List<NameModel> careerBasicList) {
        this.careerBasicList = careerBasicList;
    }

    public List<NameModel> getCareerHighList() {
        return careerHighList;
    }

    public void setCareerHighList(List<NameModel> careerHighList) {
        this.careerHighList = careerHighList;
    }

    public List<NameModel> getCareerFunAreaList() {
        return careerFunAreaList;
    }

    public void setCareerFunAreaList(List<NameModel> careerFunAreaList) {
        this.careerFunAreaList = careerFunAreaList;
    }

    @SerializedName("basic_edu")
    List<NameModel> careerBasicList;
    @SerializedName("high_edu")
    List<NameModel> careerHighList;

    @SerializedName("job_role")
    List<NameModel> careerFunAreaList;
}

