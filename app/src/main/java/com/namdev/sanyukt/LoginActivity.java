package com.namdev.sanyukt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.DialogUtils;
import com.namdev.sanyukt.utils.FormUtils;
import com.namdev.sanyukt.utils.GenericRequest;

/**
 * Created by harish on 12/24/2016.
 */

public class LoginActivity extends Activity {

    private EditText userName;
    private EditText userPass;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mActivity = LoginActivity.this;
        userName = (EditText) findViewById(R.id.edt_mob);
        userPass = (EditText) findViewById(R.id.edt_pass);
        Button loginBtn = (Button) findViewById(R.id.btn_login);
        final TextView forgotPass = (TextView) findViewById(R.id.forget_password);
        TextView signUp = (TextView) findViewById(R.id.txv_sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RegisterationActivity.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogUtils().forgotPassDialog(mActivity, true);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataValidate()) {
                    final Users users = new Users();
                    users.setUserphoneno(userName.getText().toString());
                    users.setUserpassword(userPass.getText().toString());
                    users.setAction(AppConstants.USER_LOGIN_ACTION);

                    GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.USER_LOGIN,
                            ApiResponse.class, users, new Response.Listener<ApiResponse>() {
                        @Override
                        public void onResponse(ApiResponse response) {
                            if (response.getResponsecode().equals(AppConstants.SUCCESS)) {
                                Users user = new Gson().fromJson(response.getData(), Users.class);
                                if (user.getUserid() != null) {
                                    AppPreferences.getInstance().setUserLogin(mActivity, users);
                                    Intent intent = new Intent(mActivity, MainActivity.class);
                                    startActivity(intent);
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
            }
        });
    }

    public boolean isDataValidate() {
        boolean isDataValid = true;
        final FormUtils formUtils = new FormUtils(mActivity);
        if (formUtils.isEmpty(userName.getText().toString())) {
            userName.requestFocus();
            userName.setError(getResources().getString(R.string.empty_email));
            isDataValid = false;
        } else if (!userName.getText().toString().matches(getResources().getString(R.string.phone_regex)) && !userName.getText().toString().matches(getResources().getString(R.string.email_regex))) {
            userName.requestFocus();
            userName.setError(getResources().getString(R.string.empty_email));
            isDataValid = false;
        } else if (formUtils.isEmpty(userPass.getText().toString())) {
            userPass.requestFocus();
            userPass.setError(getResources().getString(R.string.empty_email));
            isDataValid = false;
        }
        return isDataValid;
    }
}
