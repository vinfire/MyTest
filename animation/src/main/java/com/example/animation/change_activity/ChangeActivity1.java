package com.example.animation.change_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.animation.R;

public class ChangeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change1);
    }

    //淡入淡出
    public void alphaSwitch(View view){
        Intent intent = new Intent(this, ChangeActivity2.class);
        startActivity(intent);
        //enterAnim Activity 进入的动画
        //exitAnim Activity 退出的动画
        overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
    }

    //右往左推
    public void translateSwitch(View view){
        Intent intent = new Intent(this, ChangeActivity2.class);
        startActivity(intent);
        //enterAnim Activity 进入的动画
        //exitAnim Activity 退出的动画
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
    }

    //左上角缩小淡出
    public void zoomSwitch(View view){
        Intent intent = new Intent(this, ChangeActivity2.class);
        startActivity(intent);
        //enterAnim Activity 进入的动画
        //exitAnim Activity 退出的动画
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
}
