package com.example.mydemo1.app;

import android.app.Application;
import android.content.Context;


import com.example.mydemo1.utils.DataManager;

import javax.inject.Inject;

public class MyApp extends Application {

    public static boolean isNaightMode;
    private static MyApp app;
    private static Context context;
    @Inject
    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MyApp getInstance() {
        return app;
    }

}
