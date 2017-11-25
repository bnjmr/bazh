package ir.jahanmirbazh.classes;

import android.util.Log;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 7/30/2017.
 */

public class Logger {

    public static void d(String method, String result) {
        Log.d(V.className, method + " | " + result);
    }

    public static void e(String method, String result) {
        Log.e(V.className, method + " | " + result);
    }
}
