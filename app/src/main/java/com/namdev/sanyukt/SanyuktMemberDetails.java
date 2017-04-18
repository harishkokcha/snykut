package com.namdev.sanyukt;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.MemberResponse;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;

/**
 * Created by Harish on 2/9/2017.
 */
public class SanyuktMemberDetails extends AppCompatActivity implements View.OnClickListener {
    private Activity mActivity;
    private TextView name, createdBy, dob, maritalStatus, height, weight, skinType, bodyType, fName, fStatus, fGotra, mName, mStatus, mGotra, manglikText, state, city, edu, workingIn, workingAs, income;
    private Member member;
    private Users users;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_person_details_slide_page);
        mActivity = getParent();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.id_sanyukt_member_name);
        createdBy = (TextView) findViewById(R.id.id_details_created_by);
        dob = (TextView) findViewById(R.id.id_details_age_dob);
        maritalStatus = (TextView) findViewById(R.id.id_details_marital_status);
        height = (TextView) findViewById(R.id.id_details_height);
        weight = (TextView) findViewById(R.id.id_details_weight);
        skinType = (TextView) findViewById(R.id.id_details_skin_type);
        bodyType = (TextView) findViewById(R.id.id_details_body_type);
        fName = (TextView) findViewById(R.id.id_details_father_name);
        fStatus = (TextView) findViewById(R.id.id_details_father_status);
        fGotra = (TextView) findViewById(R.id.id_details_father_gotra);
        mName = (TextView) findViewById(R.id.id_details_mother_name);
        mStatus = (TextView) findViewById(R.id.id_details_mother_status);
        mGotra = (TextView) findViewById(R.id.id_details_mother_gotra);
        manglikText = (TextView) findViewById(R.id.id_details_is_manglik);
        state = (TextView) findViewById(R.id.id_details_state);
        city = (TextView) findViewById(R.id.id_details_city);
        edu = (TextView) findViewById(R.id.id_details_education);
        workingIn = (TextView) findViewById(R.id.id_details_working);
        workingAs = (TextView) findViewById(R.id.id_details_workas);
        income = (TextView) findViewById(R.id.id_details_income);

        users = AppPreferences.getInstance().getUser(this);
        Log.d("Harish", "AppPreferences.getInstance().getUser(mActivity) SanyuktMemberDetails userid : " + users.getUserid());
        member = new Member();
        member.setMemberUserId(users.getUserid());
        member.setMemberId(getIntent().getStringExtra(AppConstants.MemberId));
        member.setAction(AppConstants.MEMBER_DETAILS);
        View likeBtn = findViewById(R.id.id_like_btn);
        View favBtn = findViewById(R.id.id_favorite_btn);
        View contactBtn = findViewById(R.id.id_contact_btn);
        likeBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);
        contactBtn.setOnClickListener(this);
        final ProgressBar finalProgressBar = (ProgressBar) findViewById(R.id.progress_bar_example);
        finalProgressBar.setVisibility(View.VISIBLE);
        final ScrollView view = (ScrollView) findViewById(R.id.id_page_scrol_view);
        view.setVisibility(View.GONE);
        GenericRequest genericRequest = new GenericRequest<>(Request.Method.POST, AppConstants.BASE_URL,
                MemberResponse.class, member, new Response.Listener<MemberResponse>() {
            @Override
            public void onResponse(MemberResponse response) {
                finalProgressBar.setVisibility(View.GONE);
                if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                    member = response.getMember();
                    setUpData(member);
                    view.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finalProgressBar.setVisibility(View.GONE);
                Log.d("Harish", "VolleyError error : " + new Gson().toJson(error));
            }
        });

        AppController.getInstance().addToRequestQueue(genericRequest);
    }

    private void setUpData(Member member) {

        name.setText(member.getMemberName());
        createdBy.setText(member.getMemberRelation());
        dob.setText(getAgeFromDOB(member.getMemberDob()));
        maritalStatus.setText(member.getMemberMaritalStatus());
        height.setText(member.getMemberHeight());
        weight.setText(member.getMemberWeight());
        skinType.setText(member.getMemberBodyComplexion());
        bodyType.setText(member.getMemberPhysicalStatus());
        fName.setText(member.getMemberFatherName());
        fStatus.setText(member.getMemberFatherOccupation());
        fGotra.setText(member.getMemberFatherGotta());
        mName.setText(member.getMemberMotherName());
        mStatus.setText(member.getMemberMotherOccupation());
        mGotra.setText(member.getMemberMotherGotta());
        manglikText.setText(member.getMemberIsManglik());
        state.setText(member.getMemberState());
        city.setText(member.getMemberCity());
        edu.setText(member.getMemberEducation());
        workingIn.setText(member.getMemberEmployeeIn());
        workingAs.setText(member.getMemberEmployeeDetails());
        income.setText(member.getMemberAnnualIncome());
        member.setMemberUserId(users.getUserid());

        actionBar.setTitle(member.getMemberName());
        actionBar.setSubtitle(member.getMemberEducation());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getAgeFromDOB(String memberDob) {

        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_like_btn:
                member.setAction(AppConstants.LIKE_MEMBER);
                member.setMemberLike(member.getMemberLike() == 0 ? 1 : 0);
                sendRequesttoserver();
                break;
            case R.id.id_favorite_btn:
                member.setAction(AppConstants.FEV_MEMBER);
                member.setMemberFev(member.getMemberFev() == 0 ? 1 : 0);
                sendRequesttoserver();
                break;
            case R.id.id_contact_btn:
                break;
            default:
                break;
        }
    }

    private void sendRequesttoserver() {

        GenericRequest genericRequest = new GenericRequest<>(Request.Method.POST, AppConstants.BASE_URL,
                MemberResponse.class, member, new Response.Listener<MemberResponse>() {
            @Override
            public void onResponse(MemberResponse response) {
                Log.d("Harish", "VolleyError error : " + new Gson().toJson(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Harish", "VolleyError error : " + new Gson().toJson(error));
            }
        });
        AppController.getInstance().addToRequestQueue(genericRequest);
    }
}
