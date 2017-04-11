package com.example.animation.interpolator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.animation.R;

public class InterpolatorActivity extends AppCompatActivity {

    private ImageView imageView;
    private AnimationSet animationSet;
    private ScaleAnimation scaleAnimation;
    private TranslateAnimation translateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //请求把标题去掉
        getSupportActionBar().hide();
        setContentView(R.layout.activity_interpolator);

        imageView = (ImageView) findViewById(R.id.imageView);

        //shareInterpolator:动画插值器  true 使用AnimationSet的动画插值器； false 使用各自Animation的动画插值器
        animationSet = new AnimationSet(true);
        //添加弹球效果
        animationSet.setInterpolator(new BounceInterpolator());

        //设置缩放动画
        scaleAnimation = new ScaleAnimation(
                1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(scaleAnimation);

        //设置位移动画（通常最后添加，防止中心点找不准）
        translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.9f);
        animationSet.addAnimation(translateAnimation);

        animationSet.setDuration(3500);
        animationSet.setFillAfter(true);
    }

    public void start(View view){
        imageView.startAnimation(animationSet);
    }

    public void stop(View view){
        translateAnimation.cancel();
        scaleAnimation.cancel();
    }
}
