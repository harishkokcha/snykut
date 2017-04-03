package com.namdev.sanyukt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppPreferences;

/**
 * Created by harish on 12/24/2016.
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        Activity mActivity = SplashScreen.this;
        Users users = AppPreferences.getInstance().getUser(mActivity);
        Intent intent;
        Log.d("Harish", "SplashScreen users" + users.getUsername());
        if (!users.getUserid().equals("")) {
            intent = new Intent(mActivity, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
