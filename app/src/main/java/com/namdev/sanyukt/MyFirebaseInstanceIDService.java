package com.namdev.sanyukt;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.namdev.sanyukt.utils.AppPreferences;

/**
 * Created by Harish on 3/22/2017.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private String TAG = "MyFirebaseInstance";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.

        AppPreferences.getInstance().setDeviceId(refreshedToken);
//        sendRegistrationToServer(refreshedToken);
    }
}
