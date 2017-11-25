package ir.jahanmirbazh.classes;

/**
 * Created by HaMiD on 1/15/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Firoozian on 1/14/2017.
 */


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "firebasToken", Context.MODE_PRIVATE);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firebasToken", refreshedToken);
        editor.apply();



    }


}