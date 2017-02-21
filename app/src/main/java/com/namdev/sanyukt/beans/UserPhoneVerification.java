package com.namdev.sanyukt.beans;

/**
 * Created by Harish on 2/9/2017.
 */
public class UserPhoneVerification {
    private String phoneNo;
    private String userID;
    private String otp;
    private int otpSessionID;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getOtpSessionID() {
        return otpSessionID;
    }

    public void setOtpSessionID(int otpSessionID) {
        this.otpSessionID = otpSessionID;
    }
}

