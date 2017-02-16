package com.example.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "networktest";
    TextView tv_request_text;
    private Button btn_send_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send_request = (Button) findViewById(R.id.btn_send_request);
        tv_request_text = (TextView) findViewById(R.id.tv_request_text);

        btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*使用HttpURLConnection*/
//                sendRequestWithHttpURLConnection();

                /*使用OkHttp*/
                sendRequestWithOkHttp();
            }
        });
    }

    /*使用HttpURLConnection实现网络请求*/
    private void sendRequestWithHttpURLConnection() {
        String address = "https://www.baidu.com";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                //在这里根据返回内容执行具体的逻辑
                showResponse(response);
            }

            @Override
            public void onError(Exception e) {
                //在这里对异常情况进行处理
                Log.i(TAG, "onError");
            }
        });
    }

    /*使用OkHttp实现网络请求*/
    private void sendRequestWithOkHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("http://10.0.2.2/xmlTest/get_data.json")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//
////                    showResponse(responseData);
//
//                    //使用Pull解析XML格式的数据
////                    parseXMLWithPull(responseData);
//
//                    //使用SAX解析XML格式的数据
////                    parseXMLWithSAX(responseData);
//
//                    //使用JSONObject解析JSON格式的数据
////                    parseJSONWithJSONObject(responseData);
//
//                    //使用GSON解析JSON格式的数据
//                    parseJSONWithGSON(responseData);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        HttpUtil.sendOkHttpRequest("http://10.0.2.2/xmlTest/get_data.json", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                    //使用Pull解析XML格式的数据
//                    parseXMLWithPull(responseData);

                    //使用SAX解析XML格式的数据
//                    parseXMLWithSAX(responseData);

                    //使用JSONObject解析JSON格式的数据
//                    parseJSONWithJSONObject(responseData);

                    //使用GSON解析JSON格式的数据
                    parseJSONWithGSON(responseData);
            }
        });
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                tv_request_text.setText(response);
            }
        });
    }

    private void parseXMLWithPull(String responseData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            Log.i(TAG, "parseXMLWithPull: id is "+id);
                            Log.i(TAG, "parseXMLWithPull: name is "+name);
                            Log.i(TAG, "parseXMLWithPull: version is "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            //将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            //开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.i(TAG, "parseJSONWithJSONObject: id is "+id);
                Log.i(TAG, "parseJSONWithJSONObject: name is "+name);
                Log.i(TAG, "parseJSONWithJSONObject: version is "+version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
//        gson.fromJson(jsonData,App.class); //将JSON数据解析成一个对象
        List<App> applist = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType()); //解析JSON数组
        for (App app : applist) {
            Log.i(TAG, "parseJSONWithGSON: id is "+app.getId());
            Log.i(TAG, "parseJSONWithGSON: name is "+app.getName());
            Log.i(TAG, "parseJSONWithGSON: version is "+app.getVersion());
        }
    }
}
