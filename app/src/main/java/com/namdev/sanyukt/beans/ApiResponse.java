package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {


    private String responsecode;
    private String dataDS;
    @SerializedName("data")
    private Object objects;

    private String message;

    public Object getObjects() {
        return objects;
    }

    public void setObjects(Object objects) {
        this.objects = objects;
    }



    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getData() {
        return dataDS;
    }

    public void setData(String data) {
        this.dataDS = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
