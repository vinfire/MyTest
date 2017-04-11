package com.example.animation.property;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.animation.R;

public class ObjectAnimatorActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private Button btn_translate;
    private Button btn_scale;
    private Button btn_alpha;
    private Button btn_rotate;
    private Button btn_set1;
    private Button btn_set2;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);

        //获取窗口管理者
        WindowManager windowManager = getWindowManager();
        //获取展示效果对象
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕矩阵
        defaultDisplay.getMetrics(metrics);
        //获取屏幕宽高
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        imageView = (ImageView) findViewById(R.id.imageView);

        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_set1 = (Button) findViewById(R.id.btn_set1);
        btn_set2 = (Button) findViewById(R.id.btn_set2);

        btn_translate.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_alpha.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_set1.setOnClickListener(this);
        btn_set2.setOnClickListener(this);
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
            case R.id.btn_set1:
                combine1(); //组合动画
                break;
            case R.id.btn_set2:
                combine2(); //组合动画
                break;
            default:
                break;
        }
    }

    private void translate() {
        //参数1：target 目标控件
        //参数2：property 动画类型
        //参数3：values 可变参数
        ObjectAnimator object_transX = ObjectAnimator.ofFloat(imageView, "translationX", 0, 10, 20, 50, 100, 300);
        object_transX.setDuration(3000);
        object_transX.setRepeatCount(ObjectAnimator.INFINITE);
        object_transX.start();

    }

    private void scale() {
        ObjectAnimator object_scale = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.8f, 0.5f, 0, 1.0f);
        object_scale.setDuration(5000);
        object_scale.start();
    }

    private void alpha() {
        ObjectAnimator object_alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.8f, 0.5f, 0, 1.0f);
        object_alpha.setDuration(5000);
        object_alpha.start();
    }

    private void rotate() {
        ObjectAnimator object_rotate = ObjectAnimator.ofFloat(imageView, "rotation", 0, 100, 120, 200, 210, 230, 360 );
        object_rotate.setDuration(4000);
        object_rotate.start();
    }

    private void combine1() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, alpha, scaleX, scaleY);
        objectAnimator.setDuration(4000);
        objectAnimator.start();
    }

    private void combine2(){

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new PointF(0,0)); //初始位置
        valueAnimator.setDuration(5000);
        //设置属性动画的执行过程数据
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            //fraction  (当前时间t)/duration
            //startValue 起始点
            //endValue 终点
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x = fraction*width/2;
                pointF.y = (float) Math.sqrt(pointF.x*width)*0.9f;
                return pointF;
            }
        });

        //设置动画变化的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //将当前位置点取出
                PointF pointF = (PointF) animation.getAnimatedValue();
                //设置当前imageView的位置
                imageView.setTranslationX(pointF.x);
                imageView.setTranslationY(pointF.y);
            }
        });

        //执行动画
        valueAnimator.start();
    }
}
