package com.namdev.sanyukt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.adapters.CustomAdapter;
import com.namdev.sanyukt.adapters.RelationAdapter;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.CareerData;
import com.namdev.sanyukt.beans.ListEdu;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.MemberResponse;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;
import com.namdev.sanyukt.views.CustomTextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Harish on 4/10/2017.
 */

public class AddEditPersonDetails extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Member member;
    private Activity mActivity;
    private Calendar presentCalender;
    private Calendar selectedCalendar;
    private DatePickerDialog datePickerDialog;
    private EditText dob;
    private Member upData;
    private EditText weight;
    private EditText name;
    private EditText fGotra;
    private AutoCompleteTextView education;
    private AutoCompleteTextView hEducation;
    private AutoCompleteTextView empDetails;
    private EditText city;
    private EditText state;
    private EditText fName;
    private EditText mName;
    private EditText mGotra;
    private EditText relation;
    private EditText manglik;
    private EditText bodyType;
    private EditText bodyComplexion;
    private EditText maritalStatus;
    private EditText physicalStatus;
    private EditText haveChild;
    private EditText employeeIn;
    private EditText AnnualIncome;
    private EditText motherOccupation;
    private EditText fatherOccupation;
    private EditText height;
    private RadioGroup genderRadioGroup;


    public void setUpData(Member upData) {
        dob.setText(upData.getMemberDob());
        weight.setText(upData.getMemberWeight());
        name.setText(upData.getMemberName());
        fGotra.setText(upData.getMemberFatherGotta());
        education.setText(upData.getMemberEducation());
        hEducation.setText(upData.getMemberEducationHigher());
        empDetails.setText(upData.getMemberEmployeeDetails());
        city.setText(upData.getMemberCity());
        state.setText(upData.getMemberState());
        fName.setText(upData.getMemberFatherName());
        mName.setText(upData.getMemberMotherName());
        mGotra.setText(upData.getMemberMotherGotta());
        relation.setText(upData.getMemberRelation());
        manglik.setText(upData.getMemberIsManglik());
        bodyType.setText(upData.getMemberBodyType());
        bodyComplexion.setText(upData.getMemberBodyComplexion());
        maritalStatus.setText(upData.getMemberMaritalStatus());
        physicalStatus.setText(upData.getMemberPhysicalStatus());
        haveChild.setText(upData.getMemberHaveChild());
        employeeIn.setText(upData.getMemberEmployeeIn());
        AnnualIncome.setText(upData.getMemberAnnualIncome());
        motherOccupation.setText(upData.getMemberMotherOccupation());
        fatherOccupation.setText(upData.getMemberFatherOccupation());
        height.setText(upData.getMemberHeight());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_basicdetails_layout);
        mContext = this;
        mActivity = this;
        member = new Member();
        Users users = AppPreferences.getInstance().getUser(this);
        String memberId = getIntent().getStringExtra(AppConstants.MemberId);
        final ProgressBar finalProgressBar = (ProgressBar) findViewById(R.id.progress_bar_example);
        finalProgressBar.setVisibility(View.GONE);
        if (memberId != null && !memberId.equals("")) {
            member.setMemberUserId(users.getUserid());
            member.setMemberId(getIntent().getStringExtra(AppConstants.MemberId));
            member.setAction(AppConstants.MEMBER_DETAILS);
            finalProgressBar.setVisibility(View.VISIBLE);
            GenericRequest genericRequest = new GenericRequest<>(Request.Method.POST, AppConstants.BASE_URL,
                    MemberResponse.class, member, new Response.Listener<MemberResponse>() {
                @Override
                public void onResponse(MemberResponse response) {
                    finalProgressBar.setVisibility(View.GONE);
                    if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                        member = response.getMember();
                        setUpData(member);
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
        presentCalender = Calendar.getInstance();
        presentCalender.add(Calendar.DAY_OF_MONTH, 0);

        selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(Calendar.DAY_OF_MONTH, presentCalender.get(Calendar.DAY_OF_MONTH));
        selectedCalendar.set(Calendar.MONTH, presentCalender.get(Calendar.MONTH));
        selectedCalendar.set(Calendar.YEAR, presentCalender.get(Calendar.YEAR));


        relation = (EditText) findViewById(R.id.id_input_profile_for);
        manglik = (EditText) findViewById(R.id.id_input_manglik);
        bodyType = (EditText) findViewById(R.id.id_input_body_type);
        bodyComplexion = (EditText) findViewById(R.id.id_input_body_complexion);
        maritalStatus = (EditText) findViewById(R.id.id_input_marital_satus);
        haveChild = (EditText) findViewById(R.id.id_input_have_child);
        physicalStatus = (EditText) findViewById(R.id.id_input_physical_status);
        employeeIn = (EditText) findViewById(R.id.id_input_employement_in);
        AnnualIncome = (EditText) findViewById(R.id.id_input_annual_income);
        motherOccupation = (EditText) findViewById(R.id.id_input_mother_occupation);
        fatherOccupation = (EditText) findViewById(R.id.id_input_father_occupation);
        height = (EditText) findViewById(R.id.id_input_height_type);
        relation.setOnClickListener(this);
        manglik.setOnClickListener(this);
        bodyType.setOnClickListener(this);
        bodyComplexion.setOnClickListener(this);
        maritalStatus.setOnClickListener(this);
        haveChild.setOnClickListener(this);
        physicalStatus.setOnClickListener(this);
        employeeIn.setOnClickListener(this);
        AnnualIncome.setOnClickListener(this);
        motherOccupation.setOnClickListener(this);
        fatherOccupation.setOnClickListener(this);
        height.setOnClickListener(this);

        weight = (EditText) findViewById(R.id.id_input_weight);
        name = (EditText) findViewById(R.id.id_input_name);
        dob = (EditText) findViewById(R.id.id_input_dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        genderRadioGroup = (RadioGroup) findViewById(R.id.rdg_gender);
        education = (AutoCompleteTextView) findViewById(R.id.id_input_education);
        hEducation = (AutoCompleteTextView) findViewById(R.id.id_input_education_heigher);
        empDetails = (AutoCompleteTextView) findViewById(R.id.id_input_employement_details);

        //CustomAdapter customAdapter = new CustomAdapter(mActivity,list);


        city = (EditText) findViewById(R.id.id_input_city);
        state = (EditText) findViewById(R.id.id_input_state);
        fName = (EditText) findViewById(R.id.id_input_father_name);
        mName = (EditText) findViewById(R.id.id_input_mother_name);
        mGotra = (EditText) findViewById(R.id.id_input_mother_gotra);
        fGotra = (EditText) findViewById(R.id.id_input_father_gotra);

        TextView submit = (TextView) findViewById(R.id.id_fragment_new_member_submit);

        initDateTimePicker(21);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Harish", " onCheckedChanged " + group.getCheckedRadioButtonId() + " checkedId " + checkedId);
                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.radioMale) {
                    initDateTimePicker(21);
                } else {
                    initDateTimePicker(18);
                }

            }
        });
        submit.setOnClickListener(this);
        getListEducation();
    }

    public void getListEducation() {
        final ProgressBar finalProgressBar = (ProgressBar) findViewById(R.id.progress_bar_example);
        //finalProgressBar.setVisibility(View.VISIBLE);
        member.setAction(AppConstants.DEFAULT_ADD_MEMBER_LIST);
        GenericRequest genericRequest = new GenericRequest<>(Request.Method.POST, AppConstants.BASE_GEN_URL,
                ListEdu.class, member, new Response.Listener<ListEdu>() {
            @Override
            public void onResponse(final ListEdu response) {
                //finalProgressBar.setVisibility(View.GONE);
                CustomAdapter customAdapter = new CustomAdapter(mActivity, response.getCareerData().getCareerBasicList());
                education.setAdapter(customAdapter);
                education.setThreshold(1);
                education.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        education.setText(response.getCareerData().getCareerBasicList().get(position).getName());
                    }
                });
                CustomAdapter hEduAdapter = new CustomAdapter(mActivity, response.getCareerData().getCareerHighList());
                hEducation.setAdapter(hEduAdapter);
                hEducation.setThreshold(1);
                hEducation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hEducation.setText(response.getCareerData().getCareerHighList().get(position).getName());
                    }
                });
                CustomAdapter hEmpAdapter = new CustomAdapter(mActivity, response.getCareerData().getCareerFunAreaList());
                empDetails.setAdapter(hEmpAdapter);
                empDetails.setThreshold(1);
                empDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        empDetails.setText(response.getCareerData().getCareerFunAreaList().get(position).getName());
                    }
                });

                Log.d("Harish", "response :" + new Gson().toJson(response).toString());
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

    private void showSpinnerDialog(Activity hostActivity, final EditText materialSpinner, String manglik, final String[] stringArray) {

        final Dialog dialog = new Dialog(hostActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_list_dialog);
        TextView header = (TextView) dialog.findViewById(R.id.id_dialog_box_hdr);

        header.setText(manglik);

        BaseAdapter mAdapter = new RelationAdapter(hostActivity
                , stringArray);
        final CustomTextView listCrossBtn = (CustomTextView) dialog.findViewById(R.id.id_select_relation_dialog_close_ic);
        ListView listView = (ListView) dialog.findViewById(R.id.id_city_listView);
        listView.setAdapter(mAdapter);
        listCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materialSpinner.setText(stringArray[position]);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void initDateTimePicker(int yrs) {
        datePickerDialog = new DatePickerDialog(mActivity, dpickerListener, presentCalender.get(Calendar.YEAR), presentCalender.get(Calendar.MONTH),
                presentCalender.get(Calendar.DAY_OF_MONTH));
        Calendar maxCalender = Calendar.getInstance();
        maxCalender.add(Calendar.YEAR, -yrs);
        datePickerDialog.getDatePicker().setMaxDate(maxCalender.getTimeInMillis());
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.YEAR, year);

            Date presentDate = presentCalender.getTime();
            Date selectedDate = selectedCalendar.getTime();
            Log.d("Harish", "selectedCalendar : " + selectedCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (selectedCalendar.get(Calendar.MONTH) + 1) + "/" + selectedCalendar.get(Calendar.YEAR));
            Log.d("Harish", "selectedCalendar AGE : " + getAgeFromDate());
            dob.setText(selectedCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (selectedCalendar.get(Calendar.MONTH) + 1) + "/" + selectedCalendar.get(Calendar.YEAR));

        }
    };

    private int getAgeFromDate() {

        if (selectedCalendar.getTime().after(presentCalender.getTime())) {
            return -1;
        } else {
            return presentCalender.get(Calendar.YEAR) - selectedCalendar.get(Calendar.YEAR);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_input_profile_for:
                showSpinnerDialog(mActivity, relation, "Relation", getResources().getStringArray(R.array.relation_array));
                break;
            case R.id.id_input_manglik:
                showSpinnerDialog(mActivity, manglik, "Manglik", getResources().getStringArray(R.array.manglik_array));
                break;
            case R.id.id_input_body_type:
                showSpinnerDialog(mActivity, bodyType, "Body Type", getResources().getStringArray(R.array.body_type_array));
                break;
            case R.id.id_input_body_complexion:
                showSpinnerDialog(mActivity, bodyComplexion, "Body Complexion", getResources().getStringArray(R.array.body_complexion_array));
                break;
            case R.id.id_input_marital_satus:
                showSpinnerDialog(mActivity, maritalStatus, "Marital Status", getResources().getStringArray(R.array.marital_status_array));
                break;
            case R.id.id_input_have_child:
                showSpinnerDialog(mActivity, haveChild, "Have Child", getResources().getStringArray(R.array.have_child_array));
                break;
            case R.id.id_input_physical_status:
                showSpinnerDialog(mActivity, physicalStatus, "Physical status", getResources().getStringArray(R.array.physical_status_array));
                break;
            case R.id.id_input_employement_in:
                showSpinnerDialog(mActivity, employeeIn, "Employee In", getResources().getStringArray(R.array.employement_in_array));
                break;
            case R.id.id_input_annual_income:
                showSpinnerDialog(mActivity, AnnualIncome, "Annual Income", getResources().getStringArray(R.array.annual_salary_array));
                break;
            case R.id.id_input_mother_occupation:
                showSpinnerDialog(mActivity, motherOccupation, "Mother Occupation", getResources().getStringArray(R.array.employement_in_array));
                break;
            case R.id.id_input_father_occupation:
                showSpinnerDialog(mActivity, fatherOccupation, "Father Occupation", getResources().getStringArray(R.array.employement_in_array));
                break;
            case R.id.id_input_height_type:
                showSpinnerDialog(mActivity, height, "Height", getResources().getStringArray(R.array.height_array));
                break;
            case R.id.id_fragment_new_member_submit: {
                member.setMemberName(name.getText().toString());
                member.setMemberDob(dob.getText().toString());
                member.setMemberCity(city.getText().toString());
                member.setMemberState(state.getText().toString());
                member.setMemberEducation(education.getText().toString());
                member.setMemberEmployeeDetails(empDetails.getText().toString());
                member.setMemberFatherName(fName.getText().toString());
                member.setMemberFatherGotta(fGotra.getText().toString());
                member.setMemberMotherName(mName.getText().toString());
                member.setMemberMotherGotta(mGotra.getText().toString());

                member.setMemberEducationHigher(hEducation.getText().toString());
                member.setMemberAnnualIncome(AnnualIncome.getText().toString());
                member.setMemberHeight(height.getText().toString());
                member.setMemberWeight(weight.getText().toString());
                member.setMemberBodyComplexion(bodyComplexion.getText().toString());
                member.setMemberBodyType(bodyType.getText().toString());
                member.setMemberEducation(education.getText().toString());
                member.setMemberEducationHigher(hEducation.getText().toString());
                member.setMemberRelation(relation.getText().toString());
                member.setMemberMotherOccupation(motherOccupation.getText().toString());
                member.setMemberFatherOccupation(fatherOccupation.getText().toString());
                member.setMemberHaveChild(haveChild.getText().toString());
                member.setMemberIsManglik(manglik.getText().toString());

                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.radioMale) {
                    member.setMemberGender("M");
                } else {
                    member.setMemberGender("F");
                }

                Users users = AppPreferences.getInstance().getUser(mActivity);
                member.setMemberUserId(users.getUserid());
                if (member.getMemberId() == null) {
                    member.setAction(AppConstants.MEMBER_CREATE);
                } else {
                    member.setAction(AppConstants.MEMBER_UPDATE);
                }
                Log.d("Harish", " Member Details " + new Gson().toJson(member));
                GenericRequest genericRequest = new GenericRequest<MemberResponse>(Request.Method.POST, AppConstants.BASE_URL,
                        MemberResponse.class, member, new Response.Listener<MemberResponse>() {
                    @Override
                    public void onResponse(MemberResponse response) {
                        if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                            Member member = response.getMember();
                            if (member.getMemberId() != null) {
                                Toast.makeText(mActivity, "member Added successfully", Toast.LENGTH_SHORT).show();
                            }
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
            break;
            default:
                break;

        }
    }
}
