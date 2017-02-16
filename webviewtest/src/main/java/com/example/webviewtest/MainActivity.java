package com.example.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = myWebView.getSettings(); //可以用此去设置一些浏览器的属性
        settings.setJavaScriptEnabled(true); //让WebView支持JavaScript脚本
        myWebView.setWebViewClient(new WebViewClient()); //网页跳转时，目标网页仍在当前WebView中显示,而不是打开系统浏览器
        myWebView.loadUrl("http://www.baidu.com");
    }
}
