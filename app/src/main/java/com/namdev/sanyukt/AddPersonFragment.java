package com.namdev.sanyukt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.MemberResponse;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;
import com.namdev.sanyukt.views.MaterialSpinner;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Harish on 11/21/2016.
 */
public class AddPersonFragment extends Fragment {
    Context mContext;
    Member member;
    private Activity mActivity;
    private Calendar presentCalender;
    private Calendar selectedCalendar;
    private DatePickerDialog datePickerDialog;
    private EditText dob;

    public static AddPersonFragment newInstance() {
        return new AddPersonFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_person, container, false);
        mActivity = getActivity();
        presentCalender = Calendar.getInstance();
        presentCalender.add(Calendar.DAY_OF_MONTH, 0);

        selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(Calendar.DAY_OF_MONTH, presentCalender.get(Calendar.DAY_OF_MONTH));
        selectedCalendar.set(Calendar.MONTH, presentCalender.get(Calendar.MONTH));
        selectedCalendar.set(Calendar.YEAR, presentCalender.get(Calendar.YEAR));

        member = new Member();
        ProfileForSpinner(rootView);
        //GenderSpinner(rootView);
        ManglikSpinner(rootView);
        BodyTypeSpinner(rootView);
        BodyComplexionSpinner(rootView);
        MaritalStatusSpinner(rootView);
        HaveChildSpinner(rootView);
        PhysicalStatusSpinner(rootView);
        EmployementSpinner(rootView);
        AnnualIncomeSpinner(rootView);
        MotherOcupationSpinner(rootView);
        FatherOcupationSpinner(rootView);
        HeightSpinner(rootView);

//        final EditText height = (EditText) rootView.findViewById(R.id.id_input_height);
        final EditText weight = (EditText) rootView.findViewById(R.id.id_input_weight);
        final EditText name = (EditText) rootView.findViewById(R.id.id_input_name);
        dob = (EditText) rootView.findViewById(R.id.id_input_dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        final RadioGroup genderRadioGroup = (RadioGroup) rootView.findViewById(R.id.rdg_gender);
        final EditText education = (EditText) rootView.findViewById(R.id.id_input_education);
        final EditText hEducation = (EditText) rootView.findViewById(R.id.id_input_education_heigher);
        final EditText empDetails = (EditText) rootView.findViewById(R.id.id_input_employement_details);
        final EditText city = (EditText) rootView.findViewById(R.id.id_input_city);
        final EditText state = (EditText) rootView.findViewById(R.id.id_input_state);
        final EditText fName = (EditText) rootView.findViewById(R.id.id_input_father_name);
        final EditText mName = (EditText) rootView.findViewById(R.id.id_input_mother_name);
        final EditText mGotra = (EditText) rootView.findViewById(R.id.id_input_mother_gotra);
        final EditText fGotra = (EditText) rootView.findViewById(R.id.id_input_father_gotra);

        TextView submit = (TextView) rootView.findViewById(R.id.id_fragment_new_member_submit);

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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                //member.setMemberHeight(height.getText().toString());
                member.setMemberWeight(weight.getText().toString());
                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.radioMale) {
                    member.setMemberGender("M");
                } else {
                    member.setMemberGender("F");
                }

                Users users = AppPreferences.getInstance().getUser(mActivity);
                member.setMemberUserId(users.getUserid());
                member.setAction(AppConstants.MEMBER_CREATE);
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
        });

        return rootView;
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

    private void AnnualIncomeSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_annual_income);
        final String array[] = getResources().getStringArray(R.array.annual_salary_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.annual_salary_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberAnnualIncome(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void HeightSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_height_type);
        final String array[] = getResources().getStringArray(R.array.height_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.height_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberHeight(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void EducationSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_education);
        final String array[] = getResources().getStringArray(R.array.relation_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.relation_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberEducation(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void EmployementSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_employement_in);
        final String array[] = getResources().getStringArray(R.array.employement_in_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.employement_in_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberEmployeeIn(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ManglikSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_manglik);
        final String array[] = getResources().getStringArray(R.array.manglik_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.manglik_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberIsManglik(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ProfileForSpinner(View rootView) {
        MaterialSpinner spinner = (MaterialSpinner) rootView.findViewById(R.id.id_input_profile_for);
        final String array[] = getResources().getStringArray(R.array.relation_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.relation_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberRelation(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void PhysicalStatusSpinner(View rootView) {
        /*Body complexion spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_physical_status);
        final String array[] = getResources().getStringArray(R.array.physical_status_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.physical_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberPhysicalStatus(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void HaveChildSpinner(View rootView) {
        /*Body complexion spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_have_child);
        final String array[] = getResources().getStringArray(R.array.have_child_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.have_child_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberHaveChild(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void MaritalStatusSpinner(View rootView) {
        /*Body complexion spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_marital_satus);
        final String array[] = getResources().getStringArray(R.array.marital_status_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.marital_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberMaritalStatus(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void BodyComplexionSpinner(View rootView) {
        /*Body complexion spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_body_complexion);
        final String array[] = getResources().getStringArray(R.array.body_complexion_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.body_complexion_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        member.setMemberBodyComplexion(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberBodyComplexion(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void BodyTypeSpinner(View rootView) {
        /*Body Type spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_body_type);
        final String array[] = getResources().getStringArray(R.array.body_type_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.body_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        member.setMemberBodyType(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberBodyType(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void MotherOcupationSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_mother_occupation);
        final String array[] = getResources().getStringArray(R.array.employement_in_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.employement_in_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberMotherOccupation(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FatherOcupationSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_father_occupation);
        final String array[] = getResources().getStringArray(R.array.employement_in_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.employement_in_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMemberFatherOccupation(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
