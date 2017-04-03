package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harish on 4/3/2017.
 */
public class MemberListResponse {

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

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    private List<Member> memberList;

    private String message;

}
