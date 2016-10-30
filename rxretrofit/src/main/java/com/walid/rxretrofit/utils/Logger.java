package com.walid.rxretrofit.utils;

import android.util.Log;

/**
 * Author   : walid
 * Data     : 2016-10-21  12:45
 * Describe :
 */
public class Logger {

    private static final String TAG = "rxretrofit";
    public static boolean DEBUG = true;

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    public static void d(String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(TAG, buildMessage(msg), tr);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    public static void v(String msg, Throwable tr) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(msg), tr);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, buildMessage(msg));
        }
    }

    public static void i(String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(TAG, buildMessage(msg), tr);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, buildMessage(msg));
        }
    }

    public static void w(String msg, Throwable tr) {
        if (DEBUG) {
            Log.w(TAG, buildMessage(msg), tr);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    public static void e(String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(TAG, buildMessage(msg), tr);
        }
    }

    private static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return "### " + caller.toString() + msg;
    }

}
