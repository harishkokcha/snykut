package com.namdev.sanyukt.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Harish on 12/29/2016.
 */
public class Member {

    @SerializedName("member_id")
    private String memberId;
    @SerializedName("user_id")
    private String memberUserId;
    @SerializedName("member_name")
    private String memberName;
    @SerializedName("member_age")
    private String memberAge;
    @SerializedName("member_dob")
    private String memberDob;
    @SerializedName("member_relation")
    private String memberRelation;
    private String memberBirthPlace;
    @SerializedName("member_gender")
    private String memberGender;
    @SerializedName("member_physical_status")
    private String memberPhysicalStatus;
    @SerializedName("member_height")
    private String memberHeight;
    @SerializedName("member_weight")
    private String memberWeight;
    @SerializedName("member_body_type")
    private String memberBodyType;
    @SerializedName("member_complexion")
    private String memberBodyComplexion;
    @SerializedName("member_merital_status")
    private String memberMaritalStatus;
    @SerializedName("member_child")
    private String memberHaveChild;
    private String memberReligion;
    @SerializedName("member_m_gotra")
    private String memberMotherGotta;
    @SerializedName("member_f_gotra")
    private String memberFatherGotta;
    @SerializedName("memebr_ismanglik")
    private String memberIsManglik;
    @SerializedName("member_basic_edu")
    private String memberEducation;
    @SerializedName("member_high_edu")
    private String memberEducationHigher;
    @SerializedName("member_employee_in")
    private String memberEmployeeIn;
    @SerializedName("member_role")
    private String memberEmployeeDetails;
    @SerializedName("member_income")
    private String memberAnnualIncome;
    @SerializedName("member_fname")
    private String memberFatherName;
    @SerializedName("member_foccupation")
    private String memberFatherOccupation;
    @SerializedName("member_moccupation")
    private String memberMotherOccupation;
    @SerializedName("member_mname")
    private String memberMotherName;
    @SerializedName("member_city")
    private String memberCity;
    @SerializedName("member_state")
    private String memberState;
    @SerializedName("member_like")
    private int memberLike;
    @SerializedName("member_fev")
    private int memberFev;
    private String memberAddress;
    private String mamberAboutMe;
    private String action;
    private int pageNumber;
    private int perPageData;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getPerPageData() {
        return perPageData;
    }

    public void setPerPageData(int perPageData) {
        this.perPageData = perPageData;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public String getMamberAboutMe() {
        return mamberAboutMe;
    }

    public void setMamberAboutMe(String mamberAboutMe) {
        this.mamberAboutMe = mamberAboutMe;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberState() {
        return memberState;
    }

    public void setMemberState(String memberState) {
        this.memberState = memberState;
    }

    public String getMemberCity() {
        return memberCity;
    }

    public void setMemberCity(String memberCity) {
        this.memberCity = memberCity;
    }

    public String getMemberMotherName() {
        return memberMotherName;
    }

    public void setMemberMotherName(String memberMotherName) {
        this.memberMotherName = memberMotherName;
    }

    public String getMemberMotherOccupation() {
        return memberMotherOccupation;
    }

    public void setMemberMotherOccupation(String memberMotherOccupation) {
        this.memberMotherOccupation = memberMotherOccupation;
    }

    public String getMemberFatherOccupation() {
        return memberFatherOccupation;
    }

    public void setMemberFatherOccupation(String memberFatherOccupation) {
        this.memberFatherOccupation = memberFatherOccupation;
    }

    public String getMemberFatherName() {
        return memberFatherName;
    }

    public void setMemberFatherName(String memberFatherName) {
        this.memberFatherName = memberFatherName;
    }


    public String getMemberAnnualIncome() {
        return memberAnnualIncome;
    }

    public void setMemberAnnualIncome(String memberAnnualIncome) {
        this.memberAnnualIncome = memberAnnualIncome;
    }

    public String getMemberEmployeeDetails() {
        return memberEmployeeDetails;
    }

    public void setMemberEmployeeDetails(String memberEmployeeDetails) {
        this.memberEmployeeDetails = memberEmployeeDetails;
    }

    public String getMemberEmployeeIn() {
        return memberEmployeeIn;
    }

    public void setMemberEmployeeIn(String memberEmployeeIn) {
        this.memberEmployeeIn = memberEmployeeIn;
    }


    public String getMemberEducation() {
        return memberEducation;
    }

    public void setMemberEducation(String memberEducation) {
        this.memberEducation = memberEducation;
    }


    public String getMemberIsManglik() {
        return memberIsManglik;
    }

    public void setMemberIsManglik(String memberIsManglik) {
        this.memberIsManglik = memberIsManglik;
    }

    public String getMemberFatherGotta() {
        return memberFatherGotta;
    }

    public void setMemberFatherGotta(String memberFatherGotta) {
        this.memberFatherGotta = memberFatherGotta;
    }

    public String getMemberMotherGotta() {
        return memberMotherGotta;
    }

    public void setMemberMotherGotta(String memberMotherGotta) {
        this.memberMotherGotta = memberMotherGotta;
    }


    public String getMemberReligion() {
        return memberReligion;
    }

    public void setMemberReligion(String memberReligion) {
        this.memberReligion = memberReligion;
    }

    public String getMemberHaveChild() {
        return memberHaveChild;
    }

    public void setMemberHaveChild(String memberHaveChild) {
        this.memberHaveChild = memberHaveChild;
    }

    public String getMemberMaritalStatus() {
        return memberMaritalStatus;
    }

    public void setMemberMaritalStatus(String memberMaritalStatus) {
        this.memberMaritalStatus = memberMaritalStatus;
    }

    public String getMemberBodyComplexion() {
        return memberBodyComplexion;
    }

    public void setMemberBodyComplexion(String memberBodyComplexion) {
        this.memberBodyComplexion = memberBodyComplexion;
    }

    public String getMemberBodyType() {
        return memberBodyType;
    }

    public void setMemberBodyType(String memberBodyType) {
        this.memberBodyType = memberBodyType;
    }

    public String getMemberWeight() {
        return memberWeight;
    }

    public void setMemberWeight(String memberWeight) {
        this.memberWeight = memberWeight;
    }

    public String getMemberHeight() {
        return memberHeight;
    }

    public void setMemberHeight(String memberHeight) {
        this.memberHeight = memberHeight;
    }

    public String getMemberPhysicalStatus() {
        return memberPhysicalStatus;
    }

    public void setMemberPhysicalStatus(String memberPhysicalStatus) {
        this.memberPhysicalStatus = memberPhysicalStatus;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public String getMemberBirthPlace() {
        return memberBirthPlace;
    }

    public void setMemberBirthPlace(String memberBirthPlace) {
        this.memberBirthPlace = memberBirthPlace;
    }

    public String getMemberRelation() {
        return memberRelation;
    }

    public void setMemberRelation(String memberRelation) {
        this.memberRelation = memberRelation;
    }

    public String getMemberDob() {
        return memberDob;
    }

    public void setMemberDob(String memberDob) {
        this.memberDob = memberDob;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberEducationHigher() {
        return memberEducationHigher;
    }

    public void setMemberEducationHigher(String memberEducationHigher) {
        this.memberEducationHigher = memberEducationHigher;
    }

    public String getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(String memberAge) {
        this.memberAge = memberAge;
    }

    public int getMemberLike() {
        return memberLike;
    }

    public void setMemberLike(int memberLike) {
        this.memberLike = memberLike;
    }

    public int getMemberFev() {
        return memberFev;
    }

    public void setMemberFev(int memberFev) {
        this.memberFev = memberFev;
    }
}
