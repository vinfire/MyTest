package com.example.webviewtest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class JsCallJavaVideoActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);

        webView = (WebView) findViewById(R.id.webview);

        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings(); //可以用此去设置一些浏览器的属性
        settings.setJavaScriptEnabled(true); //让WebView支持JavaScript脚本

        webView.setWebViewClient(new WebViewClient()); //网页跳转时，目标网页仍在当前WebView中显示,而不是打开系统浏览器

        //以后js通过 Android 字段调用 AndroidAndJsInterface 中的任何方法
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "android");

        ////加载本地的html页面
        webView.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");

    }

    class AndroidAndJsInterface{

        @JavascriptInterface
        public void playVideo(int id, String videoUrl, String title){
            //从系统中选择播放器进行播放
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(videoUrl), "video/*");
            startActivity(intent);

//            Toast.makeText(JsCallJavaVideoActivity.this, "videoUrl = "+videoUrl,Toast.LENGTH_SHORT).show();
        }
    }
}
