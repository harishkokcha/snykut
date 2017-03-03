package com.namdev.sanyukt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.BeanManager;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.CommonUtils;
import com.namdev.sanyukt.utils.GenericRequest;
import com.namdev.sanyukt.views.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Harish on 11/21/2016.
 */
public class AddPersonFragment extends Fragment {
    Context mContext;
    Member member;
    private Activity mActivity;
    private CommonUtils commonUtils;

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
        commonUtils = new CommonUtils();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_person, container, false);
        BeanManager beanManager = BeanManager.getInstance();
        //member = (Member) beanManager.getMembers();

        member = new Member();
        mActivity = getActivity();
        ProfileForSpinner(rootView);
        GenderSpinner(rootView);
        ManglikSpinner(rootView);
        BodyTypeSpinner(rootView);
        BodyComplexionSpinner(rootView);
        MaritalStatusSpinner(rootView);
        HaveChildSpinner(rootView);
        PhysicalStatusSpinner(rootView);
        EmployementSpinner(rootView);
        AnnualIncomeSpinner(rootView);
        final EditText height = (EditText) rootView.findViewById(R.id.id_input_height);
        final EditText weight = (EditText) rootView.findViewById(R.id.id_input_weight);
        final EditText name = (EditText) rootView.findViewById(R.id.id_input_name);
        final EditText dob = (EditText) rootView.findViewById(R.id.id_input_dob);

        final EditText education = (EditText) rootView.findViewById(R.id.id_input_education);
        final EditText hEducation = (EditText) rootView.findViewById(R.id.id_input_education_heigher);
        final EditText empDetails = (EditText) rootView.findViewById(R.id.id_input_employement_details);
        final EditText city = (EditText) rootView.findViewById(R.id.id_input_city);
        final EditText state = (EditText) rootView.findViewById(R.id.id_input_state);
        TextView submit = (TextView) rootView.findViewById(R.id.id_fragment_new_member_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setMemberName(name.getText().toString());
                member.setMemberDob(dob.getText().toString());
                member.setMamberCity(city.getText().toString());
                member.setMamberState(state.getText().toString());
                member.setMamberEducation(education.getText().toString());
                member.setMamberEmployeeDetails(empDetails.getText().toString());
                Users users=AppPreferences.getInstance().getUser(mActivity);
                member.setMemberUserId(users.getUserid());
                Log.d("Harish"," Member Details "+new Gson().toJson(member));
                GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.USER_LOGIN,
                        ApiResponse.class, member, new Response.Listener<ApiResponse>() {
                    @Override
                    public void onResponse(ApiResponse response) {
                        if (response.getData().equals(AppConstants.SUCCESS)) {
                            Member member = new Gson().fromJson(response.getData(), Member.class);
                            if (member.getMemberId() != null) {
                                Toast.makeText(mActivity,"member Added successfully",Toast.LENGTH_SHORT).show();
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

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
                                date = commonUtils.getDateTimeFromString(date, "dd/MM/yyyy", "d MMM yyyy");
                                dob.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                /*dpd.setMinDate(now);
                Log.d("Calender","MinDate : "+now);*/
                now = Calendar.getInstance();
                //now.add(Calendar.MONTH,6);
                dpd.setMaxDate(now);
                dpd.setAccentColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                dpd.show(mActivity.getFragmentManager(), "Datepickerdialog");
            }
        });
        return rootView;
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
