package com.example.networktest;

/**
 * Created by GTR on 2017/2/13.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
