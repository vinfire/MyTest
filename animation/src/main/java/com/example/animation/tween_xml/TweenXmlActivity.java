package com.example.animation.tween_xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.animation.R;
import com.example.animation.tween.TweenActivity;

public class TweenXmlActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button btn_translate;
    private Button btn_scale;
    private Button btn_alpha;
    private Button btn_rotate;
    private Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_xml);


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
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        imageView.startAnimation(animation);
    }

    private void scale() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        imageView.startAnimation(animation);
    }

    private void alpha() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        imageView.startAnimation(animation);
    }

    private void rotate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(animation);
    }

    private void setanim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.set);
        imageView.startAnimation(animation);
    }
}
