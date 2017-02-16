package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService"); //调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //打印当前线程的id
        Log.i(TAG, "onHandleIntent: Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: executed");
    }
}
