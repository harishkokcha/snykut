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

        final EditText height = (EditText) rootView.findViewById(R.id.id_input_height);
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
                member.setMamberCity(city.getText().toString());
                member.setMamberState(state.getText().toString());
                member.setMamberEducation(education.getText().toString());
                member.setMamberEmployeeDetails(empDetails.getText().toString());
                member.setMamberFatherName(fName.getText().toString());
                member.setMamberFatherGotra(fGotra.getText().toString());
                member.setMamberMotherName(mName.getText().toString());
                member.setMamberMotherGotra(mGotra.getText().toString());
                member.setMamberEducationHigher(hEducation.getText().toString());
                //member.setMamberHeight(height.getText().toString());
                member.setMamberWeight(weight.getText().toString());
                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.radioMale) {
                    member.setMamberGender("M");
                } else {
                    member.setMamberGender("F");
                }

                Users users = AppPreferences.getInstance().getUser(mActivity);
                member.setMemberUserId(users.getUserid());
                Log.d("Harish", " Member Details " + new Gson().toJson(member));
                GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.USER_LOGIN,
                        ApiResponse.class, member, new Response.Listener<ApiResponse>() {
                    @Override
                    public void onResponse(ApiResponse response) {
                        if (response.getData().equals(AppConstants.SUCCESS)) {
                            Member member = new Gson().fromJson(response.getData(), Member.class);
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
                member.setMamberAnnualIncome(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void DrinkingHabbitSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_drinking_habbit);
        final String array[] = getResources().getStringArray(R.array.drinking_habbit_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.drinking_habbit_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberDrinkingHabbit(array[position]);
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
                member.setMamberHeight(array[position]);
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
                member.setMamberEducation(array[position]);
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
                member.setMamberEmployeeIn(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SmokingHabbitSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_smoking_habbit);
        final String array[] = getResources().getStringArray(R.array.smoking_habbit_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.smoking_habbit_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberSmokingHabbit(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void EatingHabbitSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_eating_habbit);
        final String array[] = getResources().getStringArray(R.array.eating_habbit_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.eating_habbit_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberEatingHabbit(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void HoroscopeMatchSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_horoscope_match);
        final String array[] = getResources().getStringArray(R.array.horoscope_match_array);
        ArrayAdapter<CharSequence> profileForAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.horoscope_match_array, android.R.layout.simple_spinner_item);
        profileForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(profileForAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberHoroscope(array[position]);
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
                member.setMamberIsManglik(array[position]);
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
                member.setMamberPhysicalStatus(array[position]);
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
                member.setMamberHaveChild(array[position]);
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
                member.setMamberMaritalStatus(array[position]);
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
        member.setMamberBodyComplexion(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberBodyComplexion(array[position]);
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
        member.setMamberBodyType(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberBodyType(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GenderSpinner(View rootView) {
        /*Gender spinner*/
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_profile_gender);
        final String array[] = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberGender(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FamilyValueSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_family_value);
        final String array[] = getResources().getStringArray(R.array.family_value_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.family_value_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        member.setMamberFamilyValue(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberFamilyValue(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FamilyTypeSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_family_type);
        final String array[] = getResources().getStringArray(R.array.family_type_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.family_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberFamilyType(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FamilyStatusSpinner(Dialog rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.id_input_family_status);
        final String array[] = getResources().getStringArray(R.array.family_status_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.family_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member.setMamberFamilyStatus(array[position]);
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
                member.setMamberMotherOcupation(array[position]);
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
                member.setMamberFatherOcupation(array[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
