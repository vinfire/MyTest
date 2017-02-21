package com.example.networktest;

import android.app.Application;
import android.content.Context;

/**
 * Created by GTR on 2017/2/19.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
