package com.namdev.sanyukt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;

/**
 * Created by Harish on 2/9/2017.
 */
public class SanyuktMemberDetails extends Activity{
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_person_details_slide_page);
        mActivity=getParent();
        Users users=AppPreferences.getInstance().getUser(mActivity);
        Member member=new Member();
        member.setMemberUserId(users.getUserid());
        member.setMemberId(getIntent().getStringExtra(AppConstants.MemberId));
        GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.MEMBER_DETAILS,
                ApiResponse.class, member, new Response.Listener<ApiResponse>() {
            @Override
            public void onResponse(ApiResponse response) {
                if (response.getData().equals(AppConstants.SUCCESS)) {
                    Member member = new Gson().fromJson(response.getData(), Member.class);
                    setUpData(mActivity,member);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };

        AppController.getInstance().addToRequestQueue(genericRequest);
    }

    private void setUpData(Activity mActivity, Member member) {
        TextView createdBy=(TextView)mActivity.findViewById(R.id.id_details_created_by);
        TextView dob=(TextView)mActivity.findViewById(R.id.id_details_age_dob);
        TextView maritalStatus=(TextView)mActivity.findViewById(R.id.id_details_marital_status);
        TextView height=(TextView)mActivity.findViewById(R.id.id_details_height);
        TextView weight=(TextView)mActivity.findViewById(R.id.id_details_weight);
        TextView skinType=(TextView)mActivity.findViewById(R.id.id_details_skin_type);
        TextView bodyType=(TextView)mActivity.findViewById(R.id.id_details_body_type);
        TextView fName=(TextView)mActivity.findViewById(R.id.id_details_father_name);
        TextView fStatus=(TextView)mActivity.findViewById(R.id.id_details_father_status);
        TextView fGotra=(TextView)mActivity.findViewById(R.id.id_details_father_gotra);
        TextView mName=(TextView)mActivity.findViewById(R.id.id_details_mother_name);
        TextView mStatus=(TextView)mActivity.findViewById(R.id.id_details_mother_status);
        TextView mGotra=(TextView)mActivity.findViewById(R.id.id_details_mother_gotra);
        TextView manglikText=(TextView)mActivity.findViewById(R.id.id_details_is_manglik);
        TextView state=(TextView)mActivity.findViewById(R.id.id_details_state);
        TextView city=(TextView)mActivity.findViewById(R.id.id_details_city);
        TextView edu=(TextView)mActivity.findViewById(R.id.id_details_education);
        TextView workingIn=(TextView)mActivity.findViewById(R.id.id_details_working);
        TextView workingAs=(TextView)mActivity.findViewById(R.id.id_details_workas);
        TextView income=(TextView)mActivity.findViewById(R.id.id_details_income);

        createdBy.setText(member.getMemberRelation());
        dob.setText(member.getMemberDob());
        maritalStatus.setText(member.getMamberMaritalStatus());
        height.setText(member.getMamberHeight());
        weight.setText(member.getMamberWeight());
        skinType.setText(member.getMamberBodyComplexion());
        bodyType.setText(member.getMamberPhysicalStatus());
        fName.setText(member.getMamberFatherName());
        fStatus.setText(member.getMamberFatherOcupation());
        fGotra.setText(member.getMamberFatherGotra());
        mName.setText(member.getMamberMotherName());
        mStatus.setText(member.getMamberMotherOcupation());
        mGotra.setText(member.getMamberMotherGotra());
        manglikText.setText(member.getMamberIsManglik());
        state.setText(member.getMamberState());
        city.setText(member.getMamberCity());
        edu.setText(member.getMamberEducation());
        workingIn.setText(member.getMamberEmployeeIn());
        workingAs.setText(member.getMamberEmployeeDetails());
        income.setText(member.getMamberAnnualIncome());


    }
}
