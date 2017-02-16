package com.example.servicebestpractice;

/**
 * Created by GTR on 2017/2/14.
 */

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCancled();
}
