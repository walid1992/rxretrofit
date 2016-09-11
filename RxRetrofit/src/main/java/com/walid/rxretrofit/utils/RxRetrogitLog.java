package com.walid.rxretrofit.utils;

import android.util.Log;

/**
 * Created by walid on 16/8/10.
 * log 日志
 */
public class RxRetrogitLog {

    private static final String TAG = "rxjava-retrofit";
    public static boolean DEBUG;

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    public static void d(Object obj) {
        if (DEBUG) {
            Log.d(TAG, buildMessage(String.valueOf(obj)));
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    private static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return "### " + caller.toString() + msg;
    }

}
