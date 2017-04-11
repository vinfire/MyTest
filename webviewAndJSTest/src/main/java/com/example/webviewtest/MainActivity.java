package com.example.webviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnJavaAndJs;
    private Button btnJsCallJava;
    private Button btnJsCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {
        btnJavaAndJs = (Button)findViewById( R.id.btn_java_and_js );
        btnJsCallJava = (Button)findViewById( R.id.btn_js_call_java );
        btnJsCallPhone = (Button)findViewById( R.id.btn_js_call_phone );
    }

    private void initListener() {
        btnJavaAndJs.setOnClickListener( this );
        btnJsCallJava.setOnClickListener( this );
        btnJsCallPhone.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_java_and_js:
                //Java代码和H5代码互调
                Intent intent1 = new Intent(this, JavaAndJSActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_js_call_java:
                //H5调用Android播放视频
                Intent intent2 = new Intent(this, JsCallJavaVideoActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_js_call_phone:
                //H5调用Android拨打电话"
                Intent intent3 = new Intent(this, JsCallJavaCallPhoneActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
