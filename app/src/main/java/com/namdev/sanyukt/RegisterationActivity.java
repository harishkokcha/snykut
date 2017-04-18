package com.namdev.sanyukt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.UserPhoneVerification;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.CommonUtils;
import com.namdev.sanyukt.utils.DialogUtils;
import com.namdev.sanyukt.utils.FormUtils;
import com.namdev.sanyukt.utils.GenericRequest;

/**
 * Created by harish on 12/24/2016.
 */
public class RegisterationActivity extends Activity {

    CommonUtils mCommonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_activity);
        mCommonUtils = new CommonUtils();
        final Activity mActivity = RegisterationActivity.this;
        final EditText nameEditText = (EditText) findViewById(R.id.et_name);
        final EditText phoneEditText = (EditText) findViewById(R.id.mob_no);
        final EditText emailEditText = (EditText) findViewById(R.id.email);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);

        Button signUp = (Button) findViewById(R.id.btn_join);
        TextView loginText = (TextView) findViewById(R.id.txt_login_inst);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormUtils formUtils = new FormUtils(mActivity);
                if (formUtils.isNameValidate(nameEditText.getText().toString(), true)
                        && formUtils.isPhoneNumber(phoneEditText.getText().toString(), true)
                        && formUtils.isEmailAddress(emailEditText.getText().toString(), true)
                        && formUtils.hasText(passwordEditText.getText().toString(), getResources().getString(R.string.password_empty_msg))) {
                    Users users = new Users();
                    users.setUseremail(emailEditText.getText().toString());
                    users.setUserphoneno(phoneEditText.getText().toString());
                    users.setUsername(nameEditText.getText().toString());
                    users.setUserpassword(passwordEditText.getText().toString());
                    users.setAction(AppConstants.USER_CREATE_ACTION);

                    GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.BASE_URL,
                            ApiResponse.class, users, new Response.Listener<ApiResponse>() {
                        @Override
                        public void onResponse(ApiResponse response) {
                            if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                                Users user = new Gson().fromJson((response.getObjects()).toString(), Users.class);
                                Log.d("Harish", "response user" + new Gson().toJson(user));
                                if (user.getUserid() != null) {
                                    setUpMobileVerificationDialog(mActivity, user);
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Harish", "response " + new Gson().toJson(error));
                        }
                    }) {
                    };

                    AppController.getInstance().addToRequestQueue(genericRequest);
                }
            }
        });

    }

    public void setResendOTPBtn(Activity mActivity, TextView resendOtpBtn) {
        resendOtpBtn.setTextColor(mCommonUtils.getColorInt(R.color.colorPrimary, mActivity));
        resendOtpBtn.setClickable(true);
    }


    public void resetResendOTPBtn(Activity mActivity, TextView resendOtpBtn) {
        resendOtpBtn.setTextColor(mCommonUtils.getColorInt(R.color.darker_grey, mActivity));
        resendOtpBtn.setClickable(false);
    }

    private void setUpMobileVerificationDialog(final Activity mActivity, final Users user) {

        final Dialog mobileDialog = new Dialog(mActivity, R.style.customDialog);
        mobileDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //mobileDialog.getWindow().getAttributes().windowAnimations = R.style.customDialog;
        mobileDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mobileDialog.setContentView(R.layout.user_mobile_verification_dialog);
        mobileDialog.setCanceledOnTouchOutside(false);

        //final ProgressBar progressBar;

        TextView hdrTxt = (TextView) mobileDialog.findViewById(R.id.id_mobile_verification_dialog_hdr);
        hdrTxt.setText(mActivity.getResources().getString(R.string.user_mobile_activity_title));

        final EditText otpEditTxt = (EditText) mobileDialog.findViewById(R.id.id_user_mobile_otp_txtvw);
        final TextView resendOtpBtn = (TextView) mobileDialog.findViewById(R.id.id_user_mobile_resend_otp);
        final TextView mobileVerifiedActionBtn = (TextView) mobileDialog.findViewById(R.id.id_user_mobile_action_btn);

        ImageView mobileDialogCrossIcon = (ImageView) mobileDialog.findViewById(R.id.id_mobile_verification_dialog_close_ic);

        final UserPhoneVerification userPhoneVerification = new UserPhoneVerification();

        final CountDownTimer timer = new CountDownTimer(AppConstants.COUNT_DOWN_TIME_OPT, AppConstants.COUNT_DOWN_TIME_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                resetResendOTPBtn(mActivity, resendOtpBtn);
            }

            @Override
            public void onFinish() {
                setResendOTPBtn(mActivity, resendOtpBtn);
            }

        };
        timer.start();


        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mobileVerifiedActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpNumber = otpEditTxt.getText().toString();
                if (otpNumber.length() != mActivity.getResources().getInteger(R.integer.otp_length)) {
                    mCommonUtils.showToast(mActivity, mActivity.getResources().getString(R.string.otp_error_msg));
                } else {

                    mobileVerifiedActionBtn.setEnabled(false);
                    mobileVerifiedActionBtn.setBackgroundColor(mCommonUtils.getColorInt(R.color.darker_grey, mActivity));
                    userPhoneVerification.setUserID(user.getUserid());
                    userPhoneVerification.setOtp(otpNumber);
                    userPhoneVerification.setAction(AppConstants.OTP_VERIFY);
                    GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.BASE_URL,
                            ApiResponse.class, userPhoneVerification, new Response.Listener<ApiResponse>() {
                        @Override
                        public void onResponse(ApiResponse response) {
                            if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                                if (user.getUserid() != null) {
                                    AppPreferences.getInstance().setUserLogin(mActivity, user);
                                    Intent intent = new Intent(mActivity, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Harish", "response " + new Gson().toJson(error));
                        }
                    }) {
                    };

                    AppController.getInstance().addToRequestQueue(genericRequest);

                }
            }
        });

        mobileDialogCrossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileDialog.dismiss();
            }
        });

        mobileDialog.show();
    }
}
