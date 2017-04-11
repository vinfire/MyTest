package com.example.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JavaAndJSActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js);

        initView();

        btnLogin.setOnClickListener(this);

        initWebView();
    }

    private void initView() {

        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            login();
        }
    }

    private void login() {
        String number = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)){
            Toast.makeText(JavaAndJSActivity.this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //登录
            //javascript:javaCallJs(123456)
            myWebView.loadUrl("javascript:javaCallJs('"+number+"')");
            setContentView(myWebView);
        }
    }

    private void initWebView() {
        myWebView = new WebView(this);

        WebSettings settings = myWebView.getSettings(); //可以用此去设置一些浏览器的属性
        settings.setJavaScriptEnabled(true); //让WebView支持JavaScript脚本

        myWebView.setWebViewClient(new WebViewClient()); //网页跳转时，目标网页仍在当前WebView中显示,而不是打开系统浏览器

        //以后js通过 Android 字段调用 AndroidAndJsInterface 中的任何方法
        myWebView.addJavascriptInterface(new AndroidAndJsInterface(), "Android");

        //加载网络的网页
        //myWebView.loadUrl("http://www.baidu.com");
        ////加载本地的html页面
        myWebView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

        //显示页面
//        setContentView(myWebView);
    }

    class AndroidAndJsInterface{
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJSActivity.this, "我被Js调用了",Toast.LENGTH_SHORT).show();
        }

    }
}
