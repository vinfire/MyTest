package com.example.animation.frame;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.animation.R;

public class FrameActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        //android中imageview不支持直接显示动画 gif, 如需显示动画 gif可使用第三方库

        //方法一：使用XML实现
        //原图
//        imageView.setImageResource(R.drawable.tubage);
//        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        //全屏
//        imageView.setBackgroundResource(R.drawable.tubage);
//        animationDrawable = (AnimationDrawable) imageView.getBackground();
//        animationDrawable.start();

        //方法二：使用代码实现
        //创建帧动画对象
        animationDrawable = new AnimationDrawable();
        //添加一帧图片，并制定间隔时间
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b1), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b2), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b3), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b4), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b5), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.b6), 100);
        //设置imageView的背景为帧动画
        imageView.setBackground(animationDrawable);
        //循环执行帧动画
        animationDrawable.setOneShot(false);
        animationDrawable.start();

    }

    public void playFrame(View view){
        if (!animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    public void stopFrame(View view){
        if (animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }
}
