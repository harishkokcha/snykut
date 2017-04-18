package com.namdev.sanyukt.utils;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by harish on 12/24/2016.
 */
public class CommonUtils {

    public void showToast(Activity mActivity, String errMsg) {
        Toast.makeText(mActivity,errMsg,Toast.LENGTH_SHORT).show();
    }

    public String getDateTimeFromString(String inputTimeStamp,String fromStringFormat,String toStringFormat){
        SimpleDateFormat  format = new SimpleDateFormat(fromStringFormat,Locale.ENGLISH);
        try {
            Date date = format.parse(inputTimeStamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat(toStringFormat,Locale.ENGLISH);
            Log.d("Harish","DateFormat : "+dateFormat.format(date));
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getDateTimeFromString(String inputTimeStamp){
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(inputTimeStamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss",Locale.ENGLISH);
            Log.d("Harish","DateFormat : "+dateFormat.format(date));
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getColorInt(int iColor, final Activity mActivity) {
        int oColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            oColor = mActivity.getResources().getColor(iColor, mActivity.getTheme());
        else
            oColor = mActivity.getResources().getColor(iColor);

        return oColor;
    }

}
