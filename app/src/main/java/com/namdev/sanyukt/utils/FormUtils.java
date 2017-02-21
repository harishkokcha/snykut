package com.namdev.sanyukt.utils;

import android.app.Activity;

import com.namdev.sanyukt.R;

import java.util.regex.Pattern;

/**
 * Created by harish on 12/24/2016.
 */

public class FormUtils {

    Activity mActivity;

    public FormUtils(Activity mActivity) {
        this.mActivity=mActivity;
    }

    public boolean isCityalidate(String editText, boolean required){
        String NAME_REGEX =mActivity.getResources().getString(R.string.name_regex);
        String EMAIL_MSG =mActivity.getResources().getString(R.string.city_error_msg);
        if ( !isEmpty(editText))
            return isValid(editText, NAME_REGEX, EMAIL_MSG,required,null);
        else
            return true;
    }

    public boolean isNameValidate(String editText, boolean required){
        String NAME_EMPTY_MSG=mActivity.getResources().getString(R.string.empty_name);
        String NAME_REGEX =mActivity.getResources().getString(R.string.name_regex);
        String NAME_MSG =mActivity.getResources().getString(R.string.name_error_msg);
        return isValid(editText, NAME_REGEX, NAME_MSG,required,NAME_EMPTY_MSG);
    }

    public boolean isEmailAddress(String editText, boolean required) {
        String EMAIL_EMPTY_MSG = mActivity.getResources().getString(R.string.empty_email);
        String EMAIL_REGEX =mActivity.getResources().getString(R.string.email_regex);
        String EMAIL_MSG =mActivity.getResources().getString(R.string.email_error_msg);
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required,EMAIL_EMPTY_MSG);
    }

    public boolean isPhoneNumber(String editText, boolean required) {
        String PHONE_EMPTY_MSG=mActivity.getResources().getString(R.string.empty_phone);
        String PHONE_REGEX = mActivity.getResources().getString(R.string.phone_regex);
        String PHONE_MSG = mActivity.getResources().getString(R.string.phone_error_msg);
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required,PHONE_EMPTY_MSG);
    }


    public boolean isValid(String editText, String regex, String errMsg, boolean required,String blankMsg) {

        // editText.setError(null);
        if ( required && !hasText(editText,blankMsg) ) return false;
        if (required && !Pattern.matches(regex, editText)) {
            new CommonUtils().showToast(mActivity,errMsg);
            return false;
        }

        return true;
    }


    public boolean hasText(String editText,String msg) {


        if (editText.trim().length() == 0) {
            //  editText.setError(REQUIRED_MSG);
            new CommonUtils().showToast(mActivity,msg);
            return false;
        }

        return true;
    }

    public boolean isEmpty(String etText) {
        return etText.trim().length() <= 0;
    }
}
