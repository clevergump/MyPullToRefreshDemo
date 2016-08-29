package com.clevergump.my_pulltorefresh_demo;

import android.app.Application;
import android.content.Context;

/**
 * @author clevergump
 */
public class MainApplication extends Application {
    private static MainApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context get() {
        return sInstance.getApplicationContext();
    }
}