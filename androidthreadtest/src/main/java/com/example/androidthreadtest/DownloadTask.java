package com.example.androidthreadtest;

import android.os.AsyncTask;

/**
 * Created by GTR on 2017/2/14.
 */

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {


    @Override
    protected void onPreExecute() {
        //显示进度对话框
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //在这里更新进度
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //关闭进度对话框

        //在这里提示下载结果
    }
}
