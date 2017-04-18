package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Harish on 4/4/2017.
 */

public class MemberResponse {

    private String responsecode;
    private String dataDS;
    @SerializedName("data")
    private Member member;

    private String message;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
