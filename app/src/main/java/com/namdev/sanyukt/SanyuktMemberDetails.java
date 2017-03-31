package com.namdev.sanyukt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
public class SanyuktMemberDetails extends Activity {
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_person_details_slide_page);
        mActivity = getParent();
        Users users = AppPreferences.getInstance().getUser(mActivity);
        Member member = new Member();
        member.setMemberUserId(users.getUserid());
        member.setMemberId(getIntent().getStringExtra(AppConstants.MemberId));
        ImageView likeBtn = (ImageView) findViewById(R.id.id_like_btn);
        ImageView favBtn = (ImageView) findViewById(R.id.id_favorite_btn);
        ImageView contactBtn = (ImageView) findViewById(R.id.ic_contact_btn);
        GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.MEMBER_DETAILS,
                ApiResponse.class, member, new Response.Listener<ApiResponse>() {
            @Override
            public void onResponse(ApiResponse response) {
                if (response.getData().equals(AppConstants.SUCCESS)) {
                    Member member = new Gson().fromJson(response.getData(), Member.class);
                    setUpData(mActivity, member);
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
        Log.i("Setup data",member.toString());
        TextView name = (TextView) mActivity.findViewById(R.id.id_sanyukt_member_name);
        TextView createdBy = (TextView) mActivity.findViewById(R.id.id_details_created_by);
        TextView dob = (TextView) mActivity.findViewById(R.id.id_details_age_dob);
        TextView maritalStatus = (TextView) mActivity.findViewById(R.id.id_details_marital_status);
        TextView height = (TextView) mActivity.findViewById(R.id.id_details_height);
        TextView weight = (TextView) mActivity.findViewById(R.id.id_details_weight);
        TextView skinType = (TextView) mActivity.findViewById(R.id.id_details_skin_type);
        TextView bodyType = (TextView) mActivity.findViewById(R.id.id_details_body_type);
//        TextView fName = (TextView) mActivity.findViewById(R.id.id_details_father_name);
//        TextView fStatus = (TextView) mActivity.findViewById(R.id.id_details_father_status);
//        TextView fGotra = (TextView) mActivity.findViewById(R.id.id_details_father_gotra);
//        TextView mName = (TextView) mActivity.findViewById(R.id.id_details_mother_name);
//        TextView mStatus = (TextView) mActivity.findViewById(R.id.id_details_mother_status);
//        TextView mGotra = (TextView) mActivity.findViewById(R.id.id_details_mother_gotra);
        TextView manglikText = (TextView) mActivity.findViewById(R.id.id_details_is_manglik);
        TextView state = (TextView) mActivity.findViewById(R.id.id_details_state);
        TextView city = (TextView) mActivity.findViewById(R.id.id_details_city);
        TextView edu = (TextView) mActivity.findViewById(R.id.id_details_education);
        TextView workingIn = (TextView) mActivity.findViewById(R.id.id_details_working);
        TextView workingAs = (TextView) mActivity.findViewById(R.id.id_details_workas);
        TextView income = (TextView) mActivity.findViewById(R.id.id_details_income);

        name.setText(member.getMemberName());
        createdBy.setText(member.getMemberRelation());
        dob.setText(member.getMemberDob());
        maritalStatus.setText(member.getMemberMaritalStatus());
        height.setText(member.getMemberHeight());
        weight.setText(member.getMemberWeight());
        skinType.setText(member.getMemberBodyComplexion());
        bodyType.setText(member.getMemberPhysicalStatus());
//        fName.setText(member.getMemberFatherName());
//        fStatus.setText(member.getMemberFatherOccupation());
//        fGotra.setText(member.getMemberFatherGotta());
//        mName.setText(member.getMemberMotherName());
//        mStatus.setText(member.getMemberMotherOccupation());
//        mGotra.setText(member.getMemberMotherGotta());
        manglikText.setText(member.getMemberIsManglik());
        state.setText(member.getMemberState());
        city.setText(member.getMemberCity());
        edu.setText(member.getMemberEducation());
        workingIn.setText(member.getMemberEmployeeIn());
        workingAs.setText(member.getMemberEmployeeDetails());
        income.setText(member.getMemberAnnualIncome());


    }
}
