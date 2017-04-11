package com.example.animation.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.animation.R;

public class TweenActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button btn_translate;
    private Button btn_scale;
    private Button btn_alpha;
    private Button btn_rotate;
    private Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);

        imageView = (ImageView) findViewById(R.id.imageView);

        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_set = (Button) findViewById(R.id.btn_set);

        btn_translate.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_alpha.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate:
                translate(); //位移动画
                break;
            case R.id.btn_scale:
                scale(); //缩放动画
                break;
            case R.id.btn_alpha:
                alpha(); //透明度动画
                break;
            case R.id.btn_rotate:
                rotate(); //旋转动画
                break;
            case R.id.btn_set:
                setanim(); //组合动画
            default:
                break;
        }
    }

    private void translate() {
        //framXType 开始点X坐标的类型: 相对于自己 相对于父亲 绝对位置
        //fromXValue 开始点X坐标的位置
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f);
        //运行时间
        translateAnimation.setDuration(2000); //动画时间必须设置
        //设置循环运行
        translateAnimation.setRepeatCount(Animation.INFINITE);
        //保持最后状态
        translateAnimation.setFillAfter(true);
        //设置重复模式: Animation.RESTART 重新开始; Animation.REVERSE 往复
        translateAnimation.setRepeatMode(Animation.REVERSE);
        //开始动画
        imageView.startAnimation(translateAnimation);
    }

    private void scale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 3.0f,
                1.0f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        imageView.startAnimation(scaleAnimation);
    }

    private void alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setRepeatCount(3);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(alphaAnimation);
    }

    private void rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(3);
        imageView.startAnimation(rotateAnimation);
    }

    private void setanim() {
        //创建补间动画集合
        AnimationSet animationSet = new AnimationSet(false); //true表示使用AnimationSet的插值器，false表示使用animation自己的插值器

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -0.5f);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 0,
                1.0f, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.5f);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //添加顺序不同会导致不同动画
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation); //放最后

        animationSet.setDuration(5000);
        imageView.startAnimation(animationSet);

        //为动画设置监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Display display = getWindowManager().getDefaultDisplay();
                // 获取屏幕高度
                int height = display.getHeight();
                Toast toast = Toast.makeText(TweenActivity.this, "icon out of view", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, height/4);
                toast.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
