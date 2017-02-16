package com.example.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MyMediaPlayer";

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();

        Button btn_play  = (Button) findViewById(R.id.btn_play);
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        Button btn_stop  = (Button) findViewById(R.id.btn_stop);

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        Log.i(TAG, "SDK_API LEVEL: "+ Build.VERSION.SDK_INT);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "No Permission");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            Log.i(TAG, "Have Permission");
            initMediaPlayer(); //初始化MediaPlay
        }
    }

    private void initMediaPlayer() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String absolutePath = externalStorageDirectory.getAbsolutePath();
        Log.i(TAG, "absolutePath: "+absolutePath);

        try {
            File file = new File(Environment.getExternalStorageDirectory(), "t.mp3");
            String absolutePath2 = file.getAbsolutePath();
            Log.i(TAG, "absolutePath2: "+absolutePath2);
            mediaPlayer.setDataSource(file.getPath()); //指定音频文件的路径
            mediaPlayer.prepare(); //让MediaPlayer进入到准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG, "onRequestPermissionsResult: Get the permission");
                    initMediaPlayer();
                }else {
                    Toast.makeText(MainActivity.this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                if (!mediaPlayer.isPlaying()){
                    Log.i(TAG, "play");
                    mediaPlayer.start(); //开始播放
                }
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()){
                    Log.i(TAG, "pause");
                    mediaPlayer.pause(); //暂停播放
                }
                break;
            case R.id.btn_stop:
                if (mediaPlayer.isPlaying()){
                    Log.i(TAG, "stop");
                    mediaPlayer.reset(); //停止播放
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
