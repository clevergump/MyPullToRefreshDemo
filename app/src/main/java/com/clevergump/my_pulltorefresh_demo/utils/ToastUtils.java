package com.clevergump.my_pulltorefresh_demo.utils;

import android.content.Context;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.MainApplication;


public class ToastUtils {

    private final static boolean DEBUG = true;

    private final static String TAG = ToastUtils.class.getSimpleName();

    /**
     * 构造函数
     */
    private ToastUtils() {
    }

    /**
     * 调试用的 toast
     *
     * @param msg
     */
    public static void showDebug(String msg) {
        if (DEBUG) {
            showShort(msg);
        }
    }

    /**
     * 调试用的 toast
     *
     * @param strResId
     */
    public static void showDebug(int strResId) {
        if (DEBUG) {
            showShort(strResId);
        }
    }

    public static void showShort(CharSequence msg) {
        Context appContext = MainApplication.get();
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(CharSequence msg) {
        Context appContext = MainApplication.get();
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(int strResId) {
        Context appContext = MainApplication.get();
        CharSequence msg = appContext.getText(strResId);
        showShort(msg);
    }

    public static void showLong(int strResId) {
        Context appContext = MainApplication.get();
        CharSequence msg = appContext.getText(strResId);
        showLong(msg);
    }
}