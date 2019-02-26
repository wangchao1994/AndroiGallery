
package com.raisesail.gallery.utils;

import android.util.Log;


public class LogUtils {
	
    private static boolean DEBUG = true;//默认关闭LOG输出

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.i(TAG, msg, e);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.e(TAG, msg, e);
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.d(TAG, msg, e);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void v(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.v(TAG, msg, e);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.w(TAG, msg, e);
        }
    }
    
}
